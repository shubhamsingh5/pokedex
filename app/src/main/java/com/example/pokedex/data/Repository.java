package com.example.pokedex.data;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pokedex.data.local.PokemonDatabase;
import com.example.pokedex.data.local.entity.MoveDetail;
import com.example.pokedex.data.local.entity.PokemonOverview;
import com.example.pokedex.data.local.entity.Species;
import com.example.pokedex.data.remote.NetworkBoundResource;
import com.example.pokedex.data.remote.Resource;
import com.example.pokedex.data.remote.api.PokeApiService;
import com.example.pokedex.data.remote.api.RetrofitClient;
import com.example.pokedex.data.remote.model.PokeApiResponse;
import com.example.pokedex.data.remote.model.move.MoveApiResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Repository {

    private PokeApiService webService;
    private static Repository repository;
    private PokemonDatabase db;
    private int numFetched;

    public static Repository getInstance(Context context, int stored) {
        if (repository == null) {
            repository = new Repository(context, stored);
        }
        return repository;
    }

    public static Repository getInstance(Context context) {
        return repository;
    }

    private Repository(Context context, int stored) {
        this.webService = RetrofitClient.getRetrofitClient(PokeApiService.class);
        this.db = PokemonDatabase.getInstance(context);
        this.numFetched = stored;
    }

    public Observable<Resource<List<PokemonOverview>>> getAllPokemon(int offset) {
        return new NetworkBoundResource<List<PokemonOverview>, PokeApiResponse>() {

            @Override
            protected void saveCallResult(@NonNull PokeApiResponse item) {
                Observable.fromIterable(item.getResults())
                        .concatMap(pokemon -> webService.getPokemonOverview(pokemon.getUrl()))
                        .toList()
                        .subscribe(list -> {
                                    db.pokemonDAO().insertPokemonOverview(list);
                                    numFetched += list.size();
                                },
                                throwable -> Log.e(TAG, throwable.getMessage(), throwable))
                        .dispose();
            }

            @Override
            protected boolean shouldFetch() {
                return numFetched == 0 || numFetched <= offset;
            }

            @NonNull
            @Override
            protected Flowable<List<PokemonOverview>> loadFromDb() {
                return db.pokemonDAO().getAllPokemonsOverview();
            }

            @NonNull
            @Override
            protected Observable<Resource<PokeApiResponse>> createCall() {
                return webService.loadPokemons(offset)
                        .flatMap(pokeApiResponse -> Observable.just(pokeApiResponse == null
                                ? Resource.error("", new PokeApiResponse())
                                : Resource.success(pokeApiResponse)));

//                return webService.loadPokemons()
//                        .flatMap(pokeApiResponse -> Observable.fromIterable(pokeApiResponse.getResults()))
//                        .concatMap(pokemon -> webService.getPokemonOverview(pokemon.getUrl()))
//                        .toList()
//                        .toObservable();
            }
        }.getAsObservable();
    }

    public Single<Species> getPokemonSpeciesFromDb(int id) {
        return db.pokemonDAO().getPokemonSpeciesById(id)
                .onErrorResumeNext(t -> getPokemonSpeciesFromApi(id))
                .retry();
    }

    public Single<Species> getPokemonSpeciesFromApi(int id) {
        return webService.getPokemonSpecies(id)
                .doAfterSuccess(species -> db.pokemonDAO().insertPokemonSpecies(species));
    }

    public Observable<MoveDetail> getPokemonMoveFromDb(int id) {
        return db.pokemonDAO().getPokemonMoveById(id)
                .onErrorResumeNext(t -> getPokemonMoveFromApi(id))
                .retry()
                .toObservable();
    }

    public Single<MoveDetail> getPokemonMoveFromApi(int id) {
        return webService.getMoveById(id)
                .doAfterSuccess(move -> db.pokemonDAO().insertPokemonMove(move));
    }

    //TODO: figure out why this doesn't work
//    public Observable<Resource<Species>> getPokemonSpecies(int id) {
//        return new NetworkBoundResource<Species, Species>() {
//            @Override
//            protected void saveCallResult(@NonNull Species item) {
//                db.pokemonDAO().insertPokemonSpecies(item);
//            }
//
//            @Override
//            protected boolean shouldFetch() {
//                return true;
//            }
//
//            @NonNull
//            @Override
//            protected Flowable<Species> loadFromDb() {
//                return db.pokemonDAO().getPokemonSpeciesById(id);
//            }
//
//            @NonNull
//            @Override
//            protected Observable<Resource<Species>> createCall() {
//                return webService.getPokemonSpecies(id)
//                        .flatMap(species -> Observable.just(species == null
//                                ? Resource.error("", new Species())
//                                : Resource.success(species)));
//            }
//        }.getAsObservable();
//    }
    public Observable<Resource<PokemonOverview>> getPokemonDetail(int id) {
        return db.pokemonDAO().getPokemonDetailById(id)
                .toObservable()
                .map(Resource::success)
                .take(1);
    }

    public Observable<List<MoveDetail>> getMoveDetail(List<MoveApiResponse> moves) {
        return Observable.fromIterable(moves)
                .concatMap(move -> {
                    int id = Integer.parseInt(move.getMove().getUrl().substring(30).replace("/", ""));
                    return getPokemonMoveFromDb(id);
                })
                .toList()
                .toObservable();
    }
}

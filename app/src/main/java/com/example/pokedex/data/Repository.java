package com.example.pokedex.data;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pokedex.data.local.PokemonDatabase;
import com.example.pokedex.data.local.entity.Pokemon;
import com.example.pokedex.data.local.entity.PokemonOverview;
import com.example.pokedex.data.remote.NetworkBoundResource;
import com.example.pokedex.data.remote.Resource;
import com.example.pokedex.data.remote.api.PokeApiService;
import com.example.pokedex.data.remote.api.RetrofitClient;
import com.example.pokedex.data.remote.model.PokeApiResponse;
import com.example.pokedex.data.remote.model.species.Species;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Repository {

    private PokeApiService webService;
    private static Repository repository;
    private PokemonDatabase db;

    public static Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }

    private Repository(Context context) {
        this.webService = RetrofitClient.getRetrofitClient(PokeApiService.class);
        this.db = PokemonDatabase.getInstance(context);
    }

    public Observable<Resource<List<PokemonOverview>>> getAllPokemon(int offset) {
        return new NetworkBoundResource<List<PokemonOverview>, PokeApiResponse>() {

            @Override
            protected void saveCallResult(@NonNull PokeApiResponse item) {
                Observable.fromIterable(item.getResults())
                        .concatMap(pokemon -> webService.getPokemonOverview(pokemon.getUrl()))
                        .toList()
                        .subscribe(list -> db.pokemonDAO().insertPokemonOverview(list),
                                throwable -> Log.e(TAG, throwable.getMessage(), throwable));
            }

            @Override
            protected boolean shouldFetch() {
                return true;
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

    public Observable<Resource<Pokemon>> getPokemonDetail(int id) {
        return new NetworkBoundResource<Pokemon, Pokemon>() {
            @Override
            protected void saveCallResult(@NonNull Pokemon item) {
                db.pokemonDAO().insertPokemonDetail(item);
            }

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @NonNull
            @Override
            protected Flowable<Pokemon> loadFromDb() {
                return db.pokemonDAO().getPokemonDetailById(id);
            }

            @NonNull
            @Override
            protected Observable<Resource<Pokemon>> createCall() {
                return webService.getPokemonDetail(id)
                        .flatMap(pokemon -> Observable.just(pokemon == null
                                ? Resource.error("", new Pokemon())
                                : Resource.success(pokemon)));

//                Observable<Pokemon> pokemonResult = webService.getPokemonDetail(id);
//                Observable<Species> speciesResult = webService.getPokemonSpecies(id);
//                return Observable.zip(pokemonResult, speciesResult, (pokemon, species) -> {
//                    if (pokemon == null) {
//                        return Resource.error("", new Pokemon());
//                    } else {
//                        pokemon.setSpeciesDetail(species);
//                        return Resource.success(pokemon);
//                    }
//                });
            }
        }.getAsObservable();
    }
}

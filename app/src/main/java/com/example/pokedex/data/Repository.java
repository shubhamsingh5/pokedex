package com.example.pokedex.data;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pokedex.data.local.PokemonDatabase;
import com.example.pokedex.data.local.dao.PokemonDAO;
import com.example.pokedex.data.local.entity.Pokemon;
import com.example.pokedex.data.local.entity.PokemonOverview;
import com.example.pokedex.data.remote.NetworkBoundResource;
import com.example.pokedex.data.remote.Resource;
import com.example.pokedex.data.remote.api.PokeApiService;
import com.example.pokedex.data.remote.api.RetrofitClient;
import com.example.pokedex.data.remote.model.PokeApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

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
                        .concatMap(pokemon -> webService.getPokemonDetail(pokemon.getUrl()))
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
//                        .concatMap(pokemon -> webService.getPokemonDetail(pokemon.getUrl()))
//                        .toList()
//                        .toObservable();
            }
        }.getAsObservable();
    }
}

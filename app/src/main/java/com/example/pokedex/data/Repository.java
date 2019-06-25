package com.example.pokedex.data;


import android.content.Context;

import androidx.annotation.NonNull;

import com.example.pokedex.data.local.PokemonDatabase;
import com.example.pokedex.data.local.dao.PokemonDAO;
import com.example.pokedex.data.local.entity.Pokemon;
import com.example.pokedex.data.remote.NetworkBoundResource;
import com.example.pokedex.data.remote.Resource;
import com.example.pokedex.data.remote.api.PokeApiService;
import com.example.pokedex.data.remote.api.RetrofitClient;
import com.example.pokedex.data.remote.model.PokeApiResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

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

    public Observable<Resource<List<Pokemon>>> getAllPokemon() {
        return new NetworkBoundResource<List<Pokemon>, PokeApiResponse>() {

            @Override
            protected void saveCallResult(@NonNull PokeApiResponse item) {
                Observable.fromIterable(item.getResults())
                        .concatMap(pokemon -> webService.getPokemonDetail(pokemon.getUrl()))
                        .toList()
                        .subscribe(list -> db.pokemonDAO().insertPokemon(list));
            }

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @NonNull
            @Override
            protected Flowable<List<Pokemon>> loadFromDb() {
                return db.pokemonDAO().getAllPokemons();
            }

            @NonNull
            @Override
            protected Observable<Resource<PokeApiResponse>> createCall() {
                return webService.loadPokemons()
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

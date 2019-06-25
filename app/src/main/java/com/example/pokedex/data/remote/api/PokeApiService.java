package com.example.pokedex.data.remote.api;

import com.example.pokedex.data.local.entity.Pokemon;
import com.example.pokedex.data.remote.model.PokeApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface PokeApiService {

    @GET("pokemon")
    Observable<PokeApiResponse> loadPokemons();

    @GET
    Observable<Pokemon> getPokemonDetail(@Url String url);
}

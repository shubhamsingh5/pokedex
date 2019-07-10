package com.example.pokedex.data.remote.api;

import com.example.pokedex.data.local.entity.Pokemon;
import com.example.pokedex.data.local.entity.PokemonOverview;
import com.example.pokedex.data.remote.model.PokeApiResponse;
import com.example.pokedex.data.remote.model.species.Species;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PokeApiService {

    @GET("pokemon?limit=20")
    Observable<PokeApiResponse> loadPokemons(@Query("offset") int offset);

    @GET
    Observable<PokemonOverview> getPokemonOverview(@Url String url);

    @GET("pokemon/{id}")
    Observable<Pokemon> getPokemonDetail(@Path("id") int id);

    @GET("pokemon-species/{id}")
    Observable<Species> getPokemonSpecies(@Path("id") int id);


}

package com.example.pokedex.data.remote.api;

import com.example.pokedex.data.local.entity.PokemonOverview;
import com.example.pokedex.data.local.entity.Species;
import com.example.pokedex.data.remote.model.PokeApiResponse;
import com.example.pokedex.data.local.entity.MoveDetail;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PokeApiService {

    @GET("pokemon?limit=20")
    Observable<PokeApiResponse> loadPokemons(@Query("offset") int offset);

    @GET
    Observable<PokemonOverview> getPokemonOverview(@Url String url);

    @GET("pokemon-species/{id}")
    Single<Species> getPokemonSpecies(@Path("id") int id);

    @GET("move/{id}")
    Observable<MoveDetail> getMoveById(@Path("id") int id);
//
//    @GET
//    Observable<Species> getSpeciesDetail(@Url String url);

}

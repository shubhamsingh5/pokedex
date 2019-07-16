package com.example.pokedex.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.pokedex.data.local.entity.Species;
import com.example.pokedex.data.local.entity.PokemonOverview;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PokemonDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPokemonOverview(List<PokemonOverview> pokemonOverviews);

    @Query("SELECT * FROM pokemons_overview")
    Flowable<List<PokemonOverview>> getAllPokemonsOverview();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPokemonSpecies(Species species);

    @Query("SELECT * FROM pokemons_species where id=:id")
    Flowable<Species> getPokemonSpeciesById(int id);

    @Query("SELECT * FROM pokemons_overview where id=:id")
    Flowable<PokemonOverview> getPokemonDetailById(int id);
}

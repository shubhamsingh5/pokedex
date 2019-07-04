package com.example.pokedex.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.pokedex.data.local.entity.Pokemon;
import com.example.pokedex.data.local.entity.PokemonOverview;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PokemonDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPokemonOverview(List<PokemonOverview> pokemon);

    @Query("SELECT * FROM pokemons_overview")
    Flowable<List<PokemonOverview>> getAllPokemonsOverview();
}

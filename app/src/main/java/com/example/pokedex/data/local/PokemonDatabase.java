package com.example.pokedex.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.pokedex.data.local.converter.AbilityResponseTypeConverter;
import com.example.pokedex.data.local.converter.MoveResponseTypeConverter;
import com.example.pokedex.data.local.converter.StatResponseTypeConverter;
import com.example.pokedex.data.local.converter.TypeResponseTypeConverter;
import com.example.pokedex.data.local.dao.PokemonDAO;
import com.example.pokedex.data.local.entity.Pokemon;
import com.example.pokedex.data.local.entity.PokemonOverview;

@Database(entities = {Pokemon.class, PokemonOverview.class}, version = 1, exportSchema = false)
@TypeConverters({AbilityResponseTypeConverter.class, MoveResponseTypeConverter.class,
        StatResponseTypeConverter.class, TypeResponseTypeConverter.class})
public abstract class PokemonDatabase extends RoomDatabase {

    private static PokemonDatabase INSTANCE;

    public static PokemonDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PokemonDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static PokemonDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context,
                PokemonDatabase.class, "Pokemon.db")
                .allowMainThreadQueries().build();
    }

    public abstract PokemonDAO pokemonDAO();
}

package com.example.pokedex.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.pokedex.data.local.converter.AbilityResponseTypeConverter;
import com.example.pokedex.data.local.converter.EffectEntryTypeConverter;
import com.example.pokedex.data.local.converter.FlavorTextTypeConverter;
import com.example.pokedex.data.local.converter.GeneraTypeConverter;
import com.example.pokedex.data.local.converter.HabitatTypeConverter;
import com.example.pokedex.data.local.converter.MoveResponseTypeConverter;
import com.example.pokedex.data.local.converter.PokemonTypeTypeConverter;
import com.example.pokedex.data.local.converter.SpeciesTypeConverter;
import com.example.pokedex.data.local.converter.StatResponseTypeConverter;
import com.example.pokedex.data.local.converter.TypeResponseTypeConverter;
import com.example.pokedex.data.local.dao.PokemonDAO;
import com.example.pokedex.data.local.entity.MoveDetail;
import com.example.pokedex.data.local.entity.PokemonOverview;
import com.example.pokedex.data.local.entity.Species;

@Database(entities = {PokemonOverview.class, Species.class, MoveDetail.class}, version = 1, exportSchema = false)
@TypeConverters({AbilityResponseTypeConverter.class, MoveResponseTypeConverter.class,
        StatResponseTypeConverter.class, TypeResponseTypeConverter.class, SpeciesTypeConverter.class,
        FlavorTextTypeConverter.class, GeneraTypeConverter.class, HabitatTypeConverter.class, EffectEntryTypeConverter.class,
        PokemonTypeTypeConverter.class})
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
                PokemonDatabase.class, "PokemonDetail.db")
                .allowMainThreadQueries().build();
    }

    public abstract PokemonDAO pokemonDAO();
}

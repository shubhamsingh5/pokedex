package com.example.pokedex.data.local.converter;

import androidx.room.TypeConverter;

import com.example.pokedex.data.local.entity.Species;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SpeciesTypeConverter {

    @TypeConverter
    public Species fromString(String value) {
        Type listType = new TypeToken<Species>() {
        }.getType();
        Species species = new Gson().fromJson(value, listType);
        return species;
    }

    @TypeConverter
    public String fromSpecies(Species species) {
        return new Gson().toJson(species);
    }
}

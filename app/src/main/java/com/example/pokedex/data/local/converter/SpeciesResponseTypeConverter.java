package com.example.pokedex.data.local.converter;

import androidx.room.TypeConverter;

import com.example.pokedex.data.remote.model.species.SpeciesApiResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SpeciesResponseTypeConverter {

    @TypeConverter
    public SpeciesApiResponse fromString(String value) {
        Type listType = new TypeToken<SpeciesApiResponse>() {}.getType();
        SpeciesApiResponse species = new Gson().fromJson(value, listType);
        return species;
    }

    @TypeConverter
    public String fromSpeciesApiResponse(SpeciesApiResponse species) {
        return new Gson().toJson(species);
    }
}

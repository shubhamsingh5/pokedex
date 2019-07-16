package com.example.pokedex.data.local.converter;

import androidx.room.TypeConverter;

import com.example.pokedex.data.remote.model.species.Habitat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class HabitatTypeConverter {

    @TypeConverter
    public Habitat fromString(String value) {
        Type type = new TypeToken<Habitat>() {
        }.getType();
        Habitat habitat = new Gson().fromJson(value, type);
        return habitat;
    }

    @TypeConverter
    public String fromHabitat(Habitat habitat) {
        return new Gson().toJson(habitat);
    }
}

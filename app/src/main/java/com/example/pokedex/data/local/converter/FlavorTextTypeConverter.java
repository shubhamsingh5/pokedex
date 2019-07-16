package com.example.pokedex.data.local.converter;

import androidx.room.TypeConverter;

import com.example.pokedex.data.remote.model.species.FlavorTextEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FlavorTextTypeConverter {

    @TypeConverter
    public List<FlavorTextEntry> fromString(String value) {
        Type listType = new TypeToken<List<FlavorTextEntry>>() {
        }.getType();
        List<FlavorTextEntry> flavortexts = new Gson().fromJson(value, listType);
        return flavortexts;
    }

    @TypeConverter
    public String fromList(List<FlavorTextEntry> flavortexts) {
        return new Gson().toJson(flavortexts);
    }
}

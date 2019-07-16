package com.example.pokedex.data.local.converter;

import androidx.room.TypeConverter;

import com.example.pokedex.data.remote.model.species.Genera;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GeneraTypeConverter {

    @TypeConverter
    public List<Genera> fromString(String value) {
        Type listType = new TypeToken<List<Genera>>() {
        }.getType();
        List<Genera> generas = new Gson().fromJson(value, listType);
        return generas;
    }

    @TypeConverter
    public String fromList(List<Genera> generas) {
        return new Gson().toJson(generas);
    }
}

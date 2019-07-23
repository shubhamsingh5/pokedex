package com.example.pokedex.data.local.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PokemonTypeTypeConverter {

    @TypeConverter
    public com.example.pokedex.data.remote.model.type.Type fromString(String value) {
        Type typeType = new TypeToken<com.example.pokedex.data.remote.model.type.Type>() {}.getType();
        com.example.pokedex.data.remote.model.type.Type type = new Gson().fromJson(value, typeType);
        return type;
    }

    @TypeConverter
    public String fromType(com.example.pokedex.data.remote.model.type.Type type) {
        return new Gson().toJson(type);
    }

}

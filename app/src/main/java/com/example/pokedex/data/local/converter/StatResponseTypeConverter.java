package com.example.pokedex.data.local.converter;

import androidx.room.TypeConverter;

import com.example.pokedex.data.remote.model.stat.Stat;
import com.example.pokedex.data.remote.model.stat.StatApiResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StatResponseTypeConverter {

    @TypeConverter
    public List<StatApiResponse> fromString(String value) {
        Type listType = new TypeToken<List<StatApiResponse>>() {}.getType();
        List<StatApiResponse> stats = new Gson().fromJson(value, listType);
        return stats;
    }

    @TypeConverter
    public String fromList(List<StatApiResponse> stats) {
        return new Gson().toJson(stats);
    }
}

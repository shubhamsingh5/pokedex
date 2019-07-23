package com.example.pokedex.data.local.converter;

import androidx.room.TypeConverter;

import com.example.pokedex.data.remote.model.move.EffectEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class EffectEntryTypeConverter {

    @TypeConverter
    public List<EffectEntry> fromString(String value) {
        Type listType = new TypeToken<List<EffectEntry>>() {
        }.getType();
        List<EffectEntry> effectEntries = new Gson().fromJson(value, listType);
        return effectEntries;
    }

    @TypeConverter
    public String fromList(List<EffectEntry> effectEntries) {
        return new Gson().toJson(effectEntries);
    }
}

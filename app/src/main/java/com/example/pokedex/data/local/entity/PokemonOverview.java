package com.example.pokedex.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.pokedex.data.local.converter.AbilityResponseTypeConverter;
import com.example.pokedex.data.local.converter.MoveResponseTypeConverter;
import com.example.pokedex.data.local.converter.StatResponseTypeConverter;
import com.example.pokedex.data.local.converter.TypeResponseTypeConverter;
import com.example.pokedex.data.remote.model.ability.AbilityApiResponse;
import com.example.pokedex.data.remote.model.move.MoveApiResponse;
import com.example.pokedex.data.remote.model.stat.StatApiResponse;
import com.example.pokedex.data.remote.model.type.TypeApiResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "pokemons_overview")
public class PokemonOverview {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("types")
    @Expose
    @TypeConverters(TypeResponseTypeConverter.class)
    private List<TypeApiResponse> types = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TypeApiResponse> getTypes() {
        return types;
    }

    public void setTypes(List<TypeApiResponse> types) {
        this.types = types;
    }

}
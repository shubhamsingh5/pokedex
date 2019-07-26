package com.example.pokedex.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.pokedex.data.local.converter.FlavorTextTypeConverter;
import com.example.pokedex.data.local.converter.PokemonTypeTypeConverter;
import com.example.pokedex.data.remote.model.move.EffectEntry;
import com.example.pokedex.data.remote.model.species.FlavorTextEntry;
import com.example.pokedex.data.remote.model.type.Type;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "pokemons_moves")
public class MoveDetail {

    @TypeConverters(PokemonTypeTypeConverter.class)
    @SerializedName("type")
    @Expose
    private Type type;

    @SerializedName("power")
    @Expose
    private Integer power;

    @SerializedName("pp")
    @Expose
    private Integer pp;

    @SerializedName("name")
    @Expose
    private String name;

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("effect_chance")
    @Expose
    private Integer effectChance;

    @SerializedName("effect_entries")
    @Expose
    private List<EffectEntry> effectEntries = null;

    @TypeConverters(FlavorTextTypeConverter.class)
    @SerializedName("flavor_text_entries")
    @Expose
    private List<FlavorTextEntry> flavorTextEntries = null;

    @SerializedName("accuracy")
    @Expose
    private Integer accuracy;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getPp() {
        return pp;
    }

    public void setPp(Integer pp) {
        this.pp = pp;
    }

    public String getName() {
        String s = this.name.replace("-", " ");
        s = s.toUpperCase();
        return s;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEffectChance() {
        return effectChance;
    }

    public void setEffectChance(Integer effectChance) {
        this.effectChance = effectChance;
    }

    public List<EffectEntry> getEffectEntries() {
        return effectEntries;
    }

    public void setEffectEntries(List<EffectEntry> effectEntries) {
        this.effectEntries = effectEntries;
    }

    public List<FlavorTextEntry> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public void setFlavorTextEntries(List<FlavorTextEntry> flavorTextEntries) {
        this.flavorTextEntries = flavorTextEntries;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }
}

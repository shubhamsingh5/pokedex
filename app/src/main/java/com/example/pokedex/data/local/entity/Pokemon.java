package com.example.pokedex.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.pokedex.data.local.converter.AbilityResponseTypeConverter;
import com.example.pokedex.data.local.converter.SpeciesResponseTypeConverter;
import com.example.pokedex.data.local.converter.StatResponseTypeConverter;
import com.example.pokedex.data.local.converter.TypeResponseTypeConverter;
import com.example.pokedex.data.remote.model.species.SpeciesApiResponse;
import com.example.pokedex.data.remote.model.ability.AbilityApiResponse;
import com.example.pokedex.data.remote.model.move.MoveApiResponse;
import com.example.pokedex.data.remote.model.stat.StatApiResponse;
import com.example.pokedex.data.remote.model.type.TypeApiResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "pokemons")
public class Pokemon {

    @TypeConverters(AbilityResponseTypeConverter.class)
    @SerializedName("abilities")
    @Expose
    private List<AbilityApiResponse> abilities = null;

    @SerializedName("base_experience")
    @Expose
    private Integer baseExperience;

    @SerializedName("height")
    @Expose
    private Integer height;

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("location_area_encounters")
    @Expose
    private String locationAreaEncounters;

    @SerializedName("moves")
    @Expose
    private List<MoveApiResponse> moves = null;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("order")
    @Expose
    private Integer order;

    @TypeConverters(SpeciesResponseTypeConverter.class)
    @SerializedName("species")
    @Expose
    private SpeciesApiResponse species;

    @TypeConverters(StatResponseTypeConverter.class)
    @SerializedName("stats")
    @Expose
    private List<StatApiResponse> stats = null;

    @TypeConverters(TypeResponseTypeConverter.class)
    @SerializedName("types")
    @Expose
    private List<TypeApiResponse> types = null;

    @SerializedName("weight")
    @Expose
    private Integer weight;

    public List<AbilityApiResponse> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<AbilityApiResponse> abilities) {
        this.abilities = abilities;
    }

    public Integer getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(Integer baseExperience) {
        this.baseExperience = baseExperience;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocationAreaEncounters() {
        return locationAreaEncounters;
    }

    public void setLocationAreaEncounters(String locationAreaEncounters) {
        this.locationAreaEncounters = locationAreaEncounters;
    }

    public List<MoveApiResponse> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveApiResponse> moves) {
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public SpeciesApiResponse getSpecies() {
        return species;
    }

    public void setSpecies(SpeciesApiResponse species) {
        this.species = species;
    }

    public List<StatApiResponse> getStats() {
        return stats;
    }

    public void setStats(List<StatApiResponse> stats) {
        this.stats = stats;
    }

    public List<TypeApiResponse> getTypes() {
        return types;
    }

    public void setTypes(List<TypeApiResponse> types) {
        this.types = types;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
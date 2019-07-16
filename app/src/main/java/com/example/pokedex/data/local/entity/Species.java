package com.example.pokedex.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import com.example.pokedex.data.local.converter.FlavorTextTypeConverter;
import com.example.pokedex.data.local.converter.GeneraTypeConverter;
import com.example.pokedex.data.local.converter.HabitatTypeConverter;
import com.example.pokedex.data.remote.model.species.FlavorTextEntry;
import com.example.pokedex.data.remote.model.species.Genera;
import com.example.pokedex.data.remote.model.species.Habitat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "pokemons_species")
public class Species {

    @SerializedName("base_happiness")
    @Expose
    private Integer baseHappiness;

    @SerializedName("capture_rate")
    @Expose
    private Integer captureRate;
//
//    @SerializedName("egg_groups")
//    @Expose
//    private List<EggGroup> eggGroups = null;
//
//    @SerializedName("evolution_chain")
//    @Expose
//    private EvolutionChain evolutionChain;

    @TypeConverters(FlavorTextTypeConverter.class)
    @SerializedName("flavor_text_entries")
    @Expose
    private List<FlavorTextEntry> flavorTextEntries = null;

    @SerializedName("gender_rate")
    @Expose
    private Integer genderRate;

    @TypeConverters(GeneraTypeConverter.class)
    @SerializedName("genera")
    @Expose
    private List<Genera> genera = null;
//
//    @SerializedName("generation")
//    @Expose
//    private Generation generation;
//
//    @SerializedName("growth_rate")
//    @Expose
//    private GrowthRate growthRate;

    @TypeConverters(HabitatTypeConverter.class)
    @SerializedName("habitat")
    @Expose
    private Habitat habitat;

    @SerializedName("has_gender_differences")
    @Expose
    private Boolean hasGenderDifferences;

    @SerializedName("hatch_counter")
    @Expose
    private Integer hatchCounter;

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("order")
    @Expose
    private Integer order;
//
//    @SerializedName("pokedex_numbers")
//    @Expose
//    private List<PokedexNumber> pokedexNumbers = null;

    public Integer getBaseHappiness() {
        return baseHappiness;
    }

    public void setBaseHappiness(Integer baseHappiness) {
        this.baseHappiness = baseHappiness;
    }

    public Integer getCaptureRate() {
        return captureRate;
    }

    public void setCaptureRate(Integer captureRate) {
        this.captureRate = captureRate;
    }

//
//    public List<EggGroup> getEggGroups() {
//        return eggGroups;
//    }

//    public void setEggGroups(List<EggGroup> eggGroups) {
//        this.eggGroups = eggGroups;
//    }
//
//    public EvolutionChain getEvolutionChain() {
//        return evolutionChain;
//    }
//
//    public void setEvolutionChain(EvolutionChain evolutionChain) {
//        this.evolutionChain = evolutionChain;
//    }

    public List<FlavorTextEntry> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public void setFlavorTextEntries(List<FlavorTextEntry> flavorTextEntries) {
        this.flavorTextEntries = flavorTextEntries;
    }


    public Integer getGenderRate() {
        return genderRate;
    }

    public void setGenderRate(Integer genderRate) {
        this.genderRate = genderRate;
    }

    public List<Genera> getGenera() {
        return genera;
    }

    public void setGenera(List<Genera> genera) {
        this.genera = genera;
    }
//
//    public Generation getGeneration() {
//        return generation;
//    }
//
//    public void setGeneration(Generation generation) {
//        this.generation = generation;
//    }
//
//    public GrowthRate getGrowthRate() {
//        return growthRate;
//    }
//
//    public void setGrowthRate(GrowthRate growthRate) {
//        this.growthRate = growthRate;
//    }

    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public Boolean getHasGenderDifferences() {
        return hasGenderDifferences;
    }

    public void setHasGenderDifferences(Boolean hasGenderDifferences) {
        this.hasGenderDifferences = hasGenderDifferences;
    }

    public Integer getHatchCounter() {
        return hatchCounter;
    }

    public void setHatchCounter(Integer hatchCounter) {
        this.hatchCounter = hatchCounter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
//
//    public List<PokedexNumber> getPokedexNumbers() {
//        return pokedexNumbers;
//    }
//
//    public void setPokedexNumbers(List<PokedexNumber> pokedexNumbers) {
//        this.pokedexNumbers = pokedexNumbers;
//    }

}
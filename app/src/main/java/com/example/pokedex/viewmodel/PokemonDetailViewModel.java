package com.example.pokedex.viewmodel;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokedex.data.Repository;
import com.example.pokedex.data.local.entity.PokemonOverview;
import com.example.pokedex.data.remote.model.PokemonDetail;
import com.example.pokedex.data.remote.Resource;
import com.example.pokedex.data.local.entity.Species;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class PokemonDetailViewModel extends ViewModel {
    private Repository repo;
    private MutableLiveData<Resource<PokemonOverview>> pokemonData;
    private MutableLiveData<Species> speciesData;
    private MediatorLiveData<Resource<PokemonDetail>> pokemonDetail;

    public void init(Context context) {
        if (pokemonDetail != null) {
            return;
        }
        repo = Repository.getInstance(context);
        pokemonData = new MutableLiveData<>();
        speciesData = new MutableLiveData<>();
        pokemonDetail = new MediatorLiveData<>();
    }

    public void getPokemonDetail(int id) {
        getPokemonData(id);
        pokemonDetail.addSource(getPokemonData(), res -> {
            getPokemonSpecies(id);
            PokemonDetail detail = null;
            if (res != null && res.isLoaded()) {
                detail = new PokemonDetail(res.data);
                pokemonDetail.setValue(Resource.success(detail));
            }
        });

        pokemonDetail.addSource(getSpeciesData(), res -> {
            PokemonDetail detail = pokemonDetail.getValue().data;
            if (res != null) {
                detail.setSpecies(res);
                pokemonDetail.setValue(Resource.success(detail));
            }
        });
    }

    private void getPokemonData(int id) {
        repo.getPokemonDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> getPokemonData().postValue(res));
    }

    private void getPokemonSpecies(int id) {
        repo.getPokemonSpeciesFromDb(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(species -> getSpeciesData().postValue(species));
    }

    //TODO: use with NetworkBoundResource implementation
//    private void getPokemonSpecies(int id) {
//        repo.getPokemonSpecies(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(res -> getSpeciesData().postValue(res));
//    }

    public MutableLiveData<Resource<PokemonOverview>> getPokemonData() {
        return pokemonData;
    }

    public MutableLiveData<Species> getSpeciesData() {
        return speciesData;
    }

    //TODO: use with NetworkBoundResource implementation
//    public MutableLiveData<Resource<Species>> getSpeciesData() {
//        return speciesData;
//    }

    public MediatorLiveData<Resource<PokemonDetail>> getPokemon() {
        return pokemonDetail;
    }
}

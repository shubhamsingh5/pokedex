package com.example.pokedex.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokedex.data.Repository;
import com.example.pokedex.data.local.entity.Pokemon;
import com.example.pokedex.data.remote.Resource;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PokemonViewModel extends ViewModel {
    private Repository repo;
    private MutableLiveData<Resource<List<Pokemon>>> data;

    public void init(Context context) {
        if (data != null) {
            return;
        }
        repo = Repository.getInstance(context);
        data = new MutableLiveData<>();
    }

    public void loadPokemonFromApi(int offset) {
        repo.getAllPokemon(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> getPokemons().postValue(res));
    }

    public MutableLiveData<Resource<List<Pokemon>>> getPokemons() {
        return data;
    }
}

package com.example.pokedex.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokedex.data.Repository;
import com.example.pokedex.data.local.entity.Pokemon;
import com.example.pokedex.data.remote.Resource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class PokemonDetailViewModel extends ViewModel {
    private Repository repo;
    private MutableLiveData<Resource<Pokemon>> data;

    public void init(Context context) {
        if (data != null) {
            return;
        }
        repo = Repository.getInstance(context);
        data = new MutableLiveData<>();
    }

    public void getPokemonDetailFromApi(int id) {
        repo.getPokemonDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> getPokemon().postValue(res));
    }

    public MutableLiveData<Resource<Pokemon>> getPokemon() {
        return data;
    }
}

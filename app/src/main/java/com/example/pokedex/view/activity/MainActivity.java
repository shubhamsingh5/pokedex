package com.example.pokedex.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.pokedex.R;
import com.example.pokedex.data.local.entity.PokemonOverview;
import com.example.pokedex.view.adapter.PokemonListAdapter;
import com.example.pokedex.viewmodel.PokemonListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PokemonListAdapter.RecyclerViewClickListener {

    private RecyclerView rv;
    GridLayoutManager gridLayoutManager;
    ProgressBar pb;
    PokemonListAdapter adapter;
    PokemonListViewModel vm;
    List<PokemonOverview> pokemonOverviews = new ArrayList<>();
    private boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount, offset, stored;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getPreferences(MODE_PRIVATE);
        int initVal = sharedPreferences.getInt("stored", 0);
        rv = findViewById(R.id.rv);
        pb = findViewById(R.id.progressBar);
        pokemonOverviews = new ArrayList<>();
        initView();
        vm = ViewModelProviders.of(this).get(PokemonListViewModel.class);
        vm.init(this, initVal);
        offset = 0;
        vm.loadPokemonFromApi(offset);
        vm.getPokemons().observe(this, resource -> {
            if (resource.isLoading()) {
                pb.setVisibility(View.VISIBLE);
            }
            else if (resource.isLoaded() && !resource.data.isEmpty()) {
                pb.setVisibility(View.GONE);
                pokemonOverviews.clear();
                pokemonOverviews.addAll(resource.data);
                stored = resource.data.size();
                loading = true;
                setupRV();
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //TODO: fix pagination
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //check for scroll down
                if (dy > 0) {
                    visibleItemCount = gridLayoutManager.getChildCount();
                    totalItemCount = gridLayoutManager.getItemCount();
                    pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();
                    offset = totalItemCount;
                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            //Do pagination.. i.e. fetch new data
                            vm.loadPokemonFromApi(offset);
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(gridLayoutManager);
    }

    private void setupRV() {
        if (adapter == null) {
            adapter = new PokemonListAdapter(pokemonOverviews, this, this);
            rv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    //handle click events
    @Override
    public void onClick(int pos) {
        Intent intent = new Intent(this, PokemonDetailActivity.class);
        int message = pos + 1;
        intent.putExtra("pokemon_id", message);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onStop();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPref.edit();
        editor.putInt("stored", stored);
        editor.commit();
    }
}

package com.example.pokedex.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.pokedex.R;
import com.example.pokedex.data.local.entity.PokemonOverview;
import com.example.pokedex.view.adapter.PokemonListAdapter;
import com.example.pokedex.viewmodel.PokemonListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PokemonListAdapter.RecyclerViewClickListener {

    private RecyclerView rv;
    GridLayoutManager gridLayoutManager;
    PokemonListAdapter adapter;
    PokemonListViewModel vm;
    List<PokemonOverview> pokemonOverviews = new ArrayList<>();
    private boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount, offset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        pokemonOverviews = new ArrayList<>();
        initView();
        vm = ViewModelProviders.of(this).get(PokemonListViewModel.class);
        vm.init(this);
        offset = 0;
        vm.loadPokemonFromApi(offset);
        vm.getPokemons().observe(this, resource -> {
            if (!resource.data.isEmpty()) {
                pokemonOverviews.clear();
                pokemonOverviews.addAll(resource.data);
                setupRV();
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //check for scroll down
                if (dy > 0) {
                    visibleItemCount = gridLayoutManager.getChildCount();
                    totalItemCount = gridLayoutManager.getItemCount();
                    pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            //Do pagination.. i.e. fetch new data
                            offset += 20;
                            vm.loadPokemonFromApi(offset);
                            loading = true;
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
        String message = Integer.toString(pos + 1);
        intent.putExtra("pokemon id", message);
        startActivity(intent);
    }
}

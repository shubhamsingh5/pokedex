package com.example.pokedex.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pokedex.R;
import com.example.pokedex.data.local.entity.Pokemon;
import com.example.pokedex.view.adapter.PokemonListAdapter;
import com.example.pokedex.viewmodel.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    GridLayoutManager gridLayoutManager;
    PokemonListAdapter adapter;
    PokemonViewModel vm;
    List<Pokemon> pokemons = new ArrayList<>();
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount, offset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        pokemons = new ArrayList<>();
        initView();
        vm = ViewModelProviders.of(this).get(PokemonViewModel.class);
        vm.init(this);
        offset = 0;
        vm.loadPokemonFromApi(offset);
        vm.getPokemons().observe(this, resource -> {
            if (!resource.data.isEmpty()) {
                pokemons.clear();
                pokemons.addAll(resource.data);
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
                    pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
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
            adapter = new PokemonListAdapter(pokemons, this);
            rv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}

package com.example.pokedex.view;

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
    PokemonListAdapter adapter;
    PokemonViewModel vm;
    List<Pokemon> pokemons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        pokemons = new ArrayList<>();
        initView();
        vm = ViewModelProviders.of(this).get(PokemonViewModel.class);
        vm.init(this);
        vm.loadPokemonFromApi();
        vm.getPokemons().observe(this, resource -> {
            if (!resource.data.isEmpty()) {
                pokemons = resource.data;
                setupRV();
            }
        });
    }

    private void initView() {
        rv.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
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

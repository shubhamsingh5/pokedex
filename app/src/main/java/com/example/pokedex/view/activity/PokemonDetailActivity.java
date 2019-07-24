package com.example.pokedex.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pokedex.R;
import com.example.pokedex.utils.NavigationUtils;
import com.example.pokedex.view.fragment.PokemonDetailBaseFragment;
import com.example.pokedex.view.fragment.PokemonMoveFragment;
import com.example.pokedex.view.fragment.PokemonStatFragment;
import com.example.pokedex.viewmodel.PokemonDetailViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import android.view.MenuItem;
import android.widget.TextView;

public class PokemonDetailActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private PokemonDetailViewModel vm;
    private int pokemonId;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_summary:
                        NavigationUtils.setFragment(new PokemonDetailBaseFragment(), PokemonDetailActivity.this, R.id.detail_fragment);
                        return true;
                    case R.id.navigation_moves:
                        NavigationUtils.setFragment(new PokemonMoveFragment(), PokemonDetailActivity.this, R.id.detail_fragment);
                        return true;
                    case R.id.navigation_stats:
                        NavigationUtils.setFragment(new PokemonStatFragment(), PokemonDetailActivity.this, R.id.detail_fragment);
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        Intent intent = getIntent();
        pokemonId = intent.getIntExtra("pokemon_id", 1);


        vm = ViewModelProviders.of(this).get(PokemonDetailViewModel.class);
        vm.init(this);
        vm.getPokemonDetail(pokemonId);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().performIdentifierAction(R.id.nav_view, 0);
        navView.setSelectedItemId(R.id.navigation_summary);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

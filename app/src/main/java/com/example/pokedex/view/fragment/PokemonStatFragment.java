package com.example.pokedex.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.pokedex.R;
import com.example.pokedex.data.remote.model.PokemonDetail;
import com.example.pokedex.databinding.PokemonStatsFragmentBinding;
import com.example.pokedex.utils.ColorUtils;
import com.example.pokedex.viewmodel.PokemonDetailViewModel;

public class PokemonStatFragment extends Fragment {

    private PokemonDetailViewModel vm;
    private PokemonStatsFragmentBinding binding;
    private PokemonDetail pokemonDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProviders.of(this.getActivity()).get(PokemonDetailViewModel.class);
        //TODO: add progress bar
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.pokemon_stats_fragment, container, false);
        binding.setLifecycleOwner(this.getActivity());

        vm.getPokemon().observe(getViewLifecycleOwner(), pokemonResource -> {
            if (pokemonResource.isLoaded()) {
                pokemonDetail = pokemonResource.data;
                binding.setPokemonDetail(pokemonDetail);
                if (pokemonDetail.getTypes().size() > 1) {
                    String type1 = pokemonDetail.getTypes().get(1).getType().getName();
                    binding.detailConstraintLayout.setBackgroundColor(ColorUtils.setColorBasedOnType(type1, this.getActivity()));

                } else {
                    String type0 = pokemonDetail.getTypes().get(0).getType().getName();
                    binding.detailConstraintLayout.setBackgroundColor(ColorUtils.setColorBasedOnType(type0, this.getActivity()));
                }
            }
        });

        return binding.getRoot();
    }
}

package com.example.pokedex.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.R;
import com.example.pokedex.data.remote.model.PokemonDetail;
import com.example.pokedex.databinding.PokemonBaseFragmentBinding;
import com.example.pokedex.utils.ColorUtils;
import com.example.pokedex.viewmodel.PokemonDetailViewModel;

public class PokemonDetailBaseFragment extends Fragment {

    private PokemonBaseFragmentBinding binding;
    private PokemonDetailViewModel vm;
    private PokemonDetail pokemonDetail;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProviders.of(getActivity()).get(PokemonDetailViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.pokemon_base_fragment, container, false);
        vm.getPokemon().observe(getActivity(), pokemonResource -> {
            if (pokemonResource.isLoaded() && pokemonResource != null) {
                pokemonDetail = pokemonResource.data;
                //initialise view elements
                //type color and type name
                if (pokemonDetail.getTypes().size() > 1) {
                    String type0 = pokemonDetail.getTypes().get(0).getType().getName();
                    String type1 = pokemonDetail.getTypes().get(1).getType().getName();
                    binding.detailType1.setText(type0);
                    binding.detailType1.setBackgroundColor(ColorUtils.setColorBasedOnType(type0, getContext()));
                    binding.detailConstraintLayout.setBackgroundColor(ColorUtils.setColorBasedOnType(type1, getContext()));
                    binding.detailType2.setText(type1);
                    binding.detailType2.setBackgroundColor(ColorUtils.setColorBasedOnType(type1, getContext()));
                } else {
                    String type0 = pokemonDetail.getTypes().get(0).getType().getName();
                    binding.detailType1.setText(type0);
                    binding.detailType1.setBackgroundColor(ColorUtils.setColorBasedOnType(type0, getContext()));
                    binding.detailConstraintLayout.setBackgroundColor(ColorUtils.setColorBasedOnType(type0, getContext()));
                    binding.detailType2.setVisibility(View.GONE);
                }

                binding.detailAbility1.setText(pokemonDetail.getAbilities().get(0).getAbility().getName());
                if (pokemonDetail.getAbilities().size() > 1) {
                    binding.detailAbility2.setText(pokemonDetail.getAbilities().get(1).getAbility().getName());
                } else {
                    binding.detailAbility2.setVisibility(View.GONE);
                }
                binding.setPokemonDetail(pokemonDetail);

                //image of pokemonDetail


                //set background
            }
        });
        return binding.getRoot();
    }
}

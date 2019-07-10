package com.example.pokedex.view.fragment;

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
import com.example.pokedex.data.local.entity.Pokemon;
import com.example.pokedex.databinding.PokemonBaseFragmentBinding;
import com.example.pokedex.viewmodel.PokemonDetailViewModel;

public class PokemonDetailBaseFragment extends Fragment {

    private PokemonBaseFragmentBinding binding;
    private PokemonDetailViewModel vm;
    private Pokemon pokemon;

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
                pokemon = pokemonResource.data;
                //initialise view elements
                //type color and type name
                binding.detailType1.setText(pokemon.getTypes().get(0).getType().getName());
                binding.detailType2.setText(pokemon.getTypes().get(1).getType().getName());
                binding.setPokemon(pokemon);

                //image of pokemon
                Glide.with(getActivity())
                        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getId() + ".png")
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.detailImg);

                //set background
                setCLBackground(pokemon.getTypes().get(1).getType().getName());
            }
        });
        return binding.getRoot();
    }


    private void setCLBackground(String type) {
        switch (type) {
            case "fire":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.fireType));
                break;
            case "water":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.waterType));
                break;
            case "electric":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.electricType));
                break;
            case "grass":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grassType));
                break;
            case "ice":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.iceType));
                break;
            case "fighting":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.fightingType));
                break;
            case "poison":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.poisonType));
                break;
            case "ground":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.groundType));
                break;
            case "flying":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.flyingType));
                break;
            case "psychic":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.psychicType));
                break;
            case "bug":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.bugType));
                break;
            case "rock":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.rockType));
                break;
            case "ghost":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.ghostType));
                break;
            case "dragon":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.dragonType));
                break;
            case "dark":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.darkType));
                break;
            case "steel":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.steelType));
                break;
            case "fairy":
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.fairyType));
                break;
            default:
                binding.detailConstraintLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.normalType));
        }
    }


}

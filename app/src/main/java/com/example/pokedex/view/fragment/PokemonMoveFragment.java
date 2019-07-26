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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.data.local.entity.MoveDetail;
import com.example.pokedex.data.remote.model.PokemonDetail;
import com.example.pokedex.databinding.PokemonMovesFragmentBinding;
import com.example.pokedex.utils.ColorUtils;
import com.example.pokedex.view.adapter.MoveListAdapter;
import com.example.pokedex.viewmodel.PokemonDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class PokemonMoveFragment extends Fragment {

    private PokemonDetailViewModel vm;
    private LinearLayoutManager layoutManager;
    private PokemonDetail pokemonDetail;
    private MoveListAdapter moveListAdapter;
    private PokemonMovesFragmentBinding binding;
    private List<MoveDetail> moves = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProviders.of(this.getActivity()).get(PokemonDetailViewModel.class);
        //TODO: add progress bar
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.pokemon_moves_fragment, container, false);
        binding.setLifecycleOwner(this.getActivity());
        layoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false);
        binding.moveRv.setLayoutManager(layoutManager);
        binding.moveRv.addItemDecoration(new DividerItemDecoration(this.getActivity(),
                DividerItemDecoration.VERTICAL));

        vm.getPokemon().observe(getViewLifecycleOwner(), pokemonResource -> {
            pokemonDetail = pokemonResource.data;
            if (pokemonDetail.getMoves() != null) {
                binding.progressBar.setVisibility(View.GONE);
                moves.clear();
                moves.addAll(pokemonDetail.getMoves());
            } else {
                binding.progressBar.setVisibility(View.VISIBLE);
            }
            binding.setPokemonDetail(pokemonDetail);
            if (pokemonDetail.getTypes().size() > 1) {
                String type1 = pokemonDetail.getTypes().get(1).getType().getName();
                binding.detailConstraintLayout.setBackgroundColor(ColorUtils.setColorBasedOnType(type1, this.getActivity()));

            } else {
                String type0 = pokemonDetail.getTypes().get(0).getType().getName();
                binding.detailConstraintLayout.setBackgroundColor(ColorUtils.setColorBasedOnType(type0, this.getActivity()));
            }
            setupRV();

        });
        return binding.getRoot();
    }

    private void setupRV() {
        if (moveListAdapter == null) {
            moveListAdapter = new MoveListAdapter(moves, getContext());
            binding.moveRv.setAdapter(moveListAdapter);
        } else {
            moveListAdapter.notifyDataSetChanged();
        }
    }
}

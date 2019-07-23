package com.example.pokedex.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.data.remote.model.PokemonDetail;
import com.example.pokedex.data.local.entity.MoveDetail;
import com.example.pokedex.view.adapter.MoveListAdapter;
import com.example.pokedex.viewmodel.PokemonDetailViewModel;

import java.util.List;

public class PokemonMoveFragment extends Fragment {

    private PokemonDetailViewModel vm;
    private RecyclerView rv;
    private LinearLayoutManager layoutManager;
    private PokemonDetail pokemonDetail;
    private MoveListAdapter moveListAdapter;
    private List<MoveDetail> moves;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProviders.of(this.getActivity()).get(PokemonDetailViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.pokemon_moves_fragment, container, false);
        rv = v.findViewById(R.id.move_rv);
        layoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new DividerItemDecoration(this.getActivity(),
                DividerItemDecoration.VERTICAL));
        vm.getMoveData().observe(this.getActivity(), pokemonResource -> {
            if (pokemonResource.isLoaded()) {
                moves = pokemonResource.data;
                setupRV();
            }
        });
        return v;
    }

    private void setupRV() {
        if (moveListAdapter == null) {
            moveListAdapter = new MoveListAdapter(moves);
            rv.setAdapter(moveListAdapter);
        } else {
            moveListAdapter.notifyDataSetChanged();
        }
    }
}

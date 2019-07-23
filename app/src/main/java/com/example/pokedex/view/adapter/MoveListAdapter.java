package com.example.pokedex.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.data.local.entity.MoveDetail;


import java.util.List;

public class MoveListAdapter extends RecyclerView.Adapter<MoveListAdapter.MoveViewHolder> {

    private List<MoveDetail> moves;

    public MoveListAdapter(List<MoveDetail> moves) {
        this.moves = moves;
    }

    public class MoveViewHolder extends RecyclerView.ViewHolder {
        public TextView moveName;
        public TextView movePwr;
        public TextView moveAcc;
        public TextView movePP;
        public TextView moveEffect;

        public MoveViewHolder(@NonNull View itemView) {
            super(itemView);
            moveName = itemView.findViewById(R.id.move_name);
            moveEffect = itemView.findViewById(R.id.move_effect);
            movePwr = itemView.findViewById(R.id.move_pwr);
            moveAcc = itemView.findViewById(R.id.move_acc);
            movePP = itemView.findViewById(R.id.move_pp);
        }
    }

    @NonNull
    @Override
    public MoveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.move_list_item, parent, false);
        return new MoveListAdapter.MoveViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoveViewHolder holder, int position) {
        MoveDetail move = moves.get(position);
        holder.moveName.setText(move.getName());
        holder.moveEffect.setText(move.getEffectEntries().get(0).getShortEffect());
        if (move.getAccuracy() != null) holder.moveAcc.setText(Integer.toString(move.getAccuracy()));
        else holder.moveAcc.setText("-");

        if (move.getPower() != null) holder.movePwr.setText(Integer.toString(move.getPower()));
        else holder.movePwr.setText("-");

        if (move.getPp() != null) holder.movePP.setText(Integer.toString(move.getPp()));
        else holder.movePP.setText("-");
    }

    @Override
    public int getItemCount() {
        return moves.size();
    }
}

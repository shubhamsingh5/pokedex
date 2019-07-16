package com.example.pokedex.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.R;
import com.example.pokedex.data.local.entity.PokemonOverview;
import com.example.pokedex.data.remote.model.type.TypeApiResponse;

import java.util.List;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.PokedexEntryViewHolder> {
    private final List<PokemonOverview> pokemonOverviews;
    private RecyclerViewClickListener listener;
    private Context context;

    public class PokedexEntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView dex_num;
        public TextView list_num;
        public ImageView image;
        public CardView cv;

        RecyclerViewClickListener recyclerViewClickListener;


        public PokedexEntryViewHolder(View view, RecyclerViewClickListener recyclerViewClickListener) {
            super(view);
            name = view.findViewById(R.id.poke_name);
            dex_num = view.findViewById(R.id.dex_num);
            list_num = view.findViewById(R.id.list_num);
            image = view.findViewById(R.id.poke_img);
            cv = view.findViewById(R.id.poke_cv);
            this.recyclerViewClickListener = recyclerViewClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewClickListener.onClick(getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(int pos);
    }


    public PokemonListAdapter(List<PokemonOverview> pokemonOverviews, Context context, RecyclerViewClickListener listener) {
        this.listener = listener;
        this.pokemonOverviews = pokemonOverviews;
        this.context = context;
    }

    @NonNull
    @Override
    public PokedexEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_list_item, parent, false);
        return new PokedexEntryViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexEntryViewHolder holder, int position) {
        PokemonOverview result = pokemonOverviews.get(position);
        holder.name.setText(result.getName().toUpperCase());
        holder.dex_num.setText(Integer.toString(result.getId()));
        holder.list_num.setText(Integer.toString(position));
        List<TypeApiResponse> types = pokemonOverviews.get(position).getTypes();
        String type = types.get(types.size()-1).getType().getName();
        setCardBackground(type, holder);

        int id = result.getId();
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return pokemonOverviews.size();
    }

    private void setCardBackground(String type, @NonNull PokedexEntryViewHolder holder) {
        switch (type) {
            case "fire":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fireType));
                break;
            case "water":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.waterType));
                break;
            case "electric":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.electricType));
                break;
            case "grass":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grassType));
                break;
            case "ice":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.iceType));
                break;
            case "fighting":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fightingType));
                break;
            case "poison":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.poisonType));
                break;
            case "ground":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.groundType));
                break;
            case "flying":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.flyingType));
                break;
            case "psychic":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.psychicType));
                break;
            case "bug":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bugType));
                break;
            case "rock":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.rockType));
                break;
            case "ghost":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.ghostType));
                break;
            case "dragon":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.dragonType));
                break;
            case "dark":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.darkType));
                break;
            case "steel":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.steelType));
                break;
            case "fairy":
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fairyType));
                break;
            default:
                holder.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.normalType));
        }
    }

}

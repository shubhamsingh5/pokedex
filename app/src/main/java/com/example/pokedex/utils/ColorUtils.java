package com.example.pokedex.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.pokedex.R;

public class ColorUtils {

    public static int setColorBasedOnType(String type, Context context) {
        switch (type) {
            case "fire":
                return ContextCompat.getColor(context, R.color.fireType);
            case "water":
                return ContextCompat.getColor(context, R.color.waterType);
            case "electric":
                return ContextCompat.getColor(context, R.color.electricType);
            case "grass":
                return ContextCompat.getColor(context, R.color.grassType);
            case "ice":
                return ContextCompat.getColor(context, R.color.iceType);
            case "fighting":
                return ContextCompat.getColor(context, R.color.fightingType);
            case "poison":
                return ContextCompat.getColor(context, R.color.poisonType);
            case "ground":
                return ContextCompat.getColor(context, R.color.groundType);
            case "flying":
                return ContextCompat.getColor(context, R.color.flyingType);
            case "psychic":
                return ContextCompat.getColor(context, R.color.psychicType);
            case "bug":
                return ContextCompat.getColor(context, R.color.bugType);
            case "rock":
                return ContextCompat.getColor(context, R.color.rockType);
            case "ghost":
                return ContextCompat.getColor(context, R.color.ghostType);
            case "dragon":
                return ContextCompat.getColor(context, R.color.dragonType);
            case "dark":
                return ContextCompat.getColor(context, R.color.darkType);
            case "steel":
                return ContextCompat.getColor(context, R.color.steelType);
            case "fairy":
                return ContextCompat.getColor(context, R.color.fairyType);
            default:
                return ContextCompat.getColor(context, R.color.normalType);
        }
    }
}

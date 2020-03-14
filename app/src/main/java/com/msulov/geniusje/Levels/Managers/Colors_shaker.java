package com.msulov.geniusje.Levels.Managers;

import android.content.res.Resources;
import android.util.Log;

import androidx.core.graphics.ColorUtils;

import com.msulov.geniusje.Levels.Managers.Interfaces.Colors_manager;
import com.msulov.geniusje.R;

import java.util.Random;

public class Colors_shaker implements Colors_manager {



    public static final int[] ID_COLORS = new int[]{
            R.color.colorGameGREEN,
            R.color.colorGameBLUE,
            R.color.colorGameRED
    };

    public static int COUNT = ID_COLORS.length;

    public static int DIFFICULT = 3;

    private Resources resources;
    private int[] colors_shaked;
    private int[] colors_mixed;

    @Override
    public Colors_shaker add(int color) {
        return this;
    }

    @Override
    public Colors_shaker remove(int color) {
        return this;
    }

    @Override
    public Colors_shaker shake() {
        colors_shaked = ID_COLORS.clone();
        Memory_game.getShakedArray(colors_shaked);
        return this;
    }

    @Override
    public Colors_shaker setResources(Resources resources) {
        this.resources = resources;
        return this;
    }

    @Override
    public Colors_shaker mix(int count) {
        colors_mixed = new int[count];
        Random random = new Random();
        for(int i = 0;i<count;i++){
            colors_mixed[i] = colors_shaked[random.nextInt(colors_shaked.length)];
        }
        return this;
    }

    @Override
    public int[] get() {
        return colors_mixed;
    }

    @Override
    public int mixColors(int color_id_1, int color_id_2) {
        return ColorUtils.blendARGB(resources.getColor(color_id_1),resources.getColor(color_id_2),0.5F);

    }

    @Override
    public int mixColorsValue(int color_value_1, int color_value_2,int count) {
        float ratio = 1F;
        Log.d("FLOAT",(1F/count)+" ");
        return ColorUtils.blendARGB(color_value_1,color_value_2,1F/count);
    }
}

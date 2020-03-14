package com.msulov.geniusje.Levels.Managers.Interfaces;

import android.content.res.Resources;

import com.msulov.geniusje.Levels.Managers.Colors_shaker;

public interface Colors_manager {


    Colors_shaker add(int color);

    Colors_shaker remove(int color);

    Colors_shaker shake();

    Colors_shaker setResources(Resources resources);

    Colors_shaker mix(int count);

    int[] get();

    int mixColors(int color_id_1,int color_id_2);

    int mixColorsValue(int color_value_1,int color_value_2,int color);




}

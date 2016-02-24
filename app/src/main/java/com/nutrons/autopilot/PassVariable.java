package com.nutrons.autopilot;

import android.content.Context;

import java.io.File;

/**
 * Created by Lydia on 2/23/2016.
 */
public class PassVariable {
    PathsFragment cat = new PathsFragment();
    PassVariable(){
        final File[] pathFiles = cat.context.getDir("NUTRONsCAT", Context.MODE_PRIVATE).listFiles();
    }
}
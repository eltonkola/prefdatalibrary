package com.eltonkola.prefdatalibrary;

import android.util.Log;

/**
 * Created by elton on 5/19/15.
 */
public class Utils {

    public static void log(String string) {
        if(BuildConfig.DEBUG )
            Log.v("prefData", string);
    }

}

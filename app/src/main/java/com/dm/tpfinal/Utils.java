package com.dm.tpfinal;

import android.content.Context;

// Classe d'utilitaires utilisés au sein du projet
public class Utils {

    public static int conversionDpPx(Context context, float dp) {
        float px = dp * context.getResources().getDisplayMetrics().density;
        return (int)px;
    }

}

package com.wuxiankeneng.factory.helper;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Arrays;

public class DbHelper {
    public static <Model extends LitePalSupport> boolean save(Model model) {
        return model.save();
    }

    @SafeVarargs
    public static <Model extends LitePalSupport> void saveAll(Model... models) {
        LitePal.saveAll(Arrays.asList(models));
    }
}

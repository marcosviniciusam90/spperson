package com.mvam.spperson.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class BeanUtils {

    /**
     * Clona objeto sem precisar serializar
     * @return Clone do objeto recebido por parâmetro
     */
    public static <T> T clone(T obj) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(obj), (Type) obj.getClass());
    }
}

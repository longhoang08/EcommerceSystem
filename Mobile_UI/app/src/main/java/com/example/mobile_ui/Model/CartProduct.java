package com.example.mobile_ui.Model;


import com.google.gson.Gson;


import org.json.JSONArray;

import java.util.ArrayList;

public class CartProduct {
    ArrayList<String> id = new ArrayList<>();
    public void addCart(String idPro) {
        if (!id.contains(idPro)) {
            id.add(idPro);
        }
    }
    public void removeCart(String idPro) {
        int pos = id.indexOf(idPro);
        id.remove(pos);
    }
    public String convertToString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public CartProduct convertToObject(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, CartProduct.class);
    }
    public JSONArray getIdPro() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < id.size(); i++) {
            jsonArray.put(id.get(i));
        }
        return jsonArray;
    }
}

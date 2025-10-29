package com.example.introductiontomobilesystems;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HabitsStorage {
    private static final String PREF = "habits_pref";
    private static final String KEY = "habits_json";
    private SharedPreferences prefs;

    public HabitsStorage(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public List<Habit> load() {
        String s = prefs.getString(KEY, null);
        List<Habit> list = new ArrayList<>();
        if (s == null) {
            list.add(new Habit("lorem ipsum","daily",true,false));
            list.add(new Habit("froggen machen","weekly",true,false));
            list.add(new Habit("water drinken","daily",true,true));
            list.add(new Habit("tőzken scielen","weekends",false,false));
            save(list);
            return list;
        }

        try {
            JSONArray arr = new JSONArray(s);
            for (int i=0;i<arr.length();i++){
                list.add(new Habit(arr.getJSONObject(i)));
            }
        } catch (JSONException e) { e.printStackTrace(); }
        return list;
    }

    public void save(List<Habit> list) {
        JSONArray arr = new JSONArray();
        try {
            for (Habit h: list) arr.put(h.toJson());
            prefs.edit().putString(KEY, arr.toString()).apply();
        } catch (JSONException e) { e.printStackTrace(); }
    }
}
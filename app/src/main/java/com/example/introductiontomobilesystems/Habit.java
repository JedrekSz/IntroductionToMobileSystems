package com.example.introductiontomobilesystems;

import org.json.JSONException;
import org.json.JSONObject;

public class Habit {
    public String name;
    public String freq;
    public boolean active;
    public boolean notifs;

    public Habit(String name, String freq, boolean active, boolean notifs) {
        this.name = name;
        this.freq = freq;
        this.active = active;
        this.notifs = notifs;
    }

    public Habit(JSONObject o) throws JSONException {
        this.name = o.getString("name");
        this.freq = o.getString("freq");
        this.active = o.getBoolean("active");
        this.notifs = o.optBoolean("notifs", false);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("name", name);
        o.put("freq", freq);
        o.put("active", active);
        o.put("notifs", notifs);
        return o;
    }
}
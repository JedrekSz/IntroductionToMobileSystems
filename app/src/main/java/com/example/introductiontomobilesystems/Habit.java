package com.example.introductiontomobilesystems;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;

public class Habit {
    public String name;
    public String freq;
    public boolean active;
    public boolean notifs;
    public boolean done;
    public long creationDate;
    public long dueDate;


    public Habit(String name, String freq, boolean active, boolean notifs) {
        this.name = name;
        this.freq = freq;
        this.active = active;
        this.notifs = notifs;
        this.done = false;
        this.creationDate = System.currentTimeMillis();
        this.dueDate = calculateNextDueDate(System.currentTimeMillis(), freq);
    }

    public Habit(JSONObject o) throws JSONException {
        this.name = o.getString("name");
        this.freq = o.getString("freq");
        this.active = o.getBoolean("active");
        this.notifs = o.optBoolean("notifs", false);
        this.done = o.optBoolean("done", false);
        this.creationDate = o.optLong("creationDate", System.currentTimeMillis());
        this.dueDate = o.optLong("dueDate", calculateNextDueDate(System.currentTimeMillis(), freq));
    }

    public JSONObject toJson() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("name", name);
        o.put("freq", freq);
        o.put("active", active);
        o.put("notifs", notifs);
        o.put("done", done);
        o.put("creationDate", creationDate);
        o.put("dueDate", dueDate);
        return o;
    }

    public static long calculateNextDueDate(long currentBaseTime, String frequency) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentBaseTime);

        switch (frequency) {
            case "Daily":
                break;
            case "Weekly":
                cal.add(Calendar.DAY_OF_YEAR, 7);
                break;
            case "Bi-Weekly":
                cal.add(Calendar.DAY_OF_YEAR, 14);
                break;
            case "Monthly":
                cal.add(Calendar.MONTH, 1);
                break;
            default:
                cal.add(Calendar.DAY_OF_YEAR, 1);
                break;
        }
        return cal.getTimeInMillis();
    }
}
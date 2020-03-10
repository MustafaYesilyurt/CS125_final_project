package com.example.myese.fitlyfe;

public class Location {
    private String name;
    private String city;
    private String state;
    private String uid;

    public Location()
    {
        name ="Not Available";
        city ="Not Available";
        state="Not Available";
    }

    public Location( String name, String city, String state, String uid )
    {
        this.name = name;
        this.city = city;
        this.state = state;
        this.uid = uid;
    }

    public String getName()
    {
        return name;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public String getUid()
    {
        return uid;
    }

    public String toString()
    {
        return name + ": " + state;
    }
}

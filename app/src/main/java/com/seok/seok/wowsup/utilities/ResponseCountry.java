package com.seok.seok.wowsup.utilities;

public class ResponseCountry {
    private String name;
    private int flagId;

    public ResponseCountry(String name, int flagId)
    {
        this.name = name;
        this.flagId = flagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlagId() {
        return flagId;
    }

    public void setFlagId(int flagId) {
        this.flagId = flagId;
    }
}

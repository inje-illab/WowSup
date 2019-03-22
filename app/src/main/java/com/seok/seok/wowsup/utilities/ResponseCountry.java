package com.seok.seok.wowsup.utilities;

public class ResponseCountry {
    private String _name;
    private int _flagId;

    public ResponseCountry(String name, int flagId)
    {
        _name = name;
        _flagId = flagId;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public int getFlagId() {
        return _flagId;
    }

    public void setFlagId(int _flagId) {
        this._flagId = _flagId;
    }
}

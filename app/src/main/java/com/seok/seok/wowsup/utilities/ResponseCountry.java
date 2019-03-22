package com.seok.seok.wowsup.utilities;

public class ResponseCountry {
    private String _name;
    private int _flagId;

    public ResponseCountry(String name, int flagId)
    {
        _name = name;
        _flagId = flagId;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_flagId() {
        return _flagId;
    }

    public void set_flagId(int _flagId) {
        this._flagId = _flagId;
    }
}

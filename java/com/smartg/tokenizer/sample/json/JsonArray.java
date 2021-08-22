package com.smartg.tokenizer.sample.json;

import java.util.ArrayList;

class JsonArray extends ArrayList<Object> implements JsonObject {

    private static final long serialVersionUID = -4062410183468054718L;

    @Override
    public boolean add(Object o) {
        return super.add(o);
    }

    @Override
    public Object getObject() {
        return this;
    }
}
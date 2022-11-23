package io.automation;


import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private final Map<String, Object> map;

    public ScenarioContext() {
        map = new HashMap<>();
    }

    public Object get(final String key) {
        return map.get(key);
    }

    public void set(final String key, final Object response) {
        map.put(key, response);
    }

}

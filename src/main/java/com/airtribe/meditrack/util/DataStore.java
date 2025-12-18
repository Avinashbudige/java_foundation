package com.airtribe.meditrack.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Generic DataStore<T>
public class DataStore<T> {
    private List<T> items;

    public DataStore() {
        this.items = new ArrayList<>();
    }

    public void add(T item) {
        items.add(item);
    }

    public T findById(String id) {
        return items.stream()
                .filter(item -> {
                    try {
                        java.lang.reflect.Method getId = item.getClass().getMethod("getId");
                        String itemId = (String) getId.invoke(item);
                        return id.equals(itemId);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .findFirst()
                .orElse(null);
    }

    public List<T> findAll() {
        return new ArrayList<>(items);
    }

    public List<T> findByName(String name) {
        return items.stream()
                .filter(item -> {
                    try {
                        java.lang.reflect.Method getName = item.getClass().getMethod("getName");
                        String itemName = (String) getName.invoke(item);
                        return itemName != null && itemName.toLowerCase().contains(name.toLowerCase());
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
}

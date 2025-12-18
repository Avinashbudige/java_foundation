package com.airtribe.meditrack.interfaces;

import java.util.List;

public interface Searchable {
    /**
     * Search for entities by ID
     * @param id the ID to search for
     * @return list of matching entities
     */
    List<?> searchById(String id);

    /**
     * Search for entities by name
     * @param name the name to search for
     * @return list of matching entities
     */
    List<?> searchByName(String name);
}

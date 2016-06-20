/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.database;

import java.net.URL;

/**
 *
 * @author Phelipe
 */
public interface Environment {

    /**
     * Returns the environment name
     *
     * @return
     */
    String getName();

    /**
     * Checks if a key is present
     *
     * @param key
     * @return 
     */
    boolean has(String key);

    /**
     * Checks if a key is equals to true if it's not present will return false
     * @param feature
     * @return 
     */
    boolean supports(String feature);

    /**
     * Returns a key
     * @param string
     * @return 
     */
    String get(String string);

    /**
     * Returns a key or a default value if the value isn't set
     * @param string
     * @param defaultValue
     * @return 
     */
    String get(String string, String defaultValue);

    /**
     * Sets a key in memory. This will *not* affect any configuration file.
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    Iterable<String> getKeys();

    /**
     * Locates a resource according to your current environment.
     * @param name
     * @return 
     */
    URL getResource(String name);

}

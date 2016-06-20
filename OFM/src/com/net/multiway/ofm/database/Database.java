/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.database;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Phelipe
 */
public class Database {

    protected static Database instance = null;

    protected static EntityManagerFactory emf;

    protected String persistenceUnitName = "ofmPU";

    protected Environment env;

    protected Database() {
        this(DefaultEnvironment.getInstance());
    }

    protected Database(Environment env) {
        this.env = env;
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    /**
     * Updade EntityManagerFactory Singleton
     */
    protected void initEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            return;
        }

        emf = Persistence.createEntityManagerFactory(persistenceUnitName, this.getConnectionProperties());
    }

    /**
     * Build properties for connection porposes
     *
     * @return Map<String, String> Properties to connect
     */
    private Map<String, String> getConnectionProperties() {
        Map<String, String> props = new HashMap<>();

        props.put("javax.persistence.jdbc.url", env.get("database.url"));
        props.put("javax.persistence.jdbc.user", env.get("database.user"));
        props.put("javax.persistence.jdbc.password", env.get("database.password"));
        props.put("javax.persistence.jdbc.driver", env.get("database.driver"));

        return props;
    }

    /**
     * @return EntityManagerFactory
     */
    public EntityManagerFactory getEntityManagerFactory() {
        initEntityManagerFactory();
        return emf;
    }

}

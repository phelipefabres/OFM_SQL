/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.daos;

import com.net.multiway.ofm.database.Database;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author phelipe
 */
public class DAO {
    public DAO() {
        this.emf = Database.getInstance().getEntityManagerFactory();
       
    }
    protected EntityManagerFactory emf = null;
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

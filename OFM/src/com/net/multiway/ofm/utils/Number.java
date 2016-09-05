/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author phelipe
 */
public class Number {
    
    private IntegerProperty barNumber;
    
    public int getBarNumber() {
        if (barNumber != null) {
            return barNumber.get();
        }
        return 0;
    }

    public void setBarNumber(int barNumber) {
        this.barNumberProperty().set(barNumber);
    }
    
    public IntegerProperty barNumberProperty() {
        if (barNumber == null) {
            barNumber = new SimpleIntegerProperty(0);
        }
        return barNumber;
    }
    
}

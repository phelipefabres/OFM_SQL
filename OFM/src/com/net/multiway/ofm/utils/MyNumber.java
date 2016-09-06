/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.utils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author phelipe
 */
public class MyNumber {
    
    private DoubleProperty barNumber;
    
    public double getBarNumber() {
        if (barNumber != null) {
            return barNumber.get();
        }
        return 0;
    }

    public void setBarNumber(double barNumber) {
        this.barNumberProperty().set(barNumber);
    }
    
    public DoubleProperty barNumberProperty() {
        if (barNumber == null) {
            barNumber = new SimpleDoubleProperty(0);
        }
        return barNumber;
    }
    
}

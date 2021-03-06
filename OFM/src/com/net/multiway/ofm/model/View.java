/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.model;

/**
 *
 * @author Phelipe
 */
public enum View {

    ConfigurationWindow,
    MonitorWindow,
    MainScene,
    DeviceAddDialog,
    RangeDialog,
    LoginWindow,
    LoginCreateWindow;

    /**
     * @return File path to FXML resource of the view
     */
    public String getResource() {
        switch (this) {
            case ConfigurationWindow:
                return "view/ConfigurationWindow.fxml";
            case MonitorWindow:
                return "view/MonitorWindow.fxml";
            case MainScene:
                return "view/MainScene.fxml";
            case DeviceAddDialog:
                return "view/DeviceAddDialog.fxml";
            case RangeDialog:
                return "view/RangeDialog.fxml";
            case LoginWindow:
                return "view/LoginWindow.fxml";
            case LoginCreateWindow:
                return "view/LoginCreateWindow.fxml";
            default:
                return null;
        }
    }
}

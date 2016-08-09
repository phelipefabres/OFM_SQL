/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.model;

import com.net.multiway.ofm.MainApp;
import com.net.multiway.ofm.entities.DataEvent;
import com.net.multiway.ofm.entities.DataGraphic;

import com.net.multiway.ofm.receive.ReceiveParameters;
import com.net.multiway.ofm.receive.ReceiveValues;
import com.net.multiway.ofm.exception.AlertDialog;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.Limit;
import com.net.multiway.ofm.entities.Parameter;
import com.net.multiway.ofm.entities.User;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author rafael
 */
public abstract class ControllerExec implements Initializable, IController {

    protected Parameter parameters;
    protected Device device;
    protected Limit limits;
    protected User user;

    protected ReceiveParameters receiveParameters;
    protected ReceiveValues receiveValues;
    protected EntityManagerFactory emf;
    protected DeviceComunicator host;

    // Gráfico
    @FXML
    protected LineChart<NumberAxis, NumberAxis> grafico;
    @FXML
    protected NumberAxis xAxis;
    @FXML
    protected NumberAxis yAxis;

    //result
    @FXML
    protected TableView<DataEvent> resultTable;
    
    @FXML
    protected TableColumn<DataEvent, String> typeColumn;
    @FXML
    protected TableColumn<DataEvent, Float> distanceColumn;
    @FXML
    protected TableColumn<DataEvent, Float> insertLossColumn;
    @FXML
    protected TableColumn<DataEvent, Float> reflectLossColumn;
    @FXML
    protected TableColumn<DataEvent, Float> accumulationColumn;
    @FXML
    protected TableColumn<DataEvent, Float> attenuationCoefficientColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mappingParametersTable();
        emf = Persistence.createEntityManagerFactory("ofmPU");
    }

    protected void plotGraph() {

        ObservableList<XYChart.Data<Float, Float>> dataset = FXCollections.observableArrayList();
        int i = 0;
        int k = 0;
        int n1 = receiveParameters.getData().getRangeOfTest();
        int n = receiveParameters.getData().getDataGraphicList().size();
        int fz = receiveParameters.getData().getSampleFrequency();
        float m = receiveParameters.getData().getDataEventList().get(k).getDistance();
        float fator = 0.0f;
        switch (n1) {
            case 5000:
                fator = 0.02528f;
                break;
            case 10000:
                fator = 0.90234f;
                break;
            case 30000:
                fator = 0.767338f;
                break;
            case 60000:
                fator = 0.294935f;
                break;
            case 80000:
                fator = 5.472465f;
                break;
            case 120000:
                fator = 5.636457f;
                break;
            default:
                break;
        }
        for (DataGraphic data : receiveParameters.getData().getDataGraphicList()) {
            int c = data.getPoint();

            float dx = 2 * n * fz;
            dx = (((float) i / dx)) * 1000000000 * fator;

            if (i == m) {
                receiveParameters.getData().getDataEventList().get(k).setDistance(Math.abs(dx));
                k++;
            }


            XYChart.Data<Float, Float> coordData = new XYChart.Data<>(Math.abs(dx), Math.abs((float) c / 1000)*0.725190f);

            dataset.add(coordData);

            i++;

            if (k < receiveParameters.getData().getDataEventList().size()) {
                m = receiveParameters.getData().getDataEventList().get(k).getDistance();
            }
        }

        grafico.getData().add(new XYChart.Series("Gráfico", FXCollections.observableArrayList(dataset)));

    }

    protected void exportData() {
        if ((receiveParameters == null) || (receiveValues == null)) {
            String msg = "Não há dados a serem exportados.";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
            AlertDialog.NothingToExport();
        } else {
            receiveParameters.print();
            receiveValues.print();
            String msg = "Dados exportados com sucesso.";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
            AlertDialog.ExportSuccess();
        }
    }

    protected void mappingParametersTable() {
//        numeroColumn.setCellValueFactory(cellData -> cellData.getValue().dataEventIdProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        distanceColumn.setCellValueFactory(cellData -> cellData.getValue().distanceProperty());
        insertLossColumn.setCellValueFactory(cellData -> cellData.getValue().insertionLossProperty());
        reflectLossColumn.setCellValueFactory(cellData -> cellData.getValue().echoLossProperty());
        accumulationColumn.setCellValueFactory(cellData -> cellData.getValue().acumulativeLossProperty());
        attenuationCoefficientColumn.setCellValueFactory(cellData -> cellData.getValue().averageAttenuationCoefficientProperty());
    }
}

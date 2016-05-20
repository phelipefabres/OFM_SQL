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

    protected ReceiveParameters receiveParameters;
    protected ReceiveValues receiveValues;
    protected EntityManagerFactory emf;

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
    protected TableColumn<DataEvent, Long> numeroColumn;
    @FXML
    protected TableColumn<DataEvent, Integer> typeColumn;
    @FXML
    protected TableColumn<DataEvent, Integer> distanceColumn;
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
        emf = emf = Persistence.createEntityManagerFactory("OFMPU");
    }

    protected void plotGraph() {

        ObservableList<XYChart.Data<Integer, Integer>> dataset = FXCollections.observableArrayList();

        int i = 1;
        float dataPrevious = 0;
        int m = receiveParameters.getData().getDataEventList().get(1).getDistance();
        int n = receiveParameters.getData().getDataGraphicList().size();
        int fz = receiveParameters.getData().getSampleFrequency();
        for (DataGraphic data : receiveParameters.getData().getDataGraphicList()) {
            int c = data.getValue();

            float d;
//            if (i == m) {
//                d = m * ((float) (c) / ((float) (2 * n * fz))*100000   );
//                System.out.println("Novo = " + d);
//                System.out.println("antigo = " + c);
//                System.out.println("Distancia = " + m);
//                System.out.println("Size = " + n);
//                System.out.println("dx = " + ((float) (c) / (float) (2 * n * fz)) *100000);
//                receiveParameters.getData().getDataEventList().get(1).setDistance((int) d);
//            break;
//            }
//            else {
                d = (((float) (c) / (float) (2 * n * fz)) * 1000000);
//            }

//            System.out.println("antigo = " + data.getValue());
//            System.out.println("frequencia = " + receiveParameters.getData().getSampleFrequency());
//dataPrevious = dataPrevious * i++;
            XYChart.Data<Integer, Integer> coordData = new XYChart.Data<>(i++, c);
            coordData.setNode(
                    new HoveredThresholdNode(dataPrevious, c));
            dataset.add(coordData);
            dataPrevious = d;
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
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        distanceColumn.setCellValueFactory(cellData -> cellData.getValue().distanceProperty());
        insertLossColumn.setCellValueFactory(cellData -> cellData.getValue().insertLossProperty());
        reflectLossColumn.setCellValueFactory(cellData -> cellData.getValue().echoLossProperty());
        accumulationColumn.setCellValueFactory(cellData -> cellData.getValue().acumulativeLossProperty());
        attenuationCoefficientColumn.setCellValueFactory(cellData -> cellData.getValue().averageAttenuationCoefficientProperty());
    }
}

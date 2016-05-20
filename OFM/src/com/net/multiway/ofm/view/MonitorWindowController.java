/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.view;

import com.net.multiway.ofm.MainApp;
import com.net.multiway.ofm.daos.OccurrenceDAO;
import com.net.multiway.ofm.entities.DataEvent;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.Occurrence;
import com.net.multiway.ofm.exception.AlertDialog;
import com.net.multiway.ofm.model.ControllerExec;
import com.net.multiway.ofm.model.DeviceComunicator;
import com.net.multiway.ofm.model.Mode;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author rafael
 */
public class MonitorWindowController extends ControllerExec {

    private ObservableList<Occurrence> occurrenceList;
    private Occurrence occurrence;
    //Thread de execução do monitor
    private Thread tr;
    private int count = 0;
    //device
    @FXML
    private Label ipLabel;
    @FXML
    private Label maskLabel;
    @FXML
    private Label gatewayLabel;

    // Gráfico
    @FXML
    private LineChart<Double, Double> grafico;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    //parameter
    @FXML
    private ComboBox measureRangeField;
    @FXML
    private ComboBox pulseWidthField;
    @FXML
    private ComboBox measureTimeField;
    @FXML
    private ComboBox waveLengthField;
    @FXML
    private ComboBox measureModeField;
    @FXML
    private TextField refractiveIndexField;
    @FXML
    private TextField nonReflactionThresholdField;
    @FXML
    private TextField endThresholdField;
    @FXML
    private TextField reflectionThresholdField;
    @FXML
    private TextField cycleTimeField;

    //result
    @FXML
    private TableView<DataEvent> resultTable;
    @FXML
    private TableColumn<DataEvent, Long> numeroColumn;
    @FXML
    private TableColumn<DataEvent, Integer> typeColumn;
    @FXML
    private TableColumn<DataEvent, Integer> distanceColumn;
    @FXML
    private TableColumn<DataEvent, Float> insertLossColumn;
    @FXML
    private TableColumn<DataEvent, Float> reflectLossColumn;
    @FXML
    private TableColumn<DataEvent, Float> accumulationColumn;
    @FXML
    private TableColumn<DataEvent, Float> attenuationCoefficientColumn;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonExport;
    @FXML
    private Button buttonStop;
    @FXML
    private Button buttonExecute;
    @FXML
    private Button buttonConfig;
    @FXML
    private Label executionLabel;
    @FXML
    private TableView<Occurrence> occurrenceTable;
    @FXML
    private TableColumn<Occurrence, Long> idColumm;
    @FXML
    private TableColumn<Occurrence, String> occurrenceColumm;
    @FXML
    private TableColumn<Occurrence, String> descriptionColumm;
    @FXML
    private TableColumn<Occurrence, Date> dateColumm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        occurrence = new Occurrence();

        prepareForm(Mode.VIEW);
    }

    @FXML
    private void onHandleEditParameters() {

    }

    @FXML
    private void onHandleSaveParameters() {

    }

    @FXML
    private void onHandleExecute() {
        DeviceComunicator host;
        if (device != null) {
            host = new DeviceComunicator(device.getIp().trim(), 5000);

            if (buttonSave.isDisable()) {
                executionLabel.setVisible(true);
                String msg = "Recebendo Dados do OTDR...";
                executionLabel.setText(msg);

                buttonExecute.setDisable(true);
                buttonConfig.setDisable(true);
                buttonExport.setDisable(true);
                buttonStop.setDisable(false);

                Task execute;
                execute = new Task() {
                    @Override
                    protected Void call() throws Exception {
                        count = 0;
                        while (!buttonStop.isDisable()) {
                            if (resultTable.getItems().size() > 0 && grafico.getData().size() > 0) {
                                resultTable.getItems().remove(0, resultTable.getItems().size());
                            }
                            host.connect(parameters);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        updateMessage(++count);
                                    } catch (Exception ex) {
                                        Logger.getLogger(MonitorWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });

                            try {
                                // Sleep at least n milliseconds.
                                Thread.sleep(1000 * 60 * parameters.getCycleTime());
                            } catch (InterruptedException ex) {
                                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, "Sleep interrupted.");
                            }
                        }

                        buttonExecute.setDisable(false);
                        buttonConfig.setDisable(false);
                        buttonExport.setDisable(false);

                        return null;
                    }

                    @Override
                    protected void failed() {
                        super.failed();
                        buttonExecute.setDisable(false);
                        buttonConfig.setDisable(false);
                        buttonStop.setDisable(true);
                        Exception ex = new Exception(getException());
                        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, ex.getMessage());
                        AlertDialog.exception(ex);
                    }

                    private void updateMessage(int count) throws Exception {
                        String msg = "Envio de dados finalizado.";
                        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                        executionLabel.setText(msg);

                        receiveParameters = host.getReceiveParametersData();
                        if (receiveParameters != null) {
                            resultTable.setItems(FXCollections.observableArrayList(receiveParameters.getData().getDataEventList()));

                            msg = "Eventos atualizados na tela de configuração.";
                            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                            executionLabel.setText(msg);

                            receiveValues = host.getReceiveValues();
                            grafico.getData().clear();
                            plotGraph();
                            grafico.setCreateSymbols(false);
                            msg = "Gráfico plotado. Iteração " + count;
                            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                            executionLabel.setText(msg);
//                            limits.setEventsNumber(receiveParameters.getData().getEvents().size());
                            saveOccurrence();
                            OccurrenceDAO dao = new OccurrenceDAO(emf);
                            displayOccurrence(dao.findOccurrenceEntities());
                        }
                    }

                };

                this.tr = new Thread(execute);
                this.tr.start();

            } else {
                AlertDialog.SaveParameters();
            }
        } else {
            AlertDialog.DeviceNotFound();
        }
    }

    @FXML
    private void onHandleExport() {
        exportData();
    }

    @FXML
    private void onHandleChangeToConfiguration() {
        MainApp.getInstance().showConfiguration();
    }

    @Override
    public void handleSave(ActionEvent event) {
    }

    @Override
    public void prepareForm(Mode mode) {
        switch (mode) {
            case VIEW:
                measureRangeField.setDisable(true);
                pulseWidthField.setDisable(true);
                measureTimeField.setDisable(true);
                waveLengthField.setDisable(true);
                measureModeField.setDisable(true);
                refractiveIndexField.setDisable(true);
                nonReflactionThresholdField.setDisable(true);
                endThresholdField.setDisable(true);
                reflectionThresholdField.setDisable(true);
                buttonSave.setDisable(true);
                buttonEdit.setDisable(true);
                cycleTimeField.setDisable(true);
                buttonStop.setDisable(true);
                buttonExport.setDisable(true);
                break;
        }

    }

    @Override
    public void prepareMenu(Mode mode) {
    }

    @FXML
    private void onHandleStop(ActionEvent event) {
        buttonStop.setDisable(true);
        this.tr.interrupt();
    }

    void setReference(Device reference) {

       
        this.parameters = reference.getParameter();
        this.device = reference;

        this.measureRangeField.setValue(parameters.getMeasuringRangeOfTest());
        this.pulseWidthField.setValue(parameters.getTestPulseWidth());
        this.measureTimeField.setValue(parameters.getMeasuringTime());
        this.waveLengthField.setValue(parameters.getTestWaveLength());
        this.measureModeField.setValue(parameters.getMeasureMode());
        this.refractiveIndexField.setText(parameters.getRefractiveIndex().toString());
        this.nonReflactionThresholdField.setText(parameters.getReflectionThreshold().toString());
        this.endThresholdField.setText(parameters.getEndThreshold().toString());
        this.reflectionThresholdField.setText(parameters.getReflectionThreshold().toString());
        this.cycleTimeField.setText(parameters.getCycleTime().toString());

        ipLabel.setText(device.getIp());
        maskLabel.setText(device.getMask());
        gatewayLabel.setText(device.getGateway());

    }

    private void displayOccurrence(List<Occurrence> resultList) {
        if (resultList != null) {
            occurrenceList = FXCollections.observableArrayList();
            resultList.stream().forEach((result) -> {
                occurrenceList.add(result);
            });
            occurrenceTable.setItems(occurrenceList);
//            idColumm.setCellValueFactory(cellData -> cellData.getValue().idProperty());
            occurrenceColumm.setCellValueFactory(cellData -> cellData.getValue().occurrenceProperty());
            descriptionColumm.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
            dateColumm.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        }

    }

    public void saveOccurrence() throws Exception {

        Occurrence occurrence2 = new Occurrence();
        LocalDateTime timePoint = LocalDateTime.now();

//        if (limits.getEventsNumber() < receiveParameters.getData().getEvents().size()) {
//            occurrence2.setOccurrence("Vermelho");
//            occurrence2.setDescription("Algum rompimento pode ter sido encontrado!");
//            occurrence2.setDate(timePoint.toString());
//        } else {
//            occurrence2.setOccurrence("Verde");
//            occurrence2.setDescription("Nenhum erro foi encontrado!");
//            occurrence2.setDate(timePoint.toString());
//        }
//
//        DataOccurrenceDAO dao = new DataOccurrenceDAO();
//        dao.create(occurrence2);
    }
}

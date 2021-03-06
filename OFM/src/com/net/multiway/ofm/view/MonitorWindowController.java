/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.view;

import com.net.multiway.ofm.MainApp;
import com.net.multiway.ofm.daos.DataEventDAO;
import com.net.multiway.ofm.daos.DeviceDAO;
import com.net.multiway.ofm.daos.OccurrenceDAO;
import com.net.multiway.ofm.entities.Data;
import com.net.multiway.ofm.entities.DataEvent;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.Limit;
import com.net.multiway.ofm.entities.Occurrence;
import com.net.multiway.ofm.entities.User;
import com.net.multiway.ofm.exception.AlertDialog;
import com.net.multiway.ofm.model.ControllerExec;
import com.net.multiway.ofm.model.DeviceComunicator;
import com.net.multiway.ofm.model.Mode;
import com.net.multiway.ofm.model.View;
import com.net.multiway.ofm.utils.MyNumber;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author rafael
 */
public class MonitorWindowController extends ControllerExec {

    private ObservableList<Occurrence> occurrenceList;

    private Occurrence occurrence;
    //Thread de execução do monitor
    private Service service;
    private Service worker;
    private int count = 0;
    //device
    @FXML
    private Label ipLabel;
    @FXML
    private Label maskLabel;
    @FXML
    private Label gatewayLabel;

    @FXML
    protected LineChart<NumberAxis, NumberAxis> grafico;
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

    @FXML
    private Label executionLabel;

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
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonEdit;

//    private Button buttonExport;
    @FXML
    private Button buttonStop;
    @FXML
    private Button buttonExecute;
    @FXML
    private Button buttonConfig;

    @FXML
    private TableView<Occurrence> occurrenceTable;
    @FXML
    private TableColumn<Occurrence, String> occurrenceColumm;
    @FXML
    private TableColumn<Occurrence, String> descriptionColumm;
    @FXML
    private TableColumn<Occurrence, Date> dateColumm;
    @FXML
    private ProgressBar progressBar;

    final MyNumber pb = new MyNumber();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        mappingParametersTable(typeColumn, distanceColumn, insertLossColumn, reflectLossColumn, accumulationColumn, attenuationCoefficientColumn);
        occurrence = new Occurrence();
        this.worker = null;
        prepareForm(Mode.VIEW);
        pb.setBarNumber(0);
        pb.barNumberProperty().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            progressBar.progressProperty().bind(pb.barNumberProperty());
        });

    }

    @FXML
    private void onHandleEditParameters() {

    }

    @FXML
    private void onHandleSaveParameters() {

    }

    @FXML
    private void onHandleExecute() throws IOException {
//        DeviceComunicator host;
        if (device != null) {
            if (host == null) {
                host = new DeviceComunicator(device.getIp().trim(), 5000);
            }
            if (buttonSave.isDisable()) {
                String msg = "Recebendo Dados do OTDR...";
                buttonExecute.setDisable(true);
                buttonConfig.setDisable(true);
                buttonStop.setDisable(false);

                service = new Service() {
                    @Override
                    protected Task createTask() {
                        return new Task() {
                            @Override
                            protected Object call() throws Exception {
                                count = 0;
                                while (!buttonStop.isDisable()) {
                                    if (resultTable.getItems().size() > 0 && grafico.getData().size() > 0 && occurrenceTable.getItems().size() > 0) {
                                        resultTable.getItems().remove(0, resultTable.getItems().size());
                                        occurrenceTable.getItems().remove(0, occurrenceTable.getItems().size());

                                    }
                                    host.connect(parameters, pb);
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
                                        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, "Thread Interrompida.");
                                    }
                                }

                                buttonExecute.setDisable(false);
                                buttonConfig.setDisable(false);

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

                                    plotGraph(grafico, receiveParameters);
                                    grafico.setCreateSymbols(false);
                                    grafico.setAnimated(false);
                                    grafico.setLegendVisible(false);

                                    List<Occurrence> list = saveOccurrence(receiveParameters.getData().getDataEventList().size());
//
                                    if (list.size() == 1) {
                                        Occurrence tmp = list.get(0);
//                                        if (tmp.getType().compareTo("ERRO") == 0) {
//
//                                            worker = new Service() {
//                                                @Override
//                                                protected Task createTask() {
//                                                    return new Task() {
//                                                        @Override
//                                                        protected Object call() throws Exception {
//
//                                                            for (int i = 0; i < 3; i++) {
//                                                                if (resultTable.getItems().size() > 0 && grafico.getData().size() > 0) {
//                                                                    resultTable.getItems().remove(0, resultTable.getItems().size());
//                                                                }
//                                                                host.connect(parameters,pb);
//                                                                List<Occurrence> list2 = saveOccurrence(receiveParameters.getData().getDataEventList().size());
//
//                                                                if (list2.size() == 1) {
//                                                                    Occurrence tmp2 = list.get(0);
//                                                                    if (tmp2.getType().compareTo("ERRO") == 0) {
//                                                                        break;
//                                                                    }
//                                                                }
//
//                                                            }
//
//                                                            return null;
//                                                        }
//
//                                                        @Override
//                                                        protected void succeeded() {
//
//                                                            String msg = "Envio de dados finalizado.";
//                                                            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
//                                                            executionLabel.setText(msg);
//
//                                                            receiveParameters = host.getReceiveParametersData();
//                                                            if (receiveParameters != null) {
//                                                                resultTable.setItems(FXCollections.observableArrayList(receiveParameters.getData().getDataEventList()));
//
//                                                                msg = "Eventos atualizados na tela de configuração.";
//                                                                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
//                                                                executionLabel.setText(msg);
//
//                                                                receiveValues = host.getReceiveValues();
//
//                                                                plotGraph(grafico, receiveParameters);
//                                                                grafico.setCreateSymbols(false);
//                                                                grafico.setAnimated(false);
//                                                                grafico.setLegendVisible(false);
//
//                                                                msg = "Gráfico plotado na tela de configuração.";
//                                                                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
//                                                                executionLabel.setText(msg);
//
//                                                                try {
//                                                                    List<Occurrence> list2 = saveOccurrence(receiveParameters.getData().getDataEventList().size());
//                                                                    Occurrence tmp2 = list.get(0);
//                                                                    tmp2.setDevice(device);
//                                                                    OccurrenceDAO Odao = new OccurrenceDAO();
//
//                                                                    tmp2.setDevice(device);
//                                                                    Odao.create(tmp2);
//                                                                    device.getOccurrenceList().add(tmp2);
//                                                                    msg = "Ocorrência registrada...";
//                                                                    Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
//
//                                                                } catch (Exception ex) {
//                                                                    Logger.getLogger(MonitorWindowController.class.getName()).log(Level.SEVERE, null, ex);
//                                                                }
//                                                                displayOccurrence(list);
//
//                                                            }
//                                                        }
//                                                    };
//                                                }
//
//                                            };
//                                            worker.start();
//                                        } else {
                                        tmp.setDevice(device);
                                        OccurrenceDAO Odao = new OccurrenceDAO();

                                        tmp.setDevice(device);
                                        Odao.create(tmp);
                                        device.getOccurrenceList().add(tmp);
                                        msg = "Ocorrência registrada...";
                                        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                                        resultTable.setItems(FXCollections.observableArrayList(receiveParameters.getData().getDataEventList()));

                                        msg = "Eventos atualizados na tela de configuração.";
                                        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                                        executionLabel.setText(msg);

                                        receiveValues = host.getReceiveValues();

                                        plotGraph(grafico, receiveParameters);
                                        grafico.setCreateSymbols(false);
                                        msg = "Gráfico plotado. Iteração " + count;
                                        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                                        executionLabel.setText(msg);

                                        displayOccurrence(list);

                                    } else {

                                        OccurrenceDAO Odao = new OccurrenceDAO();
                                        for (Occurrence tmp : list) {
                                            tmp.setDevice(device);
                                            Odao.create(tmp);
                                            device.getOccurrenceList().add(tmp);
                                        }
                                        msg = "Ocorrência registrada...";
                                        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                                        resultTable.setItems(FXCollections.observableArrayList(receiveParameters.getData().getDataEventList()));

                                        msg = "Eventos atualizados na tela de configuração.";
                                        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                                        executionLabel.setText(msg);

                                        receiveValues = host.getReceiveValues();
//                                    grafico.getData().clear();
                                        plotGraph(grafico, receiveParameters);
                                        grafico.setCreateSymbols(false);
                                        msg = "Gráfico plotado. Iteração " + count;
                                        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                                        executionLabel.setText(msg);
                                        // list.add(tmp);
                                        displayOccurrence(list);
                                    }

//                                }
                                }
                            }

                        };
                    }

                };
                service.start();

            } else {
                AlertDialog.SaveParameters();
            }
        } else {
            AlertDialog.DeviceNotFound();
        }
    }

    private void onHandleExport() {
        exportData(receiveParameters, receiveValues);
    }

    @FXML
    private void onHandleChangeToConfiguration() {

        if (user.getisAdmin() == 1) {
            ConfigurationWindowController controller
                    = (ConfigurationWindowController) MainApp.getInstance().showView(View.ConfigurationWindow, Mode.VIEW);

            controller.setUser(user);
            if (host != null) {
                try {
                    host.closeConnection();
                } catch (Exception ex) {
                    Logger.getLogger(MonitorWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String msg = "ConfigurationWindow inicializada...";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
        } else {
            AlertDialog.isNotAdmin();
        }
    }

    @Override
    public void handleSave(ActionEvent event
    ) {
    }

    @Override
    public void prepareForm(Mode mode
    ) {
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
//                buttonExport.setDisable(true);
                break;
            case EDIT:
                measureRangeField.setDisable(false);
                pulseWidthField.setDisable(false);
                measureTimeField.setDisable(false);
                waveLengthField.setDisable(false);
                measureModeField.setDisable(false);
                refractiveIndexField.setDisable(false);
                nonReflactionThresholdField.setDisable(false);
                endThresholdField.setDisable(false);
                reflectionThresholdField.setDisable(false);
                buttonSave.setDisable(false);
                buttonEdit.setDisable(false);
                cycleTimeField.setDisable(false);
                buttonStop.setDisable(false);
//                buttonExport.setDisable(false);
                break;
        }

    }

    @Override
    public void prepareMenu(Mode mode
    ) {
        switch (mode) {
            case VIEW:
                MainApp.getInstance().disable(Menu.Print, true);
                MainApp.getInstance().disable(Menu.ExportEL, true);
                MainApp.getInstance().disable(Menu.ExportLR, true);
                MainApp.getInstance().disable(Menu.ExportOL, true);
                MainApp.getInstance().disable(Menu.Print, true);
                break;
            case EDIT:
                MainApp.getInstance().disable(Menu.Print, false);
                MainApp.getInstance().disable(Menu.ExportEL, false);
                MainApp.getInstance().disable(Menu.ExportLR, false);
                MainApp.getInstance().disable(Menu.ExportOL, false);
                MainApp.getInstance().disable(Menu.Print, false);
                break;

            default:
                throw new AssertionError(mode.name());

        }
    }

    @FXML
    private void onHandleStop(ActionEvent event) {
        buttonStop.setDisable(true);
        this.service.cancel();
        if (this.worker != null) {
            this.worker.cancel();
        }
    }

    void setReference(Device reference, User user) {

        this.parameters = reference.getParameter();
        this.device = reference;
        this.limits = reference.getLimit();
        this.user = user;

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
        String msg = "Referência lida...";
        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
        host = null; //new DeviceComunicator(device.getIp().trim(), 5000);

    }

    private void displayOccurrence(List<Occurrence> resultList) {
        if (resultList != null) {

            occurrenceColumm.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
            descriptionColumm.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
            dateColumm.setCellValueFactory(cellData -> cellData.getValue().createTimeProperty());

            occurrenceColumm.setCellFactory((TableColumn<Occurrence, String> column) -> {
                return new TableCell<Occurrence, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            if (item.compareTo("OK") == 0) {
                                setTextFill(Color.BLACK);
                                setStyle("-fx-background-color: green");
                                descriptionColumm.setCellFactory((TableColumn<Occurrence, String> column) -> {
                                    return new TableCell<Occurrence, String>() {
                                        @Override
                                        protected void updateItem(String item, boolean empty) {
                                            super.updateItem(item, empty);

                                            if (item == null || empty) {
                                                setText(null);
                                                setStyle("");
                                            } else {
                                                setText(item);
                                                setTextFill(Color.BLACK);
                                                setStyle("-fx-background-color: green");

                                            }
                                        }
                                    };
                                });

                                dateColumm.setCellFactory((TableColumn<Occurrence, Date> column) -> {
                                    return new TableCell<Occurrence, Date>() {
                                        @Override
                                        protected void updateItem(Date item, boolean empty) {
                                            super.updateItem(item, empty);

                                            if (item == null || empty) {
                                                setText(null);
                                                setStyle("");
                                            } else {
                                                setText(item.toString());
                                                setTextFill(Color.BLACK);
                                                setStyle("-fx-background-color: green");

                                            }
                                        }
                                    };
                                });

                            } else if (item.compareTo("ATENÇÃO") == 0) {
                                setTextFill(Color.BLACK);
                                setStyle("-fx-background-color: yellow");
                                descriptionColumm.setCellFactory((TableColumn<Occurrence, String> column) -> {
                                    return new TableCell<Occurrence, String>() {
                                        @Override
                                        protected void updateItem(String item, boolean empty) {
                                            super.updateItem(item, empty);

                                            if (item == null || empty) {
                                                setText(null);
                                                setStyle("");
                                            } else {
                                                setText(item);
                                                setTextFill(Color.BLACK);
                                                setStyle("-fx-background-color: yellow");

                                            }
                                        }
                                    };
                                });

                                dateColumm.setCellFactory((TableColumn<Occurrence, Date> column) -> {
                                    return new TableCell<Occurrence, Date>() {
                                        @Override
                                        protected void updateItem(Date item, boolean empty) {
                                            super.updateItem(item, empty);

                                            if (item == null || empty) {
                                                setText(null);
                                                setStyle("");
                                            } else {
                                                setText(item.toString());
                                                setTextFill(Color.BLACK);
                                                setStyle("-fx-background-color: yellow");

                                            }
                                        }
                                    };
                                });
                            } else if (item.compareTo("ERRO") == 0) {
                                setTextFill(Color.WHITE);
                                setStyle("-fx-background-color: red");
                                descriptionColumm.setCellFactory((TableColumn<Occurrence, String> column) -> {
                                    return new TableCell<Occurrence, String>() {
                                        @Override
                                        protected void updateItem(String item, boolean empty) {
                                            super.updateItem(item, empty);

                                            if (item == null || empty) {
                                                setText(null);
                                                setStyle("");
                                            } else {
                                                setText(item);
                                                setTextFill(Color.WHITE);
                                                setStyle("-fx-background-color: red");

                                            }
                                        }
                                    };
                                });

                                dateColumm.setCellFactory((TableColumn<Occurrence, Date> column) -> {
                                    return new TableCell<Occurrence, Date>() {
                                        @Override
                                        protected void updateItem(Date item, boolean empty) {
                                            super.updateItem(item, empty);

                                            if (item == null || empty) {
                                                setText(null);
                                                setStyle("");
                                            } else {
                                                setText(item.toString());
                                                setTextFill(Color.WHITE);
                                                setStyle("-fx-background-color: red");

                                            }
                                        }
                                    };
                                });
                            } else {
                                setTextFill(Color.BLACK);
                                setStyle("");
                            }
                        }
                    }
                };
            });

            occurrenceList = FXCollections.observableArrayList();
            resultList.stream().forEach((result) -> {
                occurrenceList.add(result);
            });
            occurrenceTable.setItems(occurrenceList);
        } else {
            AlertDialog.NothingToReference();
        }

    }

    public List<Occurrence> saveOccurrence(int eventSize) throws Exception {

        Occurrence occurr = new Occurrence();
        List<Occurrence> occurrList = new ArrayList<>();
        LocalDateTime timePoint = LocalDateTime.now();
        DeviceDAO deviceDao = new DeviceDAO();

        List<Device> deviceList = deviceDao.findDeviceEntities();
        int id = 0;
        for (Device p : deviceList) {
            if ((p.getName().compareToIgnoreCase(device.getName())) == 0) {
                id = p.getDeviceId();
            }
        }
        Device deviceReference = deviceDao.findDevice(id);
        Data dataReference = deviceReference.getData();
        Limit limitReference = deviceReference.getLimit();
        DataEventDAO eventDAO = new DataEventDAO();
        List<DataEvent> eventsReference = eventDAO.findDataEventEntities();

        Collections.sort(eventsReference, new Comparator() {
            public int compare(Object o1, Object o2) {
                DataEvent p1 = (DataEvent) o1;
                DataEvent p2 = (DataEvent) o2;
                return p1.getDistance() < p2.getDistance() ? -1 : (p1.getDistance() > p2.getDistance() ? +1 : 0);
            }
        });
        for (int i = 0; i < eventsReference.size(); i++) {
            DataEvent dataEvent = eventsReference.get(i);
            System.out.println("distanceReference" + i + " = " + dataEvent.getDistance());
            System.out.println("AttenuationReference" + i + " = " + dataEvent.getAverageAttenuationCoefficient());

        }

        Data dataNow = receiveParameters.getData();
        Limit limitNow = limits;
        ArrayList<DataEvent> eventsNow = (ArrayList<DataEvent>) receiveParameters.getData().getDataEventList();
        Device deviceNow = device;

        for (int i = 0; i < eventsNow.size(); i++) {
            DataEvent dataEvent = eventsNow.get(i);
            System.out.println("distanceNow" + i + " = " + dataEvent.getDistance());
            System.out.println("AttenuationNow" + i + " = " + dataEvent.getAverageAttenuationCoefficient());

        }

        float a3 = eventsReference.get(eventsReference.size() - 1).getDistance();
        float tmp = a3 * (limitReference.getDistanceGreen() / 100.0f);

        float a3_i = a3 - tmp;
        float a3_s = a3 + tmp;
        a3 = eventsNow.get(eventsNow.size() - 1).getDistance();

        occurr.setType("OK");
        occurr.setDescription("Nenhum erro foi encontrado!");
        occurr.setCreateTime(new Date());

        if (a3 < a3_i) {
            occurr.setType("ERRO");
            occurr.setDescription("Erro! Rompimento detectado! Distância = " + a3);
            occurr.setCreateTime(new Date());
        } else if (a3 > a3_s) {
            occurr.setType("ERRO");
            occurr.setDescription("Erro! Distância maior que a referência.");
            occurr.setCreateTime(new Date());
        } else {

            for (int i = 0; i < eventsReference.size(); i++) {
                float temp = eventsReference.get(i).getInsertionLoss();
                temp = Math.abs(temp) * (limits.getInsertionYellow() / 100.0f);
                float nb = Math.abs(eventsNow.get(i).getInsertionLoss()) - Math.abs(eventsReference.get(i).getInsertionLoss());

                if (nb > 0 && nb > temp) {
                    occurr.setType("ATENÇÃO");
                    occurr.setDescription("Info! No evento " + (i + 1) + " Insertion loss aumentou a perda em " + (nb - temp) + " db.");
                    occurr.setCreateTime(new Date());
                    occurrList.add(occurr);
                }
                temp = eventsReference.get(i).getEchoLoss();
                System.out.println(temp);
                temp = Math.abs(temp) * (limits.getReflectionYellow() / 100.0f);
                float nc = Math.abs(eventsNow.get(i).getEchoLoss()) - Math.abs(eventsReference.get(i).getEchoLoss());
                System.out.println(temp);
                System.out.println(nc);
                if (nc > 0 && nc > temp) {
                    occurr.setType("ATENÇÃO");
                    occurr.setDescription("Info! No evento " + (i + 1) + " Reflection loss aumentou a perda em " + (nc - temp) + " db.");
                    occurr.setCreateTime(new Date());
                    occurrList.add(occurr);
                }
                temp = eventsReference.get(i).getAcumulativeLoss();
                temp = Math.abs(temp) * (limits.getAcumulationYellow() / 100.0f);

                float nd = Math.abs(eventsNow.get(i).getAcumulativeLoss()) - Math.abs(eventsReference.get(i).getAcumulativeLoss());

                if (nd > 0 && nd > temp) {
                    occurr.setType("ATENÇÃO");
                    occurr.setDescription("Info! No evento " + (i + 1) + " Acumulative loss aumentou a perda em " + (nd - temp) + " db.");
                    occurr.setCreateTime(new Date());
                    occurrList.add(occurr);
                }
                temp = eventsReference.get(i).getAverageAttenuationCoefficient();
                temp = Math.abs(temp) * (limits.getAttenuationYellow() / 100.0f);
                float ne = Math.abs(eventsNow.get(i).getAverageAttenuationCoefficient()) - Math.abs(eventsReference.get(i).getAverageAttenuationCoefficient());

                if (ne > temp) {
                    occurr.setType("ATENÇÃO");
                    occurr.setDescription("Info! No evento " + (i + 1) + " Attenuation Coefficient aumentou o coeficiente de atenuação em " + (ne - temp) + " db.");
                    occurr.setCreateTime(new Date());
                    occurrList.add(occurr);
                }
            }
            
            if (occurr.getType().compareTo("OK") == 0) {
                occurrList.add(occurr);
            }
            return occurrList;
        }

        occurrList.add(occurr);
        System.out.println("Size = " + occurrList.size());
        return occurrList;
    }
}

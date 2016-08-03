/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.view;

import com.net.multiway.ofm.MainApp;
import com.net.multiway.ofm.daos.DataDAO;
import com.net.multiway.ofm.daos.DataEventDAO;
import com.net.multiway.ofm.daos.DataGraphicDAO;
import com.net.multiway.ofm.daos.DeviceDAO;
import com.net.multiway.ofm.daos.LimitDAO;
import com.net.multiway.ofm.daos.ParameterDAO;
import com.net.multiway.ofm.daos.UserDAO;
import com.net.multiway.ofm.entities.Data;
import com.net.multiway.ofm.entities.DataEvent;
import com.net.multiway.ofm.entities.DataGraphic;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.Limit;
import com.net.multiway.ofm.entities.Parameter;
import com.net.multiway.ofm.entities.User;
import com.net.multiway.ofm.exception.AlertDialog;

import com.net.multiway.ofm.model.ControllerExec;
import com.net.multiway.ofm.model.DeviceComunicator;
import com.net.multiway.ofm.model.Mode;
import com.net.multiway.ofm.model.View;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author rafael
 */
public class ConfigurationWindowController extends ControllerExec {

    private ObservableList<Device> devicesData = FXCollections.observableArrayList();

    //device
    @FXML
    private ListView<Device> devicesList;

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
    private Button buttonSave;
    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonExecute;
    @FXML
    private Button buttonExport;
    @FXML
    private Button buttonReference;
    @FXML
    private Button buttonMonitor;

    @FXML
    private Label executionLabel;
    @FXML
    private Button buttonEditLimit;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private LineChart<?, ?> grafico;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private NumberAxis xAxis;

    public void setLimits(Limit limits) {
        this.limits = limits;
    }

    public void setExecutionLabel(String msg) {
        executionLabel.setText(msg);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        executionLabel.setVisible(false);

        DeviceDAO dao = new DeviceDAO();
        devicesData.addAll(dao.findDeviceEntities());
        devicesList.setItems(devicesData);
        updateDeviceList();

        if (devicesList.getItems().size() > 0) {
            device = devicesList.getItems().get(0);
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, "Devices carregados na tela...");
            parameters = device.getParameter();
            limits = device.getLimit();
        } else {
            device = null;
        }

        if (parameters == null) {
            parameters = new Parameter(0, 0, 15000, 1550, 1, 1.4685f, 0, 5.0f, 65.0f, 0, 1, 1, 10, new Date());
        }

        if (limits == null) {
            limits = new Limit(3, 3, 3, 5, 5, 5, 3, 5, 5, 3, new Date());
        }

        updateParameters();

        measureRangeField.setValue(parameters.getMeasuringRangeOfTest() * 1000);
        pulseWidthField.setValue(parameters.getTestPulseWidth());
        measureTimeField.setValue(parameters.getMeasuringTime() / 1000);
        waveLengthField.setValue(parameters.getTestWaveLength());
        if (parameters.getMeasureMode() == 1) {
            measureModeField.setValue("1-Average");
        } else {
            measureModeField.setValue("2-Real Time");
        }
        refractiveIndexField.setText(parameters.getRefractiveIndex().toString());
        nonReflactionThresholdField.setText(parameters.getNonReflactionThreshold().toString());
        endThresholdField.setText(parameters.getEndThreshold().toString());
        reflectionThresholdField.setText(parameters.getReflectionThreshold().toString());

        String msg = "Parâmetros carregados na tela...";
        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);

        cycleTimeField.setText(parameters.getCycleTime().toString());
        //prepareMenu(Mode.EDIT);
        progressBar = new ProgressBar();

    }

    @FXML
    private void onClickDeviceSelection() {
        device = devicesList.getSelectionModel().getSelectedItem();

        if (device.getDeviceId() != null) {
            DeviceDAO daop = new DeviceDAO();
            Device ref = daop.findDevice(device.getDeviceId());
            parameters = ref.getParameter();
        } else {
            parameters = new Parameter(10000, 0, 2000, 1550, 1, 1.4685f, 0, 5.0f, 65.0f, 0, 1, 1, 10, new Date());
        }
    }

    /**
     * Chamado quando o usuário clica no botão delete.
     */
    @FXML
    private void onHandleDeleteDevice() {

        int selectedIndex = devicesList.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            if (AlertDialog.DeviceDeletion(devicesList.getSelectionModel().getSelectedItem().getIp())) {
                if (device.getDeviceId() != null) {
                    try {
                        DeviceDAO daoRef = new DeviceDAO();
                        daoRef.destroy(device.getDeviceId());

                    } catch (Exception ex) {
                        Logger.getLogger(ConfigurationWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        AlertDialog.exception(ex);
                    }
                }

                devicesList.getItems().remove(selectedIndex);
                String msg = "Device removido com sucesso.";
                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);

                if (devicesList.getItems().size() > 0) {
                    device = devicesList.getItems().get(0);
                }

            }

        } else {
            String msg = "Nenhum device selecionado para deletar.";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
            AlertDialog.DeviceSelection();
        }

        updateDeviceList();
    }

    /**
     * Chamado quando o usuário clica no botão delete.
     */
    @FXML
    private void onHandleAddDevice() {

        if (device == null) {
            Device device = new Device();
            try {
                // Carrega o arquivo fxml e cria um novo stage para a janela popup.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource(View.DeviceAddDialog.getResource()));
                AnchorPane page;

                page = (AnchorPane) loader.load();

                // Cria o palco dialogStage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Editar dispositivo");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(MainApp.getInstance().getPrimaryStage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Define o device no controller.
                DeviceAddDialogController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setDevice(device);

                // Mostra a janela e espera até o usuário fechar.
                dialogStage.showAndWait();

                if (controller.isOkClicked()) {
//                DataDeviceDAO dao = new DataDeviceDAO();
//                dao.create(device);
                    devicesData.add(device);
                    devicesList.setItems(devicesData);
                    this.device = device;
                }
            } catch (IOException ex) {
                Logger.getLogger(ConfigurationWindowController.class.getName()).log(Level.SEVERE, null, ex);
                AlertDialog.exception(ex);
            }

            updateDeviceList();
        } else {
            String msg = "Não é possivel adicionar mais de um dispositivo.";
            AlertDialog.deviceErro(msg);
        }

    }

    @FXML
    private void onHandleEditParameters() {
        prepareForm(Mode.EDIT);
    }

    private boolean validateParametersField() {
        if (measureRangeField.getValue() == null) {
            AlertDialog.IncorrectField("Measuring Range Of Test");
            return false;
        } else if (pulseWidthField.getValue() == null) {
            AlertDialog.IncorrectField("Test Pulse Width");
            return false;
        } else if (measureTimeField.getValue() == null) {
            AlertDialog.IncorrectField("Measuring Time");
            return false;
        } else if (waveLengthField.getValue() == null) {
            AlertDialog.IncorrectField("Test Wave Length");
            return false;
        } else if (measureModeField.getValue() == null) {
            AlertDialog.IncorrectField("Measure Mode");
            return false;
        } else if (refractiveIndexField.getText().isEmpty()) {
            AlertDialog.IncorrectField("Refractive Index");
            return false;
        } else if (nonReflactionThresholdField.getText().isEmpty()) {
            AlertDialog.IncorrectField("Non Reflaction Threshold");
            return false;
        } else if (endThresholdField.getText().isEmpty()) {
            AlertDialog.IncorrectField("End Threshold");
            return false;
        } else if (reflectionThresholdField.getText().isEmpty()) {
            AlertDialog.IncorrectField("Reflection Threshold");
            return false;
        } else if (cycleTimeField.getText().isEmpty()) {
            AlertDialog.IncorrectField("Cycle Time");
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void onHandleSaveParameters() {
        if (validateParametersField()) {
            float nonReflThresh = Float.parseFloat(nonReflactionThresholdField.getText());
            float endThresh = Float.parseFloat(endThresholdField.getText());
            float reflectionThresh = Float.parseFloat(reflectionThresholdField.getText());

            if (nonReflThresh < 0 || nonReflThresh > 10) {
                AlertDialog.IncorrectRangeField("NonReflaction Threshold", 0, 10);
            } else if (endThresh < 0 || endThresh > 10) {
                AlertDialog.IncorrectRangeField("End Threshold", 0, 10);
            } else if (reflectionThresh < 20 || reflectionThresh > 80) {
                AlertDialog.IncorrectRangeField("Reflaction Threshold", 20, 80);
            } else {
                try {
                    parameters.setMeasuringRangeOfTest(Integer.parseInt(measureRangeField.getValue().toString()) * 1000);
                    parameters.setTestPulseWidth(Integer.parseInt(pulseWidthField.getValue().toString()));
                    parameters.setMeasuringTime(Integer.parseInt(measureTimeField.getValue().toString()) * 1000);
                    parameters.setTestWaveLength(Integer.parseInt(waveLengthField.getValue().toString()));
                    parameters.setMeasureMode((measureModeField.getValue().toString() == "1-Average") ? 1 : 2);
                    parameters.setRefractiveIndex(Float.parseFloat(refractiveIndexField.getText()));
                    parameters.setNonReflactionThreshold(Float.parseFloat(nonReflactionThresholdField.getText()));
                    parameters.setEndThreshold(Float.parseFloat(endThresholdField.getText()));
                    parameters.setReflectionThreshold(Float.parseFloat(reflectionThresholdField.getText()));
                    parameters.setCycleTime(Integer.parseInt(cycleTimeField.getText()));
                } catch (Exception ex) {
                    Logger.getLogger(ConfigurationWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    AlertDialog.exception(ex);
                    return;
                }
                prepareForm(Mode.VIEW);
            }

        }
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

                Service service = new Service() {
                    @Override
                    protected Task createTask() {
                        return new Task() {
                            @Override
                            protected Object call() throws Exception {
                                if (resultTable.getItems().size() > 0 && grafico.getData().size() > 0) {
                                    resultTable.getItems().remove(0, resultTable.getItems().size());

                                }
                                buttonExecute.setDisable(true);
                                buttonMonitor.setDisable(true);

                                host.connect(parameters);
//                                for (int i = 0; i < 100; i++) {
//                                    updateProgress(i, 100);
//                                    try {
//                                        Thread.sleep(100);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                }

                                return null;
                            }

                            @Override
                            protected void failed() {
                                super.failed();
                                buttonExecute.setDisable(false);
                                buttonMonitor.setDisable(false);
                                AlertDialog.timeOut(device.getIp());
                            }

                            @Override
                            protected void succeeded() {

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
                                    msg = "Gráfico plotado na tela de configuração.";
                                    Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                                    executionLabel.setText(msg);
                                    try {
                                        host.closeConnection();
                                    } catch (Exception ex) {
                                        Logger.getLogger(ConfigurationWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    buttonExport.setDisable(false);
                                    buttonReference.setDisable(false);
                                    buttonExecute.setDisable(false);
                                    buttonMonitor.setDisable(false);
                                    executionLabel.setVisible(false);
                                } else {
                                    System.out.println("Deu erro!");
                                    buttonExport.setDisable(false);
                                    buttonReference.setDisable(false);
                                    buttonExecute.setDisable(false);
                                    buttonMonitor.setDisable(false);
                                    executionLabel.setVisible(false);
                                }

                            }

                        };
                    }
                };
//                progressBar.progressProperty().bind(service.progressProperty());
                service.start();

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
    private void onHandleSetReference() {
        if (receiveParameters != null && receiveValues != null && device != null && limits != null) {

            Service service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            DeviceDAO deviceDao = new DeviceDAO();
                            ParameterDAO parameterDao = new ParameterDAO();
                            LimitDAO limitDao = new LimitDAO();
                            DataDAO dataDao = new DataDAO();
                            DataGraphicDAO dataGraphicDao = new DataGraphicDAO();
                            DataEventDAO dataEventDao = new DataEventDAO();
                            UserDAO userDao = new UserDAO();

                            try {
                                List<Device> deviceList = deviceDao.findDeviceEntities();
                                Device d;
                                for (Device p : deviceList) {
                                    if ((p.getName().compareToIgnoreCase(device.getName())) == 0) {
                                        device.setDeviceId(p.getDeviceId());
                                    }
                                }
                                if (device.getDeviceId() != null) {
                                    d = deviceList.get(0);
                                    deviceDao.destroy(d.getDeviceId());
                                }

                                User user = userDao.findUser(1);

                                d = new Device();
                                d.setGateway(device.getGateway());
                                d.setIp(device.getIp());
                                d.setMask(device.getMask());
                                d.setName("Device 1");
                                d.setUser(user);
                                d.setCreateTime(new Date());
                                d.setStatus("Active");
                                deviceDao.create(d);

                                Parameter parameter = new Parameter();
                                parameters.setDevice(d); // enlace bidirecional
                                parameters.setUser(user);
                                parameters.setCreateTime(new Date());
                                parameter.copy(parameters);
                                parameterDao.create(parameter);
                                d.setParameter(parameter); // enlace bidirecional

                                Limit limit = new Limit();
                                limit.setDevice(d); // enlace bidirecional
                                limit.setUser(user);
                                limit.setCreateTime(new Date());
                                limit.copy(limits);
                                limitDao.create(limit);
                                d.setLimit(limit); //enlace bidirecional

                                Data data = new Data();
                                data.setDevice(d); //enlace bidirecional
                                data.setUser(user);
                                data.setCreateTime(new Date());
                                data.copy(receiveParameters.getData());
                                dataDao.create(data);
                                d.setData(data); //enlace bidirecional

                                ArrayList<DataEvent> even = (ArrayList<DataEvent>) receiveParameters.getData().getDataEventList();
                                System.out.println("size = " + (even.size() - 1));
                                for (int i = 0; i < even.size(); i++) {
                                    DataEvent dataEvent = even.get(i);
                                    System.out.println("distance" + i + " = " + dataEvent.getDistance());
                                    dataEvent.setData(data);
                                    data.getDataEventList().add(dataEvent);
                                }
                                ArrayList<DataGraphic> list = (ArrayList<DataGraphic>) receiveParameters.getData().getDataGraphicList();
                                for (int i = 0; i < list.size(); i++) {
                                    DataGraphic dataGraphic = list.get(i); //enlace bidirecional
                                    dataGraphic.setData(data);
                                    data.getDataGraphicList().add(dataGraphic);

                                }

                                dataEventDao.createAll((ArrayList<DataEvent>) receiveParameters.getData().getDataEventList());
                                dataGraphicDao.createAll((ArrayList<DataGraphic>) receiveParameters.getData().getDataGraphicList());

                                device = d;
                                parameters = parameter;
                                limits = limit;
                                receiveParameters.setData(data);

                            } catch (Exception ex) {
                                Logger.getLogger(ConfigurationWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                AlertDialog.exception(ex);
                            }
                            return null;
                        }
                    };
                }
            };
            service.start();
            buttonReference.setDisable(true);

        } else {
            String msg = "Não há dados a serem salvos como referência.";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
            AlertDialog.NothingToReference();
        }
    }

    @FXML
    private void onHandleChangeToMonitor() throws IOException {
        DeviceDAO deviceDao = new DeviceDAO();

        List<Device> deviceList = deviceDao.findDeviceEntities();
//        for (Device p : deviceList) {
//            if ((p.getName().compareToIgnoreCase(device.getName())) == 0) {
        Device deviceReference = deviceList.get(0);
//            }
//        }

        if (deviceReference != null) {

            MonitorWindowController controller
                    = (MonitorWindowController) MainApp.getInstance().showView(View.MonitorWindow, Mode.VIEW);

            controller.setReference(deviceReference, user);

        } else {
            AlertDialog.referenceMissing();
        }
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
                cycleTimeField.setDisable(true);
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
                cycleTimeField.setDisable(false);
                buttonSave.setDisable(false);

                break;
        }
    }

    @Override
    public void prepareMenu(Mode mode) {
        switch (mode) {
            case VIEW:
                MainApp.getInstance().disable(Menu.Print, true);
                MainApp.getInstance().disable(Menu.ExportEL, true);
                MainApp.getInstance().disable(Menu.ExportLR, true);
                MainApp.getInstance().disable(Menu.ExportOL, true);
                MainApp.getInstance().disable(Menu.Print, true);
                break;
            case EDIT:
                System.out.println("Aqui!");
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

    private void updateParameters() {

        ObservableList<Integer> param1 = FXCollections.observableArrayList(new Integer[]{0, 1, 5, 10, 30, 60, 80, 120});
        measureRangeField.setItems(param1);

        ObservableList<Integer> param2 = FXCollections.observableArrayList(new Integer[]{10, 20, 50, 100, 200, 500, 1000, 2000, 10000, 20000});
        pulseWidthField.setItems(param2);

        ObservableList<Integer> param3 = FXCollections.observableArrayList(new Integer[]{15, 30, 60, 120, 180});
        measureTimeField.setItems(param3);

        ObservableList<Integer> param4 = FXCollections.observableArrayList(new Integer[]{1310, 1550});
        waveLengthField.setItems(param4);

        ObservableList<String> param5 = FXCollections.observableArrayList(new String[]{"1-Average", "2-Real Time"});
        measureModeField.setItems(param5);

        refractiveIndexField.setText("");

        nonReflactionThresholdField.setText("");

        endThresholdField.setText("");

        reflectionThresholdField.setText("");
    }

    private void updateDeviceList() {
        devicesList.setCellFactory(new Callback<ListView<Device>, ListCell<Device>>() {
            @Override
            public ListCell<Device> call(ListView<Device> p) {

                ListCell<Device> cell = new ListCell<Device>() {

                    @Override
                    protected void updateItem(Device t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getIp());
                        }
                    }

                };

                return cell;
            }
        });
    }

    @FXML
    private void onHandleEditLimit(ActionEvent event) {
        Limit limit = new Limit();
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(View.RangeDialog.getResource()));
            AnchorPane page;

            page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Limites");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainApp.getInstance().getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

//            // Define o device no controller.
            RangeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, limits);

            // Mostra a janela e espera até o usuário fechar.
            dialogStage.showAndWait();
            this.limits = controller.getLimits();

        } catch (IOException ex) {
            Logger.getLogger(ConfigurationWindowController.class.getName()).log(Level.SEVERE, null, ex);
            AlertDialog.exception(ex);
        }

    }

    void setUser(User user) {
        this.user = user;
    }

}

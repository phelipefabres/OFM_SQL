/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.view;

import com.net.multiway.ofm.MainApp;
import com.net.multiway.ofm.daos.DeviceDAO;
import com.net.multiway.ofm.daos.UserDAO;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.User;
import com.net.multiway.ofm.exception.AlertDialog;
import com.net.multiway.ofm.model.IController;
import com.net.multiway.ofm.model.Mode;
import com.net.multiway.ofm.model.View;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author phelipe
 */
public class LoginWindowController implements Initializable, IController {

    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private MainSceneController rootController;
    private Node configNode;
    private IController configController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //prepareMenu(Mode.VIEW);
    }

    @FXML
    private void onHandleLogin(ActionEvent event) {
        UserDAO userDao = new UserDAO();

        List<User> userList = userDao.findByUsername(userField.getText());

        if (userList.isEmpty()) {
            onHandleAddUser();
        } else {
            User user = null;
            for (User p : userList) {
                if (p.getUsername().compareToIgnoreCase(userField.getText()) == 0 && p.getPassword().compareTo(passwordField.getText()) == 0) {
                    user = p;
                }
            }
            if (user != null) {
                if (user.getisAdmin() == 1) {
                    ConfigurationWindowController controller
                            = (ConfigurationWindowController) MainApp.getInstance().showView(View.ConfigurationWindow, Mode.EDIT);

                    controller.setUser(user);
                    String msg = "ConfigurationWindow inicializada...";
                    Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                } else {
                    DeviceDAO dao = new DeviceDAO();
                    List<Device> deviceList = dao.findDeviceEntities();
                    if (deviceList.size() < 1) {
                        String msg = "Não há referência. Contacte o administrador para gerar uma referência.";
                        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                        AlertDialog.referenceMissing();
                    } else {

                        Device deviceReference = deviceList.get(0);

                        if (deviceReference != null) {

                            MonitorWindowController controller
                                    = (MonitorWindowController) MainApp.getInstance().showView(View.MonitorWindow, Mode.VIEW);

                            controller.setReference(deviceReference, user);

                        } else {
                            AlertDialog.referenceMissing();
                        }

                    }
                }
            } else {
                String msg = "Usuário ou senha incorreto.";
                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                AlertDialog.LoginInvalid();
            }

        }
        System.out.println("Fim da execução de login.");
    }

    @Override
    public void handleSave(ActionEvent event) {

    }

    @Override
    public void prepareForm(Mode mode) {

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

            default:
                throw new AssertionError(mode.name());

        }

    }

    private void onHandleAddUser() {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(View.LoginCreateWindow.getResource()));
            AnchorPane page;

            page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Criar Usuário");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainApp.getInstance().getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Define o device no controller.
            LoginCreateWindowController controller = loader.getController();
            controller.setDialogStage(dialogStage);
           // controller.setDevice(device);

            // Mostra a janela e espera até o usuário fechar.
            dialogStage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(ConfigurationWindowController.class.getName()).log(Level.SEVERE, null, ex);
            AlertDialog.exception(ex);
        }
    }

}

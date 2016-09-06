/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.view;

import com.net.multiway.ofm.MainApp;
import com.net.multiway.ofm.daos.UserDAO;
import com.net.multiway.ofm.entities.User;
import com.net.multiway.ofm.exception.AlertDialog;
import com.net.multiway.ofm.model.IController;
import com.net.multiway.ofm.model.Mode;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author phelipe
 */
public class LoginCreateWindowController implements Initializable, IController {

    private Stage dialogStage;
    @FXML
    private TextField userField;
    @FXML
    private TextField passField;
    private TextField confirmPassField;
    @FXML
    private CheckBox admCheckbox;
    @FXML
    private TextField emailField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onHandleOk(ActionEvent event) {
        UserDAO userDao = new UserDAO();

        List<User> userList = userDao.findByUsername(userField.getText());

        if (userList.isEmpty()) {
            List<User> email = userDao.findByEmail(emailField.getText());
            if (email.isEmpty()) {
                User user = new User();
                user.setUsername(userField.getText());
                user.setEmail(emailField.getText());
                user.setPassword(passField.getText());
                user.setisAdmin(admCheckbox.isSelected() ? 1 : 0);
                user.setCreateTime(new Date());
                userDao.create(user);

            } else {
                String msg = "Email existente!";
                Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
                AlertDialog.emailInvalid();
            }
        } else {
            String msg = "Usuário já existente.";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
            AlertDialog.LoginInvalid();
        }
        dialogStage.close();
    }

    @FXML
    private void onHandleCancel(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    private void onHandleCheckbox(ActionEvent event) {
    }

    @Override
    public void handleSave(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prepareForm(Mode mode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prepareMenu(Mode mode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

}

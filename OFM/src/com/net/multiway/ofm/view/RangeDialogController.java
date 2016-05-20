/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.view;

import com.net.multiway.ofm.daos.LimitDAO;
import com.net.multiway.ofm.entities.Limit;
import com.net.multiway.ofm.model.IController;
import com.net.multiway.ofm.model.Mode;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author phelipe
 */
public class RangeDialogController implements Initializable, IController {

    private Stage dialogStage;
    @FXML
    private TextField insertionGreenField;
    @FXML
    private TextField reflectionGreenField;
    @FXML
    private TextField distanceGreenField;
    @FXML
    private TextField distanceYellowField;
    @FXML
    private TextField insertionYellowField;
    @FXML
    private TextField reflectionYellowField;
    @FXML
    private TextField distanceRedField;
    @FXML
    private TextField insertionRedField;
    @FXML
    private TextField reflectionRedField;
    @FXML
    private TextField attenuationGreenField;
    @FXML
    private TextField attenuationYellowField;
    @FXML
    private TextField attenuationRedField;
    @FXML
    private TextField acumulationRedField;
    @FXML
    private TextField acumulationYellowField;
    @FXML
    private TextField acumulationGreenField;

    private Limit limits;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonSave;

    public Limit getLimits() {
        return limits;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        LimitDAO dao = new LimitDAO(Persistence.createEntityManagerFactory("OFMPU"));
        limits = dao.findLimit(1);
        if (limits == null) {
            limits = new Limit(3, 3, 3, 5, 5, 5, 3, 5, 5, 3, new Date());
        }

        distanceGreenField.setText(limits.getDistanceGreen().toString());
        distanceYellowField.setText(limits.getDistanceYellow().toString());
        distanceRedField.setText(limits.getDistanceYellow().toString());

        insertionGreenField.setText(limits.getInsertionGreen().toString());
        insertionYellowField.setText(limits.getInsertionYellow().toString());
        insertionRedField.setText(limits.getInsertionYellow().toString());

        reflectionGreenField.setText(limits.getReflectionGreen().toString());
        reflectionYellowField.setText(limits.getReflectionYellow().toString());
        reflectionRedField.setText(limits.getReflectionYellow().toString());

        acumulationGreenField.setText(limits.getAcumulationGreen().toString());
        acumulationYellowField.setText(limits.getAcumulationYellow().toString());
        acumulationRedField.setText(limits.getAcumulationYellow().toString());

        attenuationGreenField.setText(limits.getAttenuationGreen().toString());
        attenuationYellowField.setText(limits.getAttenuationYellow().toString());
        attenuationRedField.setText(limits.getAttenuationYellow().toString());

        prepareForm(Mode.VIEW);

    }

    /**
     * Define o palco deste dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Define a pessoa a ser editada no dialog.
     *
     * @param vehicle
     */
    public void setRange() {
    }

    @FXML
    private void onHandleEdit(ActionEvent event) {
        prepareForm(Mode.EDIT);
    }

    @FXML
    private void onHandleSave(ActionEvent event) throws Exception {
        limits.setAcumulationGreen(Float.parseFloat(acumulationGreenField.getText()));
        limits.setAcumulationYellow(Float.parseFloat(acumulationYellowField.getText()));

        limits.setAttenuationGreen(Float.parseFloat(attenuationGreenField.getText()));
        limits.setAttenuationYellow(Float.parseFloat(attenuationYellowField.getText()));

        limits.setDistanceGreen(Float.parseFloat(distanceGreenField.getText()));
        limits.setDistanceYellow(Float.parseFloat(distanceYellowField.getText()));

        limits.setInsertionGreen(Float.parseFloat(insertionGreenField.getText()));
        limits.setInsertionYellow(Float.parseFloat(insertionYellowField.getText()));

        limits.setReflectionGreen(Float.parseFloat(reflectionGreenField.getText()));
        limits.setReflectionYellow(Float.parseFloat(reflectionYellowField.getText()));

        distanceRedField.setText(limits.getDistanceYellow().toString());
        insertionRedField.setText(limits.getInsertionYellow().toString());
        reflectionRedField.setText(limits.getReflectionYellow().toString());
        acumulationRedField.setText(limits.getAcumulationYellow().toString());
        attenuationRedField.setText(limits.getAttenuationYellow().toString());

        
        prepareForm(Mode.VIEW);
    }

    @Override
    public void handleSave(ActionEvent event) {

    }

    @Override
    public void prepareForm(Mode mode) {
        switch (mode) {
            case VIEW:
                distanceGreenField.setDisable(true);
                distanceYellowField.setDisable(true);
                distanceRedField.setDisable(true);

                insertionGreenField.setDisable(true);
                insertionYellowField.setDisable(true);
                insertionRedField.setDisable(true);

                reflectionGreenField.setDisable(true);
                reflectionYellowField.setDisable(true);
                reflectionRedField.setDisable(true);

                acumulationGreenField.setDisable(true);
                acumulationYellowField.setDisable(true);
                acumulationRedField.setDisable(true);

                attenuationGreenField.setDisable(true);
                attenuationYellowField.setDisable(true);
                attenuationRedField.setDisable(true);
                buttonSave.setDisable(true);
                buttonEdit.setDisable(false);
                break;
            case EDIT:
                distanceGreenField.setDisable(false);
                distanceYellowField.setDisable(false);

                insertionGreenField.setDisable(false);
                insertionYellowField.setDisable(false);

                reflectionGreenField.setDisable(false);
                reflectionYellowField.setDisable(false);

                acumulationGreenField.setDisable(false);
                acumulationYellowField.setDisable(false);

                attenuationGreenField.setDisable(false);
                attenuationYellowField.setDisable(false);
                buttonSave.setDisable(false);
                buttonEdit.setDisable(true);
                break;
        }
    }

    @Override
    public void prepareMenu(Mode mode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void onHandleClose(ActionEvent event) {
        dialogStage.close();
    }

}

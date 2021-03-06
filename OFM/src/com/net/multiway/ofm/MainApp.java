/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm;

import com.net.multiway.ofm.database.Database;
import com.net.multiway.ofm.model.IController;
import com.net.multiway.ofm.model.Mode;
import com.net.multiway.ofm.model.View;
import com.net.multiway.ofm.view.MainSceneController;
import com.net.multiway.ofm.view.Menu;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    private static MainApp instance;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private MainSceneController rootController;
    private Node configNode;
    private IController configController;

    public static MainApp getInstance() {
        return instance;
    }

    public MainApp() {

    }

    /**
     * Shows the stage with the Voting Process Insert View
     *
     * @param primaryStage stage to show
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Optical Fiber Monitor");
        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        initRootLayout();
        // Shows the scene containing RootLayout
        Scene scene = new Scene(rootLayout);
        this.primaryStage.getIcons().add(new Image("file:monitor_graph.jpg"));
        this.primaryStage.setMaximized(true);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private void initRootLayout() {
        try {
            // Loads the root layout from fxml file
            FXMLLoader rootLoader = new FXMLLoader();
            rootLoader.setLocation(MainApp.class.getResource(View.MainScene.getResource()));
            rootLayout = (BorderPane) rootLoader.load();

            // Gets RootLayoutController
            rootController = rootLoader.getController();
            rootController.prepareForm(null);
            rootController.prepareMenu(null);
            String msg = "MainScene inicializada...";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(View.LoginWindow.getResource()));
            configNode = loader.load();
            configController = (IController) loader.getController();
            configController.prepareForm(Mode.VIEW);
            configController.prepareMenu(Mode.VIEW);
            rootController.setCenterController(configController);
            rootLayout.setCenter((AnchorPane) configNode);

            msg = "LoginWindow inicializada...";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);

            Database.getInstance().getEntityManagerFactory();

            msg = "Banco de dados carregado...";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retorna o palco principal.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Shows a view on screen <br />
     * - loads the FXML via its resource (String file name); <br />
     * - creates a reference to the view's controller; <br />
     * - prepares the controller according to <code>Mode</code> passed (Default:
     * VIEW) - positions the view in root layout's center; <br />
     * If an exception is caused, logs it.
     *
     * @see Mode
     * @see View
     * @param mode Preparation mode of view: NEW, EDIT or VIEW
     * @param view view to be shown
     */
    public IController showView(View view, Mode mode) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(view.getResource()));
            Node node = loader.load();
            IController controller = (IController) loader.getController();

            controller.prepareForm(mode);
            controller.prepareMenu(mode);

            rootController.setCenterController(controller);
            rootLayout.setCenter((AnchorPane) node);
            String msg = "showView inicializando nova tela...";
            Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);

            return controller;
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

//    public void showConfiguration() {
//
//        rootController.setCenterController(configController);
//        rootLayout.setCenter((AnchorPane) configNode);
//        String msg = "showConfiguration inicializado...";
//        Logger.getLogger(MainApp.class.getName()).log(Level.INFO, msg);
//    }
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param menu Menu to be disabled
     * @param disable <code>true</code> to disable the 'New' Item from
     * Menu<br />
     * <code>false</code> to enable the 'New' Item from Menu<br />
     */
    public void disable(Menu menu, boolean disable) {
        rootController.disable(menu, disable);
    }
}

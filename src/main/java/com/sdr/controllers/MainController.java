package com.sdr.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.naming.ldap.Control;
import java.io.IOException;


public class MainController{

    public Button chooseButton;
    private Stage distributionWindow;
    private Parent root;

    public void showDistributionGenerationWindow(){
        showWindow("/fxml/distributionWindow.fxml", "Distribution generator");
    }

    private void showWindow(String windowFilePath, String title) {
        try {
            root = FXMLLoader.load(getClass().getResource(windowFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        distributionWindow = new Stage();
        distributionWindow.initModality(Modality.APPLICATION_MODAL);
        distributionWindow.setScene(new Scene(root));
        distributionWindow.setTitle(title);
        distributionWindow.showAndWait();
    }
}

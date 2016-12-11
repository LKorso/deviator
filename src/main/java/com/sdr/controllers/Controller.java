package com.sdr.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {
    public Stage distributionWindow;

    public void showDistributionGenerationWindow() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/distributionWindow.fxml"));

        distributionWindow = new Stage();
        distributionWindow.initModality(Modality.APPLICATION_MODAL);
        distributionWindow.setScene(new Scene(root));
        distributionWindow.showAndWait();
    }
}

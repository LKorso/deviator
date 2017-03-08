package com.sdr.controllers;

import com.sdr.domain.Distribution;
import com.sdr.repository.RepositoryFactory;
import com.sdr.services.DistributionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;


public class MainController extends ControllerHelper{

    @FXML
    private Button chooseButton;

    @FXML
    private Button loadButton;

    private Stage distributionWindow;
    private FileChooser fileChooser;
    private DistributionService distributionService;

    public void showDistributionGenerationWindow(){
        final FXMLLoader loader = getLoader("/fxml/distributionGeneratorWindow.fxml");
        showWindow("Distribution generator");
    }

    public void showMainInfoWindow() {
        final Distribution selectedDistribution = loadDistributionFromFile();
        final FXMLLoader loader = getLoader("/fxml/distributionMainInfoWindow.fxml");
        final DistributionMainInfoController controller = loader.<DistributionMainInfoController>getController();
        controller.initializeData(selectedDistribution);
        showWindow("D");
    }

    private Distribution loadDistributionFromFile() {
        fileChooser = configureFileChooser();
        final File selectedFile = fileChooser.showOpenDialog(distributionWindow);
        distributionService = new DistributionService(RepositoryFactory.getReposytory(RepositoryFactory.IN_MEMORY));
        return distributionService.readFromFile(selectedFile);
    }

    private FileChooser configureFileChooser() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file with data");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel files", "*.xlsx")
        );
        return fileChooser;
    }

    private void showWindow(String title) {
        distributionWindow = new Stage();
        distributionWindow.initModality(Modality.APPLICATION_MODAL);
        distributionWindow.setScene(new Scene(root));
        distributionWindow.setTitle(title);
        distributionWindow.showAndWait();
    }
}

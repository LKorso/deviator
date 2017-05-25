package com.sdr.controllers;

import com.sdr.domain.Distribution;
import com.sdr.services.DistributionService;
import com.sdr.spring.config.WindowsConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MainController extends ControllerHelper {

    @FXML
    private Button chooseButton;

    @FXML
    private Button loadButton;

    @Autowired
    @Qualifier("distributionMainInfoWindow")
    private WindowsConfig.Window mainInfoWindow;

    @Autowired
    @Qualifier("distributionGeneratorWindow")
    private WindowsConfig.Window generationWindow;

    @Autowired
    private DistributionMainInfoController distributionMainInfoController;

    private FileChooser fileChooser;

    @Autowired
    private DistributionService distributionService;

    public void showDistributionGenerationWindow() {
        if (generationWindow.getStage() == null) {
            generationWindow.initializeStage(Modality.APPLICATION_MODAL);
        }
        generationWindow.getStage().setTitle("Distribution generator");
        generationWindow.getStage().showAndWait();
    }

    public void showMainInfoWindow() throws Exception {
        fileChooser = configureFileChooser();
        final File selectedFile = fileChooser.showOpenDialog(mainInfoWindow.getStage());
        final Distribution currentDistribution = distributionService.readFromFile(selectedFile);
        distributionService.saveDistribution(currentDistribution);
        distributionService.changeCurrentDistribution(currentDistribution);
        DistributionMainInfoController controller = (DistributionMainInfoController) mainInfoWindow.getController();
        controller.initializeData();
        if (mainInfoWindow.getStage() == null) mainInfoWindow.initializeStage(Modality.APPLICATION_MODAL);
        mainInfoWindow.getStage().setTitle("Distribution");
        mainInfoWindow.getStage().setOnCloseRequest(event -> controller.onClose());
        mainInfoWindow.getStage().showAndWait();
    }

    private FileChooser configureFileChooser() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file with data");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel files", "*.xlsx")
        );
        return fileChooser;
    }
}

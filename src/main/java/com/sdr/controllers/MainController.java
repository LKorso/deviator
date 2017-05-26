package com.sdr.controllers;

import com.sdr.domain.Distribution;
import com.sdr.services.DistributionService;
import com.sdr.spring.config.WindowsConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MainController extends BasicController {

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
        generationWindow.showAndWait();
    }

    public void showMainInfoWindow() throws Exception {
        final Distribution currentDistribution = distributionService.readFromFile(chooseFile());
        distributionService.saveDistribution(currentDistribution);
        distributionService.changeCurrentDistribution(currentDistribution);
        mainInfoWindow.showAndWait();
    }

    private File chooseFile() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file with data");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel files", "*.xlsx")
        );
        return fileChooser.showOpenDialog(mainInfoWindow.getStage());
    }
}

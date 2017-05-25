package com.sdr.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.sdr.domain.Distribution;
import com.sdr.services.DistributionService;
import com.sdr.spring.config.WindowsConfig;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DistributionGeneratorController extends ControllerHelper {

    @FXML
    private TextField number;

    @FXML
    private TextField mean;

    @FXML
    private TextField sd;

    @FXML
    private JFXCheckBox sorted;

    @FXML
    private JFXButton generateButton;

    @Autowired
    private DistributionService distributionService;

    @Autowired
    @Qualifier("distributionMainInfoWindow")
    private WindowsConfig.Window mainInfoWindow;

    public void processGenerateButton() {
        Distribution currentDistribution = createDistribution();
        distributionService.saveDistribution(currentDistribution);
        distributionService.changeCurrentDistribution(currentDistribution);
        showWindow(
                "/fxml/distributionMainInfoWindow.fxml",
                "Normal Distribution",
                currentDistribution
        );
    }

    private Distribution createDistribution() {
        return distributionService.createDistribution(
                Long.valueOf(number.getText()),
                Double.valueOf(mean.getText()),
                Double.valueOf(sd.getText()),
                !sorted.isDisabled()
        );
    }

    private void showWindow(String windowFilePath, String title, Distribution distribution) {
        DistributionMainInfoController controller = (DistributionMainInfoController) mainInfoWindow.getController();
        controller.initializeData();
        if (mainInfoWindow.getStage() == null) mainInfoWindow.initializeStage(Modality.WINDOW_MODAL);
        mainInfoWindow.getStage().setTitle(title);
        mainInfoWindow.getStage().setOnCloseRequest(event -> controller.onClose());
        mainInfoWindow.getStage().show();
    }
}
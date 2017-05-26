package com.sdr.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.sdr.domain.Distribution;
import com.sdr.services.DistributionService;
import com.sdr.spring.config.WindowsConfig;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DistributionGeneratorController extends BasicController {

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
        mainInfoWindow.showWindow();
    }

    private Distribution createDistribution() {
        return distributionService.createDistribution(
                Long.valueOf(number.getText()),
                Double.valueOf(mean.getText()),
                Double.valueOf(sd.getText()),
                !sorted.isDisabled()
        );
    }

}
package com.sdr.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.sdr.domain.Distribution;
import com.sdr.repository.RepositoryFactory;
import com.sdr.services.DistributionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private DistributionService distributionService;
    private Stage window;

    public void processGenerateButton() {
        distributionService = new DistributionService(RepositoryFactory.getReposytory(RepositoryFactory.IN_MEMORY));
        Distribution currentDistribution = createDistribution();
        distributionService.saveDistribution(currentDistribution);
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
        final FXMLLoader loader = getLoader(windowFilePath);
        DistributionMainInfoController controller = loader.<DistributionMainInfoController>getController();
        controller.initializeData(distribution);
        window = new Stage();
        window.setScene(new Scene(root, 1366, 700));
        window.setTitle(title);
        window.show();
    }
}
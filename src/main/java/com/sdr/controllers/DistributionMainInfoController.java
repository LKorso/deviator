package com.sdr.controllers;

import com.jfoenix.controls.JFXButton;
import com.sdr.domain.Distribution;
import com.sdr.repository.RepositoryFactory;
import com.sdr.services.DistributionService;
import com.sdr.spring.config.WindowsConfig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

@Component
public class DistributionMainInfoController extends ControllerHelper {

    @FXML
    private ListView<Double> distributionValues;

    @FXML
    private Label sd;

    @FXML
    private Label mean;

    @FXML
    private LineChart<Integer, Double> distributionChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private AreaChart<Double, Double> probabilityChart;

    @FXML
    private NumberAxis xProbability;

    @FXML
    private NumberAxis yProbability;

    private XYChart.Series<Integer, Double> distributionSeries;
    private XYChart.Series<Double, Double> probabilitySeries;

    @Autowired
    private DistributionService service;

    @Autowired
    @Qualifier("investigationSettingsWindow")
    private WindowsConfig.Window investigetionWindow;

    private Distribution currentDistribution;

    public void saveButtonHendler() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to save");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel files", "*.xlsx")
        );
        final File saveFile = fileChooser.showSaveDialog(new Stage());
        service.saveToFile(currentDistribution, saveFile);
    }

    public void showInvestigationSettingsWindow() {
        InvestigationSettingsController controller =
                (InvestigationSettingsController) investigetionWindow.getController();
        controller.initializeData();
        if (investigetionWindow.getStage() == null) investigetionWindow.initializeStage(Modality.APPLICATION_MODAL);
        investigetionWindow.getStage().setTitle("Investigation settings");
        investigetionWindow.getStage().showAndWait();
    }

    public void initializeData() {
        currentDistribution = service.getCurrentDistribution();
        sd.setText(currentDistribution.getStandardDeviation().toString());
        mean.setText(currentDistribution.getMean().toString());
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(true);
        xProbability.setForceZeroInRange(false);
        xProbability.setAutoRanging(true);
        yProbability.setAutoRanging(true);
        distributionSeries = new XYChart.Series();
        probabilitySeries = new XYChart.Series();
        List<Double> values = currentDistribution.getValues();
        List<Double> probability = currentDistribution.getProbability();
        for (int i = 0; i < values.size(); i++) {
            distributionValues.getItems().add(values.get(i));
            distributionSeries.getData().add(new XYChart.Data<>(i, values.get(i)));
            probabilitySeries.getData().add(new XYChart.Data<>(values.get(i), probability.get(i)));
        }
        distributionChart.getData().add(distributionSeries);
        probabilityChart.getData().add(probabilitySeries);
    }

    public void onClose() {
        distributionChart.getData().clear();
        probabilityChart.getData().clear();
        distributionValues.getItems().clear();
    }
}
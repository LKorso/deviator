package com.sdr.controllers;

import com.jfoenix.controls.JFXButton;
import com.sdr.domain.Distribution;
import com.sdr.repository.RepositoryFactory;
import com.sdr.services.DistributionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

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
    private DistributionService service;
    private Distribution currentDistribution;

    public DistributionMainInfoController() {
        service = new DistributionService(RepositoryFactory.getReposytory(RepositoryFactory.IN_MEMORY));
    }

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
        final FXMLLoader loader = getLoader("/fxml/investigationSettingsWindow.fxml");
        InvestigationSettingsController controller = loader.<InvestigationSettingsController>getController();
        controller.initializeData();
        final Stage distributionWindow = new Stage();
        distributionWindow.initModality(Modality.APPLICATION_MODAL);
        distributionWindow.setScene(new Scene(root));
        distributionWindow.setTitle("Investigation settings");
        distributionWindow.showAndWait();
    }

    public void initializeData(Distribution distribution) {
        currentDistribution = distribution;
        sd.setText(distribution.getStandardDeviation().toString());
        mean.setText(distribution.getMean().toString());
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(true);
        xProbability.setForceZeroInRange(false);
        xProbability.setAutoRanging(true);
        yProbability.setAutoRanging(true);
        distributionSeries = new XYChart.Series();
        probabilitySeries = new XYChart.Series();
        List<Double> values = distribution.getValues();
        List<Double> probability = distribution.getProbability();
        for (int i = 0; i < values.size(); i++) {
            distributionValues.getItems().add(values.get(i));
            distributionSeries.getData().add(new XYChart.Data<>(i, values.get(i)));
            probabilitySeries.getData().add(new XYChart.Data<>(values.get(i), probability.get(i)));
        }
        distributionChart.getData().add(distributionSeries);
        probabilityChart.getData().add(probabilitySeries);
    }
}
package com.sdr.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.sdr.checkers.DeviationChecker;
import com.sdr.checkers.DeviationCheckers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class InvestigationSettingsController {

    @FXML
    private ChoiceBox<String> criteriaChoiceBox;

    @FXML
    private JFXCheckBox subOservationsCheck;

    @FXML
    private JFXCheckBox splitCheck;

    @FXML
    private JFXTextField startValueField;

    @FXML
    private JFXTextField subNumberField;

    @FXML
    private JFXTextField subCoefficientField;

    @FXML
    private JFXTextField endValueField;

    @FXML
    private Button investigeteButton;

    private DeviationChecker deviationChecker;

    public void processInvestigation() throws Exception {
        deviationChecker = DeviationCheckers.getClassByName(criteriaChoiceBox.getValue()).newInstance();

    }

    public void initializeData() {
        criteriaChoiceBox.getItems().addAll(DeviationCheckers.getNames());
        criteriaChoiceBox.setValue(DeviationCheckers.GRABBS_CHECKER.getName());
    }
}

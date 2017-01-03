package com.sdr.controllers;

import com.sdr.services.distributions.Distribution;
import com.sdr.services.distributions.NormalDistribution;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;


public class DistributionController {

    public TextField number;
    public TextField mean;
    public TextField sd;
    public RadioButton sorted;

    private Distribution distribution;
    private Long observationNumber;
    private Double meanValue;
    private Double sdValue;

    public void calculateNormalDistribution(){
        extractValues();

        distribution = new NormalDistribution(observationNumber, meanValue, sdValue, sorted.isSelected());

        distribution.calculateDistribution();

    }

    private void extractValues(){
        observationNumber = Long.parseLong(number.getText());
        meanValue = Double.parseDouble(mean.getText());
        sdValue = Double.parseDouble(sd.getText());
    }
}

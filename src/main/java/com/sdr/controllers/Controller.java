package com.sdr.controllers;

import com.sdr.SimpleRExample;
import javafx.scene.control.Label;


public class Controller {
    public Label label;

    private SimpleRExample rRunner = new SimpleRExample();

    public void getMEAN(){
        label.setText("MEAN: " + rRunner.calculateMEAN());
    }
}

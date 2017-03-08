package com.sdr.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class ControllerHelper {
    protected Parent root;

    protected FXMLLoader getLoader(String filePath) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }
}

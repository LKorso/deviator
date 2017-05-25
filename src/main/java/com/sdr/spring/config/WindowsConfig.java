package com.sdr.spring.config;

import com.sdr.controllers.DistributionGeneratorController;
import com.sdr.controllers.DistributionMainInfoController;
import com.sdr.controllers.InvestigationSettingsController;
import com.sdr.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class WindowsConfig {

    @Bean(name = "startedWindow")
    public Window getStartedWindow() throws IOException {
        return loadWindow("fxml/startedWindow.fxml");
    }

    @Bean(name = "distributionGeneratorWindow")
    public Window getDistributionGeneratorWindow() throws IOException {
        return loadWindow("fxml/distributionGeneratorWindow.fxml");
    }

    @Bean(name = "distributionMainInfoWindow")
    public Window getDistributionMainInfoWindow() throws IOException {
        return loadWindow("fxml/distributionMainInfoWindow.fxml");
    }

    @Bean(name = "investigationSettingsWindow")
    public Window getInvestigationSettingsWindow() throws IOException {
        return loadWindow("fxml/investigationSettingsWindow.fxml");
    }

    @Bean
    public DistributionGeneratorController getDistributionGeneratorController() throws IOException {
        return (DistributionGeneratorController) getDistributionGeneratorWindow().getController();
    }

    @Bean
    public DistributionMainInfoController getDistributionMainInfoController() throws IOException {
        return (DistributionMainInfoController) getDistributionMainInfoWindow().getController();
    }

    @Bean
    public InvestigationSettingsController getInvestigationSettingController() throws IOException {
        return (InvestigationSettingsController) getInvestigationSettingsWindow().getController();
    }

    @Bean
    public MainController getMainController() throws IOException {
        return (MainController) getStartedWindow().getController();
    }

    @Autowired
    private Image mainIcon;

    protected Window loadWindow(String url) throws IOException {
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getClassLoader().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            Parent view = loader.getRoot();
            Object controller = loader.getController();
            return new Window(view, controller);
        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }

    @Data
    public class Window {
        private Parent view;
        private Object controller;
        private Stage stage;
        private String title;

        public Window(Parent view, Object controller) {
            this.view = view;
            this.controller = controller;
        }

        public Window(Parent view, Object controller, String title) {
            this(view, controller);
            this.title = title;
        }

        public void initializeStage(Modality modality) {
            stage = new Stage();
            stage.setScene(new Scene(view));
            stage.getIcons().add(mainIcon);
            stage.initModality(modality);
        }
    }
}

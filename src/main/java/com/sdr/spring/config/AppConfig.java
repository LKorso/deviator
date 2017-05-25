package com.sdr.spring.config;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public Image mainIcon() {
        return new Image("main_icon.png");
    }

}

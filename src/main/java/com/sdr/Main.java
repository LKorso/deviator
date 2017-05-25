package com.sdr;

import com.sdr.spring.config.WindowsConfig;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;

@Lazy
@SpringBootApplication
public class Main extends Application {

    private ConfigurableApplicationContext context;

    @Autowired
    private Image mainIcon;

    @Autowired
    @Qualifier("startedWindow")
    private WindowsConfig.Window window;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(getClass());
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Deviator");
        primaryStage.getIcons().add(mainIcon);
        primaryStage.setScene(new Scene(window.getView(), 750, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception{
        context.close();
    }
}

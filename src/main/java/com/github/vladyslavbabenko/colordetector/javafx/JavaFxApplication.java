package com.github.vladyslavbabenko.colordetector.javafx;

import com.github.vladyslavbabenko.colordetector.ColorDetectorApplication;
import com.github.vladyslavbabenko.colordetector.javafx.controller.FXController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder().sources(ColorDetectorApplication.class).run(args);
    }

    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(FXController.class);
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Color Detector");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
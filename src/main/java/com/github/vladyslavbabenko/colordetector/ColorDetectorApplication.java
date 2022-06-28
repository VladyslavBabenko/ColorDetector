package com.github.vladyslavbabenko.colordetector;

import com.github.vladyslavbabenko.colordetector.JavaFX.JavaFxApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ColorDetectorApplication {
    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }
}
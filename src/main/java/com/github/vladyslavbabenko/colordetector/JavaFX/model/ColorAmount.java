package com.github.vladyslavbabenko.colordetector.JavaFX.model;

import javafx.scene.paint.Color;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ColorAmount {
    private Color color;
    private int amount;
}
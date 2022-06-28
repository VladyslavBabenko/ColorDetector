package com.github.vladyslavbabenko.colordetector.javafx.controller;

import com.github.vladyslavbabenko.colordetector.service.ColorServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link FXController}.
 */

@Component
@FxmlView("GUI.fxml")
public class FXControllerImpl implements FXController {

    private final ColorServiceImpl colorService;

    @FXML
    private ImageView imageView;

    @Autowired
    public FXControllerImpl(ColorServiceImpl colorService) {
        this.colorService = colorService;
    }

    @Override
    public void chooseImage() {
        imageView.setImage(colorService.chooseImage("Select an image"));
    }
}
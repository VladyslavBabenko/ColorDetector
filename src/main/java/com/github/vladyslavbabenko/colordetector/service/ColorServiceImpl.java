package com.github.vladyslavbabenko.colordetector.service;

import com.github.vladyslavbabenko.colordetector.enums.ImageExtension;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ColorService}.
 */

@Service
public class ColorServiceImpl implements ColorService {
    private Image image;

    @Override
    public Image chooseImage(String windowTitle) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(windowTitle);
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Extensions",
                Arrays.stream(ImageExtension.values())
                        .map(ImageExtension::getExtension)
                        .collect(Collectors.toList()));
        chooser.getExtensionFilters().add(imageFilter);
        String path = chooser.showOpenDialog(new Stage()).getAbsolutePath();
        System.out.println(path);
        image = new Image(path);
        return image;
    }
}
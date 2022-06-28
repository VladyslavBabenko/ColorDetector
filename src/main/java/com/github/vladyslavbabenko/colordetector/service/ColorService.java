package com.github.vladyslavbabenko.colordetector.service;

import javafx.scene.image.Image;

public interface ColorService {
    /**
     * Choose existing image on a user's computer
     *
     * @return the selected image.
     */
    Image chooseImage(String windowTitle);
}
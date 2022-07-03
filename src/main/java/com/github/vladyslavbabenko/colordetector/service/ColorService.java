package com.github.vladyslavbabenko.colordetector.service;

import com.github.vladyslavbabenko.colordetector.enums.ImageExtension;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ColorService {
    private Image image;
    private int width, height;
    private List<Map.Entry<Color, Integer>> entryArrayList;

    /**
     * Creates a new window where user can select an image
     *
     * @return the selected image.
     */
    public Image chooseImage(String windowTitle) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(windowTitle);
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Extensions",
                Arrays.stream(ImageExtension.values())
                        .map(ImageExtension::getExtension)
                        .collect(Collectors.toList()));
        chooser.getExtensionFilters().add(imageFilter);
        String path = chooser.showOpenDialog(new Stage()).getAbsolutePath();
        image = new Image(path);
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        return image;
    }

    /**
     * Iterates through all the pixels in an image, writing each color it encounters to the given array.
     *
     * @param imagePixels the array that should be filled with pixel colors
     * @return array filled with pixel colors
     */
    private Color[] detectColor(Color[] imagePixels) {
        int index = 0;
        PixelReader pixelReader = image.getPixelReader();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                imagePixels[index++] = pixelReader.getColor(x, y);
            }
        }

        return imagePixels;
    }

    /**
     * Groups and counts the number of occurring colors
     *
     * @param imagePixelColors array filled with pixel colors
     * @return map with counted unique colors
     */
    private Map<Color, Integer> countColorAmount(Color[] imagePixelColors) {
        Map<Color, Integer> countPixelColors = new HashMap<>();
        countPixelColors.put(imagePixelColors[0], 0);
        for (Color value : imagePixelColors) {
            if (countPixelColors.containsKey(value)) {
                countPixelColors.replace(value, countPixelColors.get(value) + 1);
            } else {
                countPixelColors.put(value, 1);
            }
        }
        return countPixelColors;
    }

    /**
     * Sorts map by value in natural order
     *
     * @param imagePixelsColorsMap map with counted unique colors
     * @return natural ordered list
     */
    private List<Map.Entry<Color, Integer>> sortByColorAmount(Map<Color, Integer> imagePixelsColorsMap) {
        entryArrayList = new ArrayList<>(imagePixelsColorsMap.entrySet());
        entryArrayList.sort(Map.Entry.comparingByValue());
        return entryArrayList;
    }

    /**
     * Default getter, if entryArrayList already exists, returns, otherwise creates and returns
     *
     * @return SortedEntryArrayList
     */
    public List<Map.Entry<Color, Integer>> getSortedEntryArrayList() {
        if (entryArrayList != null && !entryArrayList.isEmpty()) {
            return entryArrayList;
        }
        return sortByColorAmount(countColorAmount(detectColor(new Color[width * height])));
    }

    /**
     * Resets all values bringing the program to its starting point
     */
    public void resetAll() {
        image = null;
        width = height = 0;
        entryArrayList.clear();
        entryArrayList = null;
    }

    /**
     * Default getter
     *
     * @return TotalPixels of an image
     */
    public int getTotalPixels() {
        return width * height;
    }

    /**
     * Converts input value to percentage in relative to all pixels of the image
     *
     * @param input value that should be converted
     * @return percentage of each color relative to all pixels of the image
     */
    private double convertToPercentage(int input) {
        return input * 100.0 / (width * height);
    }

    /**
     * Gets the percentage from entryArrayList
     *
     * @param offset defines the offset value from the end
     * @return percentage of each color relative to all pixels of the image from entryArrayList
     */
    public double getPercentage(int offset) {
        return convertToPercentage(entryArrayList.get(entryArrayList.size() - 1 - offset).getValue());
    }

    /**
     * Gets the color from entryArrayList
     *
     * @param offset defines the offset value from the end
     * @return each color from entryArrayList
     */
    public Color getColorValue(int offset) {
        return entryArrayList.get(entryArrayList.size() - 1 - offset).getKey();
    }
}
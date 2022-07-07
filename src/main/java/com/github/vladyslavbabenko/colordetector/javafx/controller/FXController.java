package com.github.vladyslavbabenko.colordetector.javafx.controller;

import com.github.vladyslavbabenko.colordetector.service.ColorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("GUI.fxml")
public class FXController implements Initializable {

    private final ColorService colorService;
    private final GridPane gridPane = new GridPane();
    private int colorPickerAmount;

    @FXML
    private ImageView imageView;
    @FXML
    private Slider colorPickerAmountSlider;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label totalPixels;

    @Autowired
    public FXController(ColorService colorService) {
        this.colorService = colorService;
    }

    /**
     * Sets selected image to the imageViewer
     */
    private void chooseImage() {
        imageView.setImage(colorService.chooseImage("Select an image"));
    }

    /**
     * Runs all the necessary methods to start the application
     */
    public void start() {
        if (imageView.getImage() != null) {
            resetAll();
        }
        chooseImage();
        calcColorPickerAmount(colorService.getSortedEntryArrayList().size());
        setUpGridPane();
        totalPixels.setText("Total pixels: " + colorService.getTotalPixels());
        createColorPicker(colorPickerAmount);
        colorPickerAmountSlider.setValue(colorPickerAmount);
    }

    /**
     * Sets parameters of the gridPane
     */
    private void setUpGridPane() {
        ColumnConstraints firstColumn = new ColumnConstraints(35);
        firstColumn.setHalignment(HPos.CENTER);

        ColumnConstraints secondColumn = new ColumnConstraints();
        secondColumn.setHgrow(Priority.NEVER);
        secondColumn.setMinWidth(40.0);
        secondColumn.setMaxWidth(100.0);
        secondColumn.setHalignment(HPos.CENTER);

        ColumnConstraints thirdColumn = new ColumnConstraints(65);
        firstColumn.setHalignment(HPos.CENTER);

        gridPane.getColumnConstraints().addAll(firstColumn, secondColumn, thirdColumn);
        gridPane.getRowConstraints().add(new RowConstraints(30));

        scrollPane.setContent(gridPane);
    }

    /**
     * Creates a new row for each detected color (up to 1000 pcs.) according to template (№ Color %)
     *
     * @param colorPickerAmount number of new rows
     */
    private void createColorPicker(int colorPickerAmount) {
        for (int i = 0; i < colorPickerAmount; i++) {
            Label numeration = new Label(String.valueOf(i + 1));
            numeration.setFont(new Font("Calibri", 14));

            ColorPicker colorPicker = new ColorPicker();
            colorPicker.setValue(colorService.getColorValue(i));

            Label percentage = new Label(String.format(" %.4f", colorService.getPercentage(i)));
            percentage.setFont(new Font("Calibri", 14));

            gridPane.add(numeration, 0, i);
            gridPane.add(colorPicker, 1, i);
            gridPane.add(percentage, 2, i);
        }
    }

    /**
     * Clears and recalculates contents of the gridPane
     */
    public void resetColorPickers() {
        if (imageView.getImage() != null) {
            gridPane.getChildren().clear();
            createColorPicker(colorPickerAmount);
        }
    }

    /**
     * Resets all values bringing the program to its starting point
     */
    public void resetAll() {
        colorPickerAmount = 0;
        resetColorPickers();
        imageView.setImage(null);
        totalPixels.setText("Total pixels: 0");
        colorService.resetAll();
        colorPickerAmountSlider.setValue(colorPickerAmountSlider.getMin());
    }

    /**
     * Recalculates colorPickerAmount according to slider position/start of a program
     *
     * @param value the value what should be assigned to colorPickerAmount
     */
    private void calcColorPickerAmount(int value) {
        if (value < colorPickerAmount) {
            colorPickerAmount = value;
        } else {
            int listSize = 0;
            if (imageView.getImage() != null) {
                listSize = colorService.getSortedEntryArrayList().size();
            }
            colorPickerAmount = Math.min(Math.min(listSize, value), 1000);
        }
        resetColorPickers();
    }

    /**
     * Adds an event listener to the slider
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colorPickerAmountSlider.valueProperty()
                .addListener((observableValue, number, t1) ->
                        calcColorPickerAmount((int) colorPickerAmountSlider.getValue()));
    }
}
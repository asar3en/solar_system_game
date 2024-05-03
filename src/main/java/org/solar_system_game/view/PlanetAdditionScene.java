package org.solar_system_game.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.solar_system_game.Main;

import javax.swing.*;

public class PlanetAdditionScene implements ViewScene{
    Scene javaFxScene;
    ViewManager manager;
    public Scene GetJavafxScene() {
        return javaFxScene;
    }

    public PlanetAdditionScene(ViewManager manager) {
        this.manager = manager;
        Group root = new Group();
        //setUpButtons(root);
        setUpElements(root);
        javaFxScene = new Scene(root, manager.mainStage.getWidth(), manager.mainStage.getHeight());
    }

    private void setUpElements(Group root){
        GridPane planetParametersPane = new GridPane(0, 1);
        Label planetNameLabel = new Label ("Nazwa planety:");
        TextField planetNameField = new TextField();
        Label planetMassLabel = new Label ("Masa [e24 kg]:");
        TextField planetMassField = new TextField();
        Label planetIniXLabel = new Label ("Początkowe x");
        TextField planetIniXField = new TextField();
        Label planetIniYLabel = new Label ("Początkowe y");
        TextField planetIniYField = new TextField();
        Label planetIniv_XLabel = new Label ("Początkowa prędkość x [km/s]");
        TextField planetIniv_XField = new TextField();
        Label planetIniv_YLabel = new Label ("Początkowe prędkość y [km/s]");
        TextField planetIniv_YField = new TextField();
        Button planetColourButton = new Button("Kolor planety");
        Button planetFinishSetup = new Button("OK");

        planetColourButton.setOnAction(new EventHandler<ActionEvent>() {
                                           @Override
                                           public void handle(ActionEvent actionEvent) {
                                               ColorPicker bgColorPicker = new ColorPicker();
                                               Color a = bgColorPicker.getValue();
                                               //później będzie kod używający koloru, który używa konkretna planeta
                                           }
                                       });

        planetFinishSetup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                manager.SwitchScene("MissionCreator", null);
            }
        });

        planetParametersPane.getChildren().addAll(planetNameLabel, planetNameField, planetMassLabel, planetMassField, planetIniXLabel, planetIniXField, planetIniYLabel, planetIniYField, planetIniv_XLabel, planetIniv_XField, planetIniv_YLabel, planetIniv_YField, planetColourButton, planetFinishSetup);
        root.getChildren().add(planetParametersPane);
    }

}

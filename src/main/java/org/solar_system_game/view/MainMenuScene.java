package org.solar_system_game.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MainMenuScene implements ViewScene {

    public Scene GetJavafxScene() {
        Group root = new Group();
        Button button = new Button("click me pls");
        root.getChildren().add(button);
        return new Scene(root);
    }
}

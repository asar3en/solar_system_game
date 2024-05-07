package org.solar_system_game.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class LoadGameScene implements ViewScene{
    Scene javaFxScene;
    ViewManager manager;
    @Override
    public Scene GetJavafxScene() {
        return javaFxScene;
    }

    public LoadGameScene(ViewManager manager) {
        this.manager = manager;
        Group root = new Group();
        setUpButtons(root);

        javaFxScene = new Scene(root, manager.mainStage.getWidth(), manager.mainStage.getHeight(), Color.BLACK);
        setKeyShortcuts();
    }

    private void setUpButtons(Group root) {
        var buttonsFlowPane = new FlowPane();
        buttonsFlowPane.layoutYProperty().bind(manager.mainStage.heightProperty().divide(1.5));
        buttonsFlowPane.layoutXProperty().bind(manager.mainStage.widthProperty().subtract(buttonsFlowPane.widthProperty()).divide(3));

        Button loadButton = new Button("WCZYTAJ");
        Button deleteButton = new Button("USUŃ ZAPIS");
        Button backButton = new Button("POWRÓT DO MENU");

        backButton.setOnAction(event -> manager.SwitchScene("MainMenu", null));

        buttonsFlowPane.getChildren().addAll(loadButton,deleteButton,backButton);
        root.getChildren().add(buttonsFlowPane);
    }

    private void setKeyShortcuts() {
        javaFxScene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case KeyCode.F12:
                    manager.mainStage.setFullScreen(false);
                    break;
                case KeyCode.F11:
                    manager.mainStage.setFullScreen(true);
                    break;
                case KeyCode.BACK_SPACE:
                    manager.SwitchScene("MainMenu", null);
                default: break;
            }
        });
    }

}

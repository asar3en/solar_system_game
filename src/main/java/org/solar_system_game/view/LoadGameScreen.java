package org.solar_system_game.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.solar_system_game.Main;

public class LoadGameScreen implements ViewScene{
    Scene javaFxScene;
    @Override
    public Scene GetJavafxScene() {
        return javaFxScene;
    }

    public LoadGameScreen() {
        Group root = new Group();
        setUpButtons(root);

        javaFxScene = new Scene(root, Main.manager.mainStage.getWidth(), Main.manager.mainStage.getHeight());
        setKeyShortcuts();
    }

    private void setUpButtons(Group root) {
        Button loadButton = new Button("WCZYTAJ");
        Button deleteButton = new Button("USUŃ ZAPIS");
        Button backButton = new Button("POWRÓT DO MENU");

        loadButton.layoutYProperty().bind(Main.manager.mainStage.heightProperty().divide(1.5));
        loadButton.layoutXProperty().bind(Main.manager.mainStage.widthProperty().subtract(loadButton.widthProperty()).divide(3));

        deleteButton.layoutXProperty().bind(loadButton.layoutXProperty().add(loadButton.widthProperty().add(10)));
        deleteButton.layoutYProperty().bind(loadButton.layoutYProperty());

        backButton.layoutXProperty().bind(deleteButton.layoutXProperty().add(deleteButton.widthProperty().add(10)));
        backButton.layoutYProperty().bind(loadButton.layoutYProperty());

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.manager.SwitchScene("MainMenu", null);
            }
        });

        root.getChildren().addAll(loadButton,deleteButton,backButton);
    }

    private void setKeyShortcuts() {
        javaFxScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case KeyCode.F12:
                        Main.manager.mainStage.setFullScreen(false);
                        break;
                    case KeyCode.F11:
                        Main.manager.mainStage.setFullScreen(true);
                        break;
                    case KeyCode.BACK_SPACE:
                        Main.manager.SwitchScene("MainMenu", null);
                    default: break;
                }
            }
        });
    }

}

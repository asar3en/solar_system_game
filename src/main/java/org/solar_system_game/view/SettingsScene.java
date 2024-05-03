package org.solar_system_game.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.solar_system_game.Main;
import javafx.scene.control.CheckBox;

public class SettingsScene implements ViewScene{
    Scene javaFxScene;
    ViewManager manager;
    @Override
    public Scene GetJavafxScene() {
        return javaFxScene;
    }

    public SettingsScene(ViewManager manager) {
        this.manager = manager;
        Group root = new Group();
        setUpButtons(root);

        javaFxScene = new Scene(root, manager.mainStage.getWidth(), manager.mainStage.getHeight(), Color.BLACK);
        setKeyShortcuts();
    }

    private void setUpButtons(Group root) {
        VBox cbHolder = new VBox(10);
        CheckBox fullscreenCB = new CheckBox("Włącz tryb pełnoekranowy");
        CheckBox autoSave = new CheckBox("Włącz automatyczne zapisywanie");
        cbHolder.layoutXProperty().bind(manager.mainStage.widthProperty().subtract(cbHolder.widthProperty()).divide(2));
        cbHolder.layoutYProperty().bind(manager.mainStage.heightProperty().subtract(cbHolder.heightProperty()).divide(3));
        cbHolder.prefWidthProperty().bind(manager.mainStage.widthProperty().divide(6));

        cbHolder.getChildren().addAll(fullscreenCB, autoSave);

        javafx.scene.control.Button backButton = new Button("POWRÓT DO MENU");
        backButton.layoutXProperty().bind(cbHolder.layoutXProperty());
        backButton.layoutYProperty().bind(cbHolder.layoutYProperty().add(cbHolder.heightProperty()).add(10));

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                manager.SwitchScene("MainMenu", null);
            }
        });

        root.getChildren().addAll(cbHolder, backButton);
    }

    private void setKeyShortcuts() {
        javaFxScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case KeyCode.F12:
                        manager.mainStage.setFullScreen(false);
                        break;
                    case KeyCode.F11:
                        manager.mainStage.setFullScreen(true);
                        break;
                    default: break;
                }
            }
        });
    }
}

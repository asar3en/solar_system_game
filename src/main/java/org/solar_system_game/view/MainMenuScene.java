package org.solar_system_game.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.solar_system_game.Main;

public class MainMenuScene implements ViewScene {

    Scene javaFxScene;
    ViewManager manager;

    public MainMenuScene(ViewManager manager) {
        this.manager = manager;
        Group root = new Group();
        setBackground(root);
        setUpTitle(root);
        setUpButtons(root);

        javaFxScene = new Scene(root, manager.mainStage.getWidth(), manager.mainStage.getHeight(), Color.BLACK);
        setKeyShortcuts();
    }

    private void setBackground(Group root) {
        Rectangle bg = new Rectangle();
        bg.widthProperty().bind(manager.mainStage.widthProperty());
        bg.heightProperty().bind(manager.mainStage.heightProperty());
        bg.setFill(new ImagePattern(new Image("copyrightfreestars.png")));
        root.getChildren().add(bg);
    }

    private void setUpTitle(Group root) {
        Label title = new Label("Solar System Game!");
        title.setFont(Font.font("Arial", 50));
        title.setTextFill(Color.WHITE);
        title.layoutXProperty().bind(manager.mainStage.widthProperty().subtract(title.widthProperty()).divide(2));
        title.layoutYProperty().bind(manager.mainStage.heightProperty().subtract(title.heightProperty()).divide(3));

        root.getChildren().add(title);
    }

    private void setUpButtons(Group root) {
        Button newGameButton = new Button("Nowa gra");
        newGameButton.layoutXProperty().bind(manager.mainStage.widthProperty().subtract(newGameButton.widthProperty()).divide(2));
        newGameButton.layoutYProperty().bind(manager.mainStage.heightProperty().subtract(newGameButton.heightProperty()).divide(2));
        newGameButton.prefWidthProperty().bind(manager.mainStage.widthProperty().divide(6));

        Button loadGameButton = new Button("Wczytaj gre");
        loadGameButton.layoutXProperty().bind(newGameButton.layoutXProperty());
        loadGameButton.layoutYProperty().bind(newGameButton.layoutYProperty().add(newGameButton.heightProperty().add(5)));
        loadGameButton.prefWidthProperty().bind(manager.mainStage.widthProperty().divide(6));

        loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!manager.SwitchScene("LoadGame", null)) {
                    LoadGameScene loadGameScene = new LoadGameScene(manager);
                    manager.SwitchScene("LoadGame", loadGameScene.GetJavafxScene());
                }
            }
        });

        Button settingsButton = new Button("Opcje");
        settingsButton.layoutXProperty().bind(newGameButton.layoutXProperty());
        settingsButton.layoutYProperty().bind(loadGameButton.layoutYProperty().add(loadGameButton.heightProperty().add(5)));
        settingsButton.prefWidthProperty().bind(manager.mainStage.widthProperty().divide(6));

        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!manager.SwitchScene("Settings", null)) {
                    SettingsScene optionsScreen = new SettingsScene(manager);
                    manager.SwitchScene("Settings", optionsScreen.GetJavafxScene());
                }
            }
        });

        Button exitGameButton = new Button("Wyjście z gry");
        exitGameButton.layoutXProperty().bind(newGameButton.layoutXProperty());
        exitGameButton.layoutYProperty().bind(settingsButton.layoutYProperty().add(loadGameButton.heightProperty().add(5)));
        exitGameButton.prefWidthProperty().bind(manager.mainStage.widthProperty().divide(6));

        exitGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });


        root.getChildren().addAll(newGameButton, loadGameButton, settingsButton, exitGameButton);
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
                    case KeyCode.F10:
                        var mgS = new MainGameScene(manager);
                        manager.SwitchScene("MGS", mgS.GetJavafxScene());
                        break;
                    default: break;
                }
            }
        });
    }

    public Scene GetJavafxScene() {
        return javaFxScene;
    }
}

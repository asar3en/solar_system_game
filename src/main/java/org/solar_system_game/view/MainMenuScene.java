package org.solar_system_game.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
        VBox mainMenu = new VBox();
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.prefWidthProperty().bind(manager.mainStage.widthProperty().divide(6));
        mainMenu.layoutXProperty().bind(manager.mainStage.widthProperty().subtract(mainMenu.widthProperty()).divide(2));
        mainMenu.layoutYProperty().bind(manager.mainStage.heightProperty().subtract(mainMenu.heightProperty()).divide(2));

        Button newGameButton = new Button(manager.menuElements.getString("newGame"));
        Button settingsButton = new Button(manager.menuElements.getString("settings"));
        Button exitGameButton = new Button(manager.menuElements.getString("exit"));

        newGameButton.setOnAction(e-> {
            if(!manager.SwitchScene("MGS", null)) {
                var mgS = new MainGameScene(manager);
                manager.SwitchScene("MGS", mgS);
            }
        });

        settingsButton.setOnAction(event -> {
            if(!manager.SwitchScene("Settings", null)) {
                SettingsScene optionsScreen = new SettingsScene(manager);
                manager.SwitchScene("Settings", optionsScreen);
            }
        });
        exitGameButton.setOnAction(event -> {Platform.exit();});

        //will just make buttons as wide as vbox
        newGameButton.setMaxWidth(Double.MAX_VALUE);
        settingsButton.setMaxWidth(Double.MAX_VALUE);
        exitGameButton.setMaxWidth(Double.MAX_VALUE);

        mainMenu.getChildren().addAll(newGameButton, settingsButton, exitGameButton);
        root.getChildren().add(mainMenu);
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

    public Scene GetJavafxScene() {
        return javaFxScene;
    }

    @Override
    public void UpdateToCurrLocale() {
        Group root = new Group();
        setBackground(root);
        setUpTitle(root);
        setUpButtons(root);
        javaFxScene.setRoot(root);
    }
}

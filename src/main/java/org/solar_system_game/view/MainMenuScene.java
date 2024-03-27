package org.solar_system_game.view;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.solar_system_game.Main;

public class MainMenuScene implements ViewScene {

    Scene javaFxScene;

    public MainMenuScene() {
        Group root = new Group();
        setUpTitle(root);
        setUpButtons(root);

        javaFxScene = new Scene(root, Main.manager.mainStage.getWidth(), Main.manager.mainStage.getHeight());

        setKeyShortcuts();
    }

    private void setUpTitle(Group root) {
        Label title = new Label("Solar System Game!");
        title.setFont(Font.font("Arial", 50));
        title.layoutXProperty().bind(Main.manager.mainStage.widthProperty().subtract(title.widthProperty()).divide(2));
        title.layoutYProperty().bind(Main.manager.mainStage.heightProperty().subtract(title.heightProperty()).divide(3));

        root.getChildren().add(title);
    }

    private void setUpButtons(Group root) {
        Button newGameButton = new Button("Nowa gra");
        newGameButton.layoutXProperty().bind(Main.manager.mainStage.widthProperty().subtract(newGameButton.widthProperty()).divide(2));
        newGameButton.layoutYProperty().bind(Main.manager.mainStage.heightProperty().subtract(newGameButton.heightProperty()).divide(2));
        newGameButton.prefWidthProperty().bind(Main.manager.mainStage.widthProperty().divide(6));

        Button loadGameButton = new Button("Wczytaj gre");
        loadGameButton.layoutXProperty().bind(newGameButton.layoutXProperty());
        loadGameButton.layoutYProperty().bind(newGameButton.layoutYProperty().add(newGameButton.heightProperty().add(5)));
        loadGameButton.prefWidthProperty().bind(Main.manager.mainStage.widthProperty().divide(6));

        loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LoadGameScreen loadGameScreen = new LoadGameScreen();
                Main.manager.SwitchScene("LoadGame", loadGameScreen.GetJavafxScene());
            }
        });

        Button optionsButton = new Button("Opcje");
        optionsButton.layoutXProperty().bind(newGameButton.layoutXProperty());
        optionsButton.layoutYProperty().bind(loadGameButton.layoutYProperty().add(loadGameButton.heightProperty().add(5)));
        optionsButton.prefWidthProperty().bind(Main.manager.mainStage.widthProperty().divide(6));

        Button exitGameButton = new Button("Wyjście z gry");
        exitGameButton.layoutXProperty().bind(newGameButton.layoutXProperty());
        exitGameButton.layoutYProperty().bind(optionsButton.layoutYProperty().add(loadGameButton.heightProperty().add(5)));
        exitGameButton.prefWidthProperty().bind(Main.manager.mainStage.widthProperty().divide(6));

        exitGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });


        root.getChildren().addAll(newGameButton, loadGameButton, optionsButton, exitGameButton);
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
                    case KeyCode.F10:
                        var mgS = new MainGameScreen();
                        Main.manager.SwitchScene("MGS", mgS.GetJavafxScene());
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

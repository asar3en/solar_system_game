package org.solar_system_game.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import org.solar_system_game.Main;

public class MainGameScreen implements ViewScene{
    Scene javaFxScene;
    @Override
    public Scene GetJavafxScene() {
        return javaFxScene;
    }

    public MainGameScreen() {
        Group root = new Group();
        setUpButtons(root);
        setUpGameInfo(root);

        javaFxScene = new Scene(root, Main.manager.mainStage.getWidth(), Main.manager.mainStage.getHeight());
        setKeyShortcuts();
    }

    private void setUpButtons(Group root) {
        MenuBar bar = new MenuBar();
        Menu mainMenu = new Menu("Menu");
        MenuItem returnToMainMenu = new MenuItem("Powrót do Menu Głównego");
        MenuItem newGame = new MenuItem("Nowa gra");
        MenuItem loadGame = new MenuItem("Wczytaj gre");
        MenuItem saveGame = new MenuItem("Zapisz gre");
        MenuItem settings = new MenuItem("Opcje");
        MenuItem exitGame = new MenuItem("Wyjście z gry");

        returnToMainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.manager.SwitchScene("MainMenu", null);
            }
        });

        loadGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!Main.manager.SwitchScene("LoadGame", null)) {
                    LoadGameScreen loadGameScreen = new LoadGameScreen();
                    Main.manager.SwitchScene("LoadGame", loadGameScreen.GetJavafxScene());
                }
            }
        });

        exitGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });

        mainMenu.getItems().addAll(returnToMainMenu,newGame,loadGame,saveGame,settings,exitGame);
        bar.getMenus().add(mainMenu);

        bar.setTranslateX(0);
        bar.setTranslateY(0);

        Button startPause = new Button("S/P");
        startPause.layoutXProperty().bind(Main.manager.mainStage.widthProperty().subtract(startPause.widthProperty()).subtract(50));
        startPause.layoutYProperty().bind(Main.manager.mainStage.heightProperty().subtract(startPause.heightProperty()).subtract(50));

        root.getChildren().addAll(bar, startPause);
    }

    private void setUpGameInfo(Group root) {
        Label target = new Label("Cel: Tu Jest Cel");
        target.setFont(Font.font("Arial", 20));
        target.prefWidthProperty().bind(Main.manager.mainStage.widthProperty().divide(6));
        target.layoutXProperty().bind(Main.manager.mainStage.widthProperty().subtract(target.widthProperty().add(15)));


        Label time = new Label("Czas: \n Tu się wyświetla czas");
        time.prefWidthProperty().bind(target.prefWidthProperty());
        time.setFont(Font.font("Arial", 20));
        time.layoutXProperty().bind(target.layoutXProperty());
        time.layoutYProperty().bind(target.layoutYProperty().add(target.heightProperty().add(10)));

        Label fuel = new Label("Stan Paliwa: coś / max");
        fuel.prefWidthProperty().bind(target.prefWidthProperty());
        fuel.setFont(Font.font("Arial", 20));
        fuel.layoutXProperty().bind(target.layoutXProperty());
        fuel.layoutYProperty().bind(time.layoutYProperty().add(time.heightProperty().add(10)));

        root.getChildren().addAll(target,time,fuel);
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
                    case KeyCode.ESCAPE:
                        // pause the game
                    default: break;
                }
            }
        });
    }
}

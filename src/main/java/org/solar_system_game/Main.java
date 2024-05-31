package org.solar_system_game;

import javafx.application.Application;
import javafx.stage.Stage;
import org.solar_system_game.view.MainMenuScene;
import org.solar_system_game.view.ViewManager;

public class Main extends  Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        var manager = new ViewManager(mainStage);
        var mainMenu = new MainMenuScene(manager);
        manager.SwitchScene("MainMenu", mainMenu);
    }
}
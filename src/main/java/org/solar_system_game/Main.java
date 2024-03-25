package org.solar_system_game;

import javafx.application.Application;
import javafx.stage.Stage;
import org.solar_system_game.view.ViewManager;

public class Main extends  Application {
    static public ViewManager manager;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        manager = new ViewManager(mainStage);
    }
}
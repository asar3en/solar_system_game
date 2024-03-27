package org.solar_system_game.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.solar_system_game.Main;

public class MissionCreatorScene implements ViewScene{

    Scene javaFxScene;
    public Scene GetJavafxScene() {
        return javaFxScene;
    }

    public MissionCreatorScene() {
        Group root = new Group();
        setUpButtons(root);

        javaFxScene = new Scene(root, Main.manager.mainStage.getWidth(), Main.manager.mainStage.getHeight());
    }

    private void setUpButtons(Group root) {
        MenuBar creatorBar = new MenuBar();
        Menu creatorMenu = new Menu("Zapisz/Zakończ");
        Menu bgMenu = new Menu("Tło");
        Menu planetMenu = new Menu("Planety");

        MenuItem exitNoSave = new MenuItem("Wyjdź bez zapisywania");
        MenuItem exitSave = new MenuItem("Zapisz stan gry");

        MenuItem addPlanet = new MenuItem("Dodaj nową planetę");

        MenuItem changeBg = new MenuItem("Zmień kolor tła");

        exitNoSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.manager.SwitchScene("MainMenu", null);
                //później będzie kod resetujący wprowadzone dane w kreatorze
            }
        });

        exitSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.manager.SwitchScene("MainGame", null);
                //później będzie kod zapisujący wprowadzone parametry
                }

        });

        addPlanet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.manager.SwitchScene("MainGame", null);
            }
        });

        changeBg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ColorPicker bgColorPicker = new ColorPicker();
                Color a = bgColorPicker.getValue();
                //później będzie kod używający koloru, który używa tło sceny gry oraz sceny kreatora
            }
        });


        creatorMenu.getItems().addAll(exitNoSave, exitSave);
        planetMenu.getItems().add(addPlanet);
        bgMenu.getItems().add(changeBg);

        creatorBar.getMenus().addAll(creatorMenu, planetMenu, bgMenu);

        creatorBar.setTranslateX(0);
        creatorBar.setTranslateY(0);

    }

}

package org.solar_system_game.view;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.solar_system_game.view.graphics.CircleRend;
import org.solar_system_game.view.graphics.RenderObject;
import org.solar_system_game.view.graphics.Renderer;

public class MainGameScene implements ViewScene{
    Scene javaFxScene;
    ViewManager manager;
    AnimationTimer timer;
    Boolean isPaused = false;
    Renderer MainRenderer;
    private double lastMouseX;
    private double lastMouseY;

    @Override
    public Scene GetJavafxScene() {
        return javaFxScene;
    }

    public MainGameScene(ViewManager manager) {
        this.manager = manager;
        Group root = new Group();
        setUpGameRendering(root);

        setUpButtons(root);
        setUpGameInfo(root);

        javaFxScene = new Scene(root, manager.mainStage.getWidth(), manager.mainStage.getHeight(), Color.BLACK);
        setKeyShortcuts();
    }

    private void setUpGameRendering(Group root) {
        Pane renderPane = new Pane();
        final double[] count = {0};
        root.getChildren().add(renderPane);

        final long[] lastUpdateTime = {0};
        final double TARGET_UPDATE_INT = 1.0 / 60;

        MainRenderer = new Renderer();
        MainRenderer.AddToRenderList(new CircleRend(700, 200, 14, Color.RED));
        MainRenderer.AddToRenderList(new CircleRend(1000, 600, 14, Color.GREEN));
        MainRenderer.AddToRenderList(new CircleRend(300, 600, 14, Color.YELLOW));
        MainRenderer.AddToRenderList(new CircleRend(0, 800, 14, Color.BLUE));
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                double elapsedTime = (l - lastUpdateTime[0]) / 1_000_000_000.0;
                if (elapsedTime >= TARGET_UPDATE_INT) {
                    renderPane.getChildren().clear();

                    MainRenderer.Redraw(renderPane);

                    lastUpdateTime[0] = l;
                }
            }
        };
        timer.start();
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

        returnToMainMenu.setOnAction(actionEvent -> {
            timer.stop();
            isPaused = true;
            manager.SwitchScene("MainMenu", null);
        });

        settings.setOnAction(actionEvent -> {
            if(!manager.SwitchScene("Settings", null)) {
                SettingsScene settingsScene = new SettingsScene(manager);
                manager.SwitchScene("Settings", settingsScene.GetJavafxScene());
            }
        });

        loadGame.setOnAction(actionEvent -> {
            if(!manager.SwitchScene("LoadGame", null)) {
                LoadGameScene loadGameScene = new LoadGameScene(manager);
                manager.SwitchScene("LoadGame", loadGameScene.GetJavafxScene());
            }
        });

        exitGame.setOnAction(actionEvent -> Platform.exit());

        mainMenu.getItems().addAll(returnToMainMenu,newGame,loadGame,saveGame,settings,exitGame);
        bar.getMenus().add(mainMenu);

        bar.setTranslateX(0);
        bar.setTranslateY(0);

        Button startPause = new Button("S/P");
        startPause.layoutXProperty().bind(manager.mainStage.widthProperty().subtract(startPause.widthProperty()).subtract(50));
        startPause.layoutYProperty().bind(manager.mainStage.heightProperty().subtract(startPause.heightProperty()).subtract(50));
        startPause.setOnAction((e) -> {
            if(isPaused) {
                timer.start();
                isPaused = false;
            }
            else {
                timer.stop();
                isPaused = true;
            }
        });

        root.getChildren().addAll(bar, startPause);
    }

    private void setUpGameInfo(Group root) {
        Label target = new Label("Cel: Tu Jest Cel");
        target.setFont(Font.font("Arial", 20));
        target.setTextFill(Color.WHITE);
        target.prefWidthProperty().bind(manager.mainStage.widthProperty().divide(6));
        target.layoutXProperty().bind(manager.mainStage.widthProperty().subtract(target.widthProperty().add(15)));


        Label time = new Label("Czas: \n Tu się wyświetla czas");
        time.prefWidthProperty().bind(target.prefWidthProperty());
        time.setFont(Font.font("Arial", 20));
        time.setTextFill(Color.WHITE);
        time.layoutXProperty().bind(target.layoutXProperty());
        time.layoutYProperty().bind(target.layoutYProperty().add(target.heightProperty().add(10)));

        Label fuel = new Label("Stan Paliwa: coś / max");
        fuel.prefWidthProperty().bind(target.prefWidthProperty());
        fuel.setFont(Font.font("Arial", 20));
        fuel.layoutXProperty().bind(target.layoutXProperty());
        fuel.setTextFill(Color.WHITE);
        fuel.layoutYProperty().bind(time.layoutYProperty().add(time.heightProperty().add(10)));

        root.getChildren().addAll(target,time,fuel);
    }

    private void setKeyShortcuts() {
        javaFxScene.setOnMousePressed(event -> {
            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();
        });
        javaFxScene.setOnMouseDragged(keyEvent -> {
            MainRenderer.ShiftScreenCords(
                    keyEvent.getSceneX() - lastMouseX,
                    keyEvent.getSceneY() - lastMouseY
            );
            lastMouseX = keyEvent.getSceneX();
            lastMouseY = keyEvent.getSceneY();
        });
        javaFxScene.setOnScroll(keyEvent -> {
            System.out.println(keyEvent.getDeltaY());
            if(keyEvent.getDeltaY() > 0) {
               MainRenderer.ResizeObjects(1.1);
               MainRenderer.ResizeScreenCoords(1.1);
           } else {
              MainRenderer.ResizeObjects(0.9);
              MainRenderer.ResizeScreenCoords(0.9);
            }
        });
        javaFxScene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case KeyCode.F12:
                    manager.mainStage.setFullScreen(false);
                    break;
                case KeyCode.F11:
                    manager.mainStage.setFullScreen(true);
                    break;
                case KeyCode.F9:
                    var MissCreSce = new MissionCreatorScene(manager);
                    manager.SwitchScene("MissCreSce", MissCreSce.GetJavafxScene());
                case KeyCode.F8:
                    var PlanetAddScene = new PlanetAdditionScene(manager);
                    manager.SwitchScene("PlanetAddScene", PlanetAddScene.GetJavafxScene());
                case KeyCode.ESCAPE:
                    // pause the game
                default: break;
            }
        });
    }
}

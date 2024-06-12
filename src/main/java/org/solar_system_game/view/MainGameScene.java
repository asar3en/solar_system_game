package org.solar_system_game.view;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;
import org.solar_system_game.sim.CelestialBody;
import org.solar_system_game.sim.SolarSystemParameters;
import org.solar_system_game.view.graphics.Camera;
import org.solar_system_game.view.graphics.Renderer;
import org.solar_system_game.view.graphics.SpaceShip;

import java.util.concurrent.TimeUnit;

public class MainGameScene implements ViewScene{
    Scene javaFxScene;
    ViewManager manager;
    AnimationTimer timer;
    Boolean isPaused = false;
    Renderer MainRenderer;
    private double lastMouseX;
    private double lastMouseY;
    int frameCount = 0;
    Boolean isShipAccelerating = false;
    double ThrustDirection;
    Scenario currentScenario;
    Group root;

    MenuBar menuBar;
    Camera cam;
    SpaceShip ss;

    @Override
    public Scene GetJavafxScene() {
        return javaFxScene;
    }

    @Override
    public void UpdateToCurrLocale() {
        root.getChildren().remove(menuBar);
        this.setUpButtons(root);
    }

    public MainGameScene(ViewManager manager) {
        this.manager = manager;
        root = new Group();
        javaFxScene = new Scene(root, manager.mainStage.getWidth(), manager.mainStage.getHeight(), Color.BLACK);
        setUpGameRendering(root);
        setUpButtons(root);
        setKeyShortcuts();
    }

    private void setUpGameRendering(Group root) {
        Pane renderPane = new Pane();
        renderPane.setMinWidth(javaFxScene.getWidth());
        renderPane.setMinHeight(javaFxScene.getHeight());
        root.getChildren().add(renderPane);

        final long[] lastUpdateTime = {0};
        final double TARGET_UPDATE_INT = 1.0 / 60;

        currentScenario = new Scenario();

        if(cam == null && cam == null && MainRenderer == null) {
            cam = new Camera(0, 0, 160_000_000, 90_000_000, javaFxScene.getWidth(), javaFxScene.getHeight());
            ss = new SpaceShip(currentScenario.GetSimSpaceship().bodyCoordinates[0] / cam.GetXFactor(), currentScenario.GetSimSpaceship().bodyCoordinates[1] / cam.GetYFactor(), 0.0, 10, 5, new Image("copyrightfreespaceship.png"));
            MainRenderer = new Renderer(renderPane, cam, ss, manager);
            currentScenario.AddRenderObjectsToRenderer(MainRenderer);
            MainRenderer.GenerateLabelNodes();
        }

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                double elapsedTime = (l - lastUpdateTime[0]) / 1_000_000_000.0;
                if (elapsedTime >= TARGET_UPDATE_INT) {
                    //CALCULATE THEORETICAL POSITIONS
                    if(!isPaused) {
                        for (int i = 0; i < 100; i++) {
                            for (CelestialBody body : currentScenario.getCelestialBodies())
                                body.nextPosition(currentScenario.getCelestialBodies());
                            currentScenario.GetSimSpaceship().nextPosition(currentScenario.getCelestialBodies(), isShipAccelerating, ss.Rotation);
                        }
                        for (CelestialBody body : currentScenario.getCelestialBodies()) {
                            currentScenario.realCelPositions.put(body.Name, new Pair<>(body.bodyCoordinates[0], body.bodyCoordinates[1]));
                        }
                        currentScenario.realCelPositions.put("SpaceShip", new Pair<>(currentScenario.GetSimSpaceship().bodyCoordinates[0], currentScenario.GetSimSpaceship().bodyCoordinates[1]));
                    }
                    //CALCULATE REAL POSITION IN RELATION TO CAMERA
                    var scaledPos = MainRenderer.ChangeRealPosToPixelRel(currentScenario.realCelPositions);

                    //UPDATE THE POSITIONS AND RADII
                    MainRenderer.UpdatePositions(scaledPos);
                    MainRenderer.UpdateRadiiFromReal(currentScenario.GetRadii());

                    //CLEAR AND RENDER
                    MainRenderer.Redraw();
                    lastUpdateTime[0] = l;
                    frameCount++;
                }
            }
        };
        timer.start();
    }

    private void setUpButtons(Group root) {
        menuBar = new MenuBar();
        Menu mainMenu = new Menu("Menu");
        MenuItem returnToMainMenu = new MenuItem(manager.menuElements.getString("returnToMenu"));
        MenuItem newGame = new MenuItem(manager.menuElements.getString("newGameRaw"));
        MenuItem settings = new MenuItem(manager.menuElements.getString("settings"));
        MenuItem exitGame = new MenuItem(manager.menuElements.getString("exit"));

        newGame.setOnAction(e -> {
            timer.stop();
             var mgS = new MainGameScene(manager);
             manager.SwitchScene("MGS", mgS);

        });

        returnToMainMenu.setOnAction(actionEvent -> {
            isPaused = true;
            manager.SwitchScene("MainMenu", null);
        });

        settings.setOnAction(actionEvent -> {
            if(!manager.SwitchScene("Settings", null)) {
                SettingsScene settingsScene = new SettingsScene(manager);
                manager.SwitchScene("Settings", settingsScene);
            }
        });


        exitGame.setOnAction(actionEvent -> Platform.exit());

        mainMenu.getItems().addAll(returnToMainMenu,newGame,settings,exitGame);
        menuBar.getMenus().add(mainMenu);

        menuBar.setTranslateX(0);
        menuBar.setTranslateY(0);

        Button startPause = new Button("S/P");
        startPause.layoutXProperty().bind(manager.mainStage.widthProperty().subtract(startPause.widthProperty()).subtract(50));
        startPause.layoutYProperty().bind(manager.mainStage.heightProperty().subtract(startPause.heightProperty()).subtract(50));
        startPause.setOnAction((e) -> {
            if(isPaused) {
                isPaused = false;
            }
            else {
                isPaused = true;
            }
        });

        root.getChildren().addAll(menuBar, startPause);
    }

    private void setKeyShortcuts() {
        //changes camera coords on resize
        javaFxScene.widthProperty().addListener(event -> {
            MainRenderer.Cam.PixelWidth = (javaFxScene.getWidth() >= 1600) ? javaFxScene.getWidth() : 1600;
        });
        javaFxScene.heightProperty().addListener(event -> {
            MainRenderer.Cam.PixelHeight = (javaFxScene.getHeight() >= 900) ? javaFxScene.getHeight() : 900;
        });

        //when you click with a mouse it gets its current position
        javaFxScene.setOnMousePressed(event -> {
            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();
        });
        //and when you drag it computes delta and changes the coordinates "in camera"
        javaFxScene.setOnMouseDragged(keyEvent -> {
            double currX = keyEvent.getSceneX();
            double currY = keyEvent.getSceneY();

            //Translates the change in pixels to change in real coordinates, almost sure it is wrong will test later
            MainRenderer.Cam.TopLeftRealXCord = MainRenderer.Cam.TopLeftRealXCord -
                    (currX - lastMouseX)*MainRenderer.Cam.GetXFactor();
            MainRenderer.Cam.TopLeftRealYCord = MainRenderer.Cam.TopLeftRealYCord -
                    (currY - lastMouseY)*MainRenderer.Cam.GetYFactor();

            lastMouseX = currX;
            lastMouseY = currY;
        });
        javaFxScene.setOnScroll(keyEvent -> {
            if(keyEvent.getDeltaY() > 0) {
               MainRenderer.Cam.RealHeight = MainRenderer.Cam.RealHeight*0.95;
               MainRenderer.Cam.RealWidth = MainRenderer.Cam.RealWidth*0.95;
           } else {
              MainRenderer.Cam.RealHeight = MainRenderer.Cam.RealHeight*1.05;
              MainRenderer.Cam.RealWidth = MainRenderer.Cam.RealWidth*1.05;
            }
        });
        javaFxScene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case KeyCode.W:
                    //move the ship, add acceleration, etc.
                    if (currentScenario.GetSimSpaceship().fuel >= 0){
                        ThrustDirection = 1;
                        currentScenario.GetSimSpaceship().impulsiveManeuver(ss.Rotation, ThrustDirection);
                    }
                    break;
                case KeyCode.A:
                    MainRenderer.spaceShip.Rotation+=0.0174533*5; //adds around 5 degrees
                    break;
                case KeyCode.D:
                    MainRenderer.spaceShip.Rotation-=0.0174533*5; //adds around 5 degrees
                    break;
                case KeyCode.S:
                    if (currentScenario.GetSimSpaceship().fuel >= 0){
                        ThrustDirection = -1;
                        currentScenario.GetSimSpaceship().impulsiveManeuver(ss.Rotation, ThrustDirection);
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case KeyCode.F12:
                    manager.mainStage.setFullScreen(false);
                    break;
                case KeyCode.F11:
                    manager.mainStage.setFullScreen(true);
                    break;
                case KeyCode.ESCAPE:
                    // pause the game
                default: break;
            }
        });

        javaFxScene.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case KeyCode.W:
                    isShipAccelerating = false;
                    break;
                default:
                    break;
            }
        });
    }
}

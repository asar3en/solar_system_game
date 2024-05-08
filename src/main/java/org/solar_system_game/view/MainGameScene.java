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
import org.solar_system_game.view.graphics.*;

import java.util.HashMap;
import java.util.Map;

public class MainGameScene implements ViewScene{
    Scene javaFxScene;
    ViewManager manager;
    AnimationTimer timer;
    Boolean isPaused = false;
    Renderer MainRenderer;
    private double lastMouseX;
    private double lastMouseY;
    int frameCount = 0;


    @Override
    public Scene GetJavafxScene() {
        return javaFxScene;
    }

    public MainGameScene(ViewManager manager) {
        this.manager = manager;
        Group root = new Group();
        javaFxScene = new Scene(root, manager.mainStage.getWidth(), manager.mainStage.getHeight(), Color.BLACK);
        setUpGameRendering(root);
        setUpButtons(root);
        setUpGameInfo(root);
        setKeyShortcuts();
    }

    private void setUpGameRendering(Group root) {
        Pane renderPane = new Pane();
        renderPane.setMinWidth(javaFxScene.getWidth());
        renderPane.setMinHeight(javaFxScene.getHeight());
        final double[] count = {0};
        root.getChildren().add(renderPane);

        final long[] lastUpdateTime = {0};
        final double TARGET_UPDATE_INT = 1.0 / 60;

        Camera cam = new Camera(0,0, 160_000_000, 90_000_000, javaFxScene.getWidth(), javaFxScene.getHeight());
        SpaceShip ss = new SpaceShip(70_000_000.0/cam.GetXFactor(), 5_000_000.0/cam.GetYFactor(), 0.0, 10, 5, new Image("copyrightfreestarship.png"));
        MainRenderer = new Renderer(renderPane, cam, ss);

        // ----- SET UP ALL OBJECTS POSITION AND RADII FOR CEL.

        // ---- STARTING SIM DATA
        Map<String, Pair<Double, Double>> realCelPositions = new HashMap<>();
        Pair<Double, Double> sunData = new Pair<>(0.0, 0.0);
        realCelPositions.put("SpaceShip", new Pair<>(70_000_000.0, 5_000_000.0));
        realCelPositions.put("Sun", sunData);

        Map<String, Double> radii = new HashMap<>();
        radii.put("Earth", 6371.0*1000);
        radii.put("Sun", 695_508.0);
        radii.put("SpaceShip", 10.0);//not a radius but is used for resizing

        // --- CREATING RENDERING OBJECTS VERY IMPORTANT FOR THE NAMES IN RADII relCelPositions and RenderObjects to be the same!
        RenderObject sun = new RenderObject(Color.YELLOW, "Sun");
        RenderObject earth = new RenderObject(Color.BLUE, "Earth");

        MainRenderer.AddToRenderList(sun);
        MainRenderer.AddToRenderList(earth);
        MainRenderer.GenerateLabelNodes();

        timer = new AnimationTimer() {
            final double EarthOrbit = 149_600_000;
            @Override
            public void handle(long l) {
                double elapsedTime = (l - lastUpdateTime[0]) / 1_000_000_000.0;
                if (elapsedTime >= TARGET_UPDATE_INT) {
                    //CALCULATE THEORETICAL POSITIONS
                        Pair<Double, Double> earthPos = new Pair<> (
                            (EarthOrbit*java.lang.Math.cos(frameCount*0.05)),
                            (EarthOrbit*java.lang.Math.sin(frameCount*0.05))
                        );
                        realCelPositions.put("Earth", earthPos);

                    //CALCULATE REAL POSITION IN RELATION TO CAMERA
                    var scaledPos = MainRenderer.ChangeRealPosToPixelRel(realCelPositions);

                    //UPDATE THE POSITIONS AND RADII
                    MainRenderer.UpdatePositions(scaledPos);
                    MainRenderer.UpdateRadiiFromReal(radii);

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
            System.out.println(keyEvent.getDeltaY());
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
                    break;
                case KeyCode.A:
                    MainRenderer.spaceShip.Rotation+=0.0174533; //adds around 1 degree
                    System.out.println("Ship rotation: " + MainRenderer.spaceShip.Rotation);
                    break;
                case KeyCode.D:
                    MainRenderer.spaceShip.Rotation-=0.0174533; //adds around 1 degree
                    System.out.println("Ship rotation: " + MainRenderer.spaceShip.Rotation);
                    break;
                case KeyCode.S:
                    break;
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

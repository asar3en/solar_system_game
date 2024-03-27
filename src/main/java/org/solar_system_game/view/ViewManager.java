package org.solar_system_game.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class ViewManager {
    private javafx.scene.Scene currentScene;
    public Stage mainStage;
    private HashMap<String, Scene> scenes = new HashMap<>();

    public ViewManager(Stage mainStage) {
        this.mainStage = mainStage;
        mainStage.setMinHeight(720);
        mainStage.setMinWidth(1080);
        mainStage.show();
    }

    private void SetupStageProperties() {
        mainStage.setFullScreen(true);
    }

    public javafx.scene.Scene GetCurrentScene() {
        return currentScene;
    }

    public Scene FindScene(String name) {
        for(var entry : scenes.entrySet())
            if(entry.getKey().equals(name))
                return entry.getValue();
        return null;
    }

    // It searches for the name provided in already existing scenes, if not found it tries to add provided scene
    //if provided scene is null and matching scene was not found throws exception
    public boolean SwitchScene(String name, Scene scene)  {
        Scene newScene = FindScene(name);
        if(newScene == null && scene != null)
        {
            newScene = scene;
            scenes.put(name, scene);
        }
        else if(newScene == null) {
            System.out.println("error not found the scene and provided scene was null");
            return false;
        }

        currentScene = newScene;
        mainStage.setScene(newScene);
        mainStage.show();
        return true;
    }
}

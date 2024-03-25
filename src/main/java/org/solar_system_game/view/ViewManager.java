package org.solar_system_game.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class ViewManager {
    private javafx.scene.Scene currentScene;
    private Stage mainStage;
    private HashMap<String, Scene> scenes = new HashMap<>();

    public ViewManager(Stage mainStage) {
        this.mainStage = mainStage;
        var mainMenu = new MainMenuScene();
        SwitchScene("MainMenu", mainMenu.GetJavafxScene());
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
    public void SwitchScene(String name, Scene scene)  {
        Scene newScene = FindScene(name);
        if(newScene == null && scene != null)
        {
            newScene = scene;
            scenes.put("MainMenu", scene);
        }
        else
            System.out.println("error not found the scene and provided scene was null");

        currentScene = newScene;
        mainStage.setScene(newScene);
        mainStage.show();
    }
}

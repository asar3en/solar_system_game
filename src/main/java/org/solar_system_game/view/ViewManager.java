package org.solar_system_game.view;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewManager {
    Locale currentLocale;
    public ResourceBundle menuElements;
    private javafx.scene.Scene currentScene;
    public Stage mainStage;
    private HashMap<String, ViewScene> scenes = new HashMap<>();

    public ViewManager(Stage mainStage) {
        this.mainStage = mainStage;
        mainStage.setMinHeight(900);
        mainStage.setMinWidth(1600);
        mainStage.setHeight(900);
        mainStage.setWidth(1600);
        mainStage.show();
        currentLocale = Locale.ENGLISH;
        menuElements = ResourceBundle.getBundle("Menu", currentLocale);
    }

    private void SetupStageProperties() {
        mainStage.setFullScreen(true);
    }

    public javafx.scene.Scene GetCurrentScene() {
        return currentScene;
    }

    public ViewScene FindScene(String name) {
        for(var entry : scenes.entrySet())
            if(entry.getKey().equals(name))
                return entry.getValue();
        return null;
    }

    // It searches for the name provided in already existing scenes, if not found it tries to add provided scene
    //if provided scene is null and matching scene was not found throws exception
    public boolean SwitchScene(String name, ViewScene scene)  {
        ViewScene newScene = FindScene(name);
        if(scene != null)
        {
            newScene = scene;
            scenes.put(name, scene);
        }
        else if(newScene == null) {
            System.out.println("error not found the scene and provided scene was null");
            return false;
        }

        currentScene = newScene.GetJavafxScene();
        mainStage.setScene(newScene.GetJavafxScene());
        mainStage.show();
        return true;
    }

    public void SwitchLocale(String lang) {
        currentLocale = Locale.forLanguageTag(lang);
        menuElements = ResourceBundle.getBundle("Menu", currentLocale);
        for(ViewScene scene : scenes.values()) {
            System.out.println("UPDATING SCENE");
            scene.UpdateToCurrLocale();
        }
    }
}

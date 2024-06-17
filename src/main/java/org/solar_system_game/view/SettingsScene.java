package org.solar_system_game.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.solar_system_game.Main;
import javafx.scene.control.CheckBox;

import java.util.Locale;

public class SettingsScene implements ViewScene{
    Scene javaFxScene;
    ViewManager manager;
    @Override
    public Scene GetJavafxScene() {
        return javaFxScene;
    }

    @Override
    public void UpdateToCurrLocale() {
        Group root = new Group();
        setUpButtons(root);
        javaFxScene.setRoot(root);
    }

    public SettingsScene(ViewManager manager) {
        this.manager = manager;
        Group root = new Group();
        setUpButtons(root);

        javaFxScene = new Scene(root, manager.mainStage.getWidth(), manager.mainStage.getHeight(), Color.BLACK);
        setKeyShortcuts();
    }

    private void setUpButtons(Group root) {
        VBox cbHolder = new VBox(10);
        CheckBox englishLang = new CheckBox(manager.menuElements.getString("englishLang"));
        CheckBox polishLang = new CheckBox(manager.menuElements.getString("polishLang"));
        englishLang.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        polishLang.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        if(manager.currentLocale.getLanguage().equals("en"))
            englishLang.setSelected(true);
        else if(manager.currentLocale.getLanguage().equals("pl"))
            polishLang.setSelected(true);

        englishLang.setOnAction(e -> {
            polishLang.setSelected(!polishLang.isSelected());
        });

        polishLang.setOnAction(e -> {
            englishLang.setSelected(!englishLang.isSelected());
        });

        cbHolder.layoutXProperty().bind(manager.mainStage.widthProperty().subtract(cbHolder.widthProperty()).divide(2));
        cbHolder.layoutYProperty().bind(manager.mainStage.heightProperty().subtract(cbHolder.heightProperty()).divide(3));
        cbHolder.prefWidthProperty().bind(manager.mainStage.widthProperty().divide(6));

        cbHolder.getChildren().addAll(englishLang, polishLang);

        Button applyButton = new Button(manager.menuElements.getString("apply"));
        applyButton.layoutXProperty().bind(cbHolder.layoutXProperty());
        applyButton.layoutYProperty().bind(cbHolder.layoutYProperty().add(cbHolder.heightProperty()).add(10));

        applyButton.setOnAction(event -> {
            if(englishLang.isSelected())
                manager.SwitchLocale("en");
            else if (polishLang.isSelected())
                manager.SwitchLocale("pl");
        });

        Button backButton = new Button(manager.menuElements.getString("returnToMenu"));
        backButton.layoutXProperty().bind(applyButton.layoutXProperty());
        backButton.layoutYProperty().bind(applyButton.layoutYProperty().add(applyButton.heightProperty()));

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                manager.SwitchScene("MainMenu", null);
            }
        });

        root.getChildren().addAll(cbHolder, applyButton, backButton);
    }

    private void setKeyShortcuts() {
        javaFxScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case KeyCode.F12:
                        manager.mainStage.setFullScreen(false);
                        break;
                    case KeyCode.F11:
                        manager.mainStage.setFullScreen(true);
                        break;
                    default: break;
                }
            }
        });
    }
}

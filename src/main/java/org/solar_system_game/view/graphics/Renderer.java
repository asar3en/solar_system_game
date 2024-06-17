package org.solar_system_game.view.graphics;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import org.solar_system_game.view.ViewManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Renderer {
    public Camera Cam;
    public List<RenderObject> renObjs = new ArrayList<RenderObject>();
    public Map<String, Label> objectLabels = new HashMap<>();
    public Pane renderPane;
    public  SpaceShip spaceShip;
    ViewManager manager;
    public Renderer(Pane renderPane, Camera camera, SpaceShip ss, ViewManager m) {
        this.renderPane = renderPane;
        this.Cam = camera;
        this.spaceShip = ss;
        manager = m;
    }

    public void GenerateLabelNodes() {
        for(RenderObject a : renObjs) {
            Label text = new Label(a.Label);
            text.setLayoutX(a.PositionX);
            text.setLayoutY(a.PositionY+a.Radius);
            text.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
            objectLabels.put(a.Label, text);
            renderPane.getChildren().add(text);
        }
        Label ssText = new Label("SpaceShip");
        ssText.setLayoutX(spaceShip.PosX);
        ssText.setLayoutY(spaceShip.PosY);
        ssText.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        objectLabels.put("SpaceShip", ssText);
        renderPane.getChildren().add(ssText);
    }

    public void AddToRenderList(RenderObject obj)
    {
        renObjs.add(obj);
    }
    public void Redraw()
    {
        renderPane.getChildren().clear();
        for(RenderObject a : renObjs) {
            renderPane.getChildren().add(a.GetNodeWithCurrSet());
        }
        for(Map.Entry<String, Label> a : objectLabels.entrySet())
            renderPane.getChildren().add(a.getValue());
        renderPane.getChildren().add(spaceShip.imageView);
    }

    public void UpdatePositions(Map<String, Pair<Double, Double>> positions)
    {
        if(objectLabels.containsKey("SpaceShip")) {
            Pair<Double, Double> ssCords = positions.get("SpaceShip");
            spaceShip.ChangePosition(ssCords.getKey(), ssCords.getValue());
        }
        for(RenderObject a : renObjs) {
            if(positions.containsKey(a.Label)) {
                Pair<Double, Double> pixelCords = positions.get(a.Label);
                a.ChangePos(pixelCords.getKey(), pixelCords.getValue());
            }
            if(objectLabels.containsKey(a.Label)) {
                Label l = objectLabels.get(a.Label);
                l.setText(manager.menuElements.getString(a.Label));
                l.setLayoutX(a.PositionX);
                l.setLayoutY(a.PositionY + a.Radius);
            }
        }
        Label ss = objectLabels.get("SpaceShip");
        ss.setLayoutX(spaceShip.PosX);
        ss.setLayoutY(spaceShip.PosY+spaceShip.Height/2);
        while(spaceShip.Rotation > java.lang.Math.PI * 2) {
            spaceShip.Rotation = spaceShip.Rotation - 2 * Math.PI;
        }
        spaceShip.imageView.setRotate(-spaceShip.Rotation* (180.0 / Math.PI));
    }
    public void UpdateRadiiFromReal(Map<String, Double> radii)
    {
        for(RenderObject a : renObjs) {
            if(radii.containsKey(a.Label)) {
                double realRadius = radii.get(a.Label);
                a.ChangeRadius(realRadius / Cam.GetXFactor());
            }
        }
        if (radii.containsKey("SpaceShip")) {
            double spaceShipWidth = radii.get("SpaceShip");
            spaceShip.ChangeSize(
                    spaceShipWidth / Cam.GetXFactor(),
                    (spaceShipWidth/ 2) / Cam.GetYFactor()
            );
        }
    }

    //Moves the real coordinates to be relative to the camera, and scales them depending on current zoom
    public Map<String, Pair<Double, Double>> ChangeRealPosToPixelRel(Map<String, Pair<Double, Double>> pos)
    {
        Map<String, Pair<Double, Double>> pixelCords = new HashMap<>();
        for(Map.Entry<String, Pair<Double, Double>> entry : pos.entrySet()) {
            pixelCords.put(
                    entry.getKey(),
                    new Pair<>(
                            (entry.getValue().getKey() - Cam.TopLeftRealXCord) / Cam.GetXFactor(),
                            (entry.getValue().getValue() - Cam.TopLeftRealYCord)/ Cam.GetYFactor()
                    )
            );
        }
        return  pixelCords;
    }
}

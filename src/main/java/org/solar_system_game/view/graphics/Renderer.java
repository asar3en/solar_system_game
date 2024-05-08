package org.solar_system_game.view.graphics;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Renderer {
    public Camera Cam;
    public List<RenderObject> renObjs = new ArrayList<RenderObject>();
    public Map<String, Label> objectLabels = new HashMap<>();
    public Pane renderPane;
    public Renderer(Pane renderPane, Camera camera) {this.renderPane = renderPane; this.Cam = camera;}

    public void GenerateLabelNodes() {
        for(RenderObject a : renObjs) {
            Label text = new Label(a.Label);
            text.setLayoutX(a.PositionX);
            text.setLayoutY(a.PositionY+a.Radius);
            text.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
            objectLabels.put(a.Label, text);
            renderPane.getChildren().add(text);
        }
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
    }

    public void UpdatePositions(Map<String, Pair<Double, Double>> positions)
    {
        for(RenderObject a : renObjs) {
            if(positions.containsKey(a.Label)) {
                Pair<Double, Double> pixelCords = positions.get(a.Label);
                a.ChangePos(pixelCords.getKey(), pixelCords.getValue());
            }
            if(objectLabels.containsKey(a.Label)) {
                Label l = objectLabels.get(a.Label);
                l.setLayoutX(a.PositionX);
                l.setLayoutY(a.PositionY + a.Radius);
            }
        }
    }
    public void UpdateRadiiFromReal(Map<String, Double> radii)
    {
        for(RenderObject a : renObjs) {
            if(radii.containsKey(a.Label)) {
                double realRadius = radii.get(a.Label);
                a.ChangeRadius(realRadius / Cam.GetXFactor());
            }
        }
    }

    //Moves the real coordinates to be relative to the camera, and scales them depending on current zoom
    public Map<String, Pair<Double, Double>> ChangeRealRelPosToPixelRel(Map<String, Pair<Double, Double>> pos)
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
            System.out.println("Object: " + entry.getKey()
                + " Real X: " + entry.getValue().getKey() + " Recalced X: " + (entry.getValue().getKey() - Cam.TopLeftRealXCord) / Cam.GetXFactor()
            );
            System.out.println("Object: " + entry.getKey()
                    + " Real Y: " + entry.getValue().getValue() + " Recalced Y: " + (entry.getValue().getValue() - Cam.TopLeftRealYCord)/ Cam.GetYFactor()
            );
        }
        return  pixelCords;
    }
}

package org.solar_system_game.view.graphics;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RenderObject {
    public String Label;
    public double PositionX;
    public double PositionY;
    public double Radius;
    public Color FillColor;
    public Circle circleNode;
    public Node GetNodeWithCurrSet() {
        return circleNode;
    }

    public void ChangePos(double newX, double newY) {
        PositionX = newX;
        PositionY = newY;
        circleNode.setCenterX(newX);
        circleNode.setCenterY(newY);
    }

    public void ChangeRadius(double newRad) {
        Radius = newRad;
        circleNode.setRadius(newRad);
    }

    public RenderObject(Color c, String label) {
        FillColor = c;
        circleNode = new Circle();
        circleNode.setCenterX(PositionX);
        circleNode.setCenterY(PositionY);
        circleNode.setRadius(Radius);
        circleNode.setFill(FillColor);
        Label = label;
    }
}


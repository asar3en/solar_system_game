package org.solar_system_game.view.graphics;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleRend extends RenderObject {
    double Radius;
    Color FillColor;
    Circle circleNode;
    @Override
    public Node GetNodeWithCurrSet() {
        return circleNode;
    }

    @Override
    public void ChangePos(double newX, double newY) {
        PositionX = newX;
        PositionY = newY;
        circleNode.setCenterX(newX);
        circleNode.setCenterY(newY);
    }

    @Override
    public void Rescale(double rescalingFactor) {
        Radius = Radius*rescalingFactor;
        circleNode.setRadius(Radius);
    }

    public CircleRend(double posX, double posY, double r, Color c) {
        PositionX = posX;
        PositionY = posY;
        Radius = r;
        FillColor = c;
        circleNode = new Circle();
        circleNode.setCenterX(PositionX);
        circleNode.setCenterY(PositionY);
        circleNode.setRadius(Radius);
        circleNode.setFill(FillColor);
        System.out.println("CREATING NEW CIRCLE");
    }
}

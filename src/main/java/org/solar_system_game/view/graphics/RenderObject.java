package org.solar_system_game.view.graphics;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

abstract public class RenderObject {
    double PositionX;
    double PositionY;
    abstract public Node GetNodeWithCurrSet();
    abstract public void ChangePos(double newX, double newY);

    //all implementations of rescaling have to have implemented a mechanism of
    //"hiding" objects that have size not suitable to be showed.
    abstract public void Rescale(double rescalingFactor);
}


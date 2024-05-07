package org.solar_system_game.view.graphics;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    public List<RenderObject> renObjs = new ArrayList<RenderObject>();

    public void AddToRenderList(RenderObject obj)
    {
        renObjs.add(obj);
    }
    public void Redraw(Pane pane)
    {
        pane.getChildren().clear();
        for(RenderObject a : renObjs) {
            pane.getChildren().add(a.GetNodeWithCurrSet());
        }
    }

    public void ResizeObjects(double resFactor) {
        for(RenderObject a : renObjs) {
            a.Rescale(resFactor);
        }
    }
    public void ResizeScreenCoords(double resFactor) {
        for(RenderObject a : renObjs) {
            a.ChangePos(a.PositionX*resFactor, a.PositionY*resFactor);
        }
    }
    public void ShiftScreenCords(double xShift, double yShift) {
        for(RenderObject a : renObjs) {
            a.ChangePos(a.PositionX+xShift, a.PositionY+yShift);
        }
        // VERY IMPORTANT HERE WE CHECK IF SOME OBJECTS THAT WERE NOT VISABLE PREVIOUSLY SHOULD
        // APPEAR AND IF SO WE SWITCH THEIR VISIABLE BOOL
    }
}

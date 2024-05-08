package org.solar_system_game.view.graphics;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpaceShip {
    public double PosX;
    public double PosY;
    public double Rotation; //radians
    public double Width;
    public double Height;
    public Image sprite;
    public ImageView imageView;

    public SpaceShip(double posX, double posY, double rotation, double width, double height, Image sprite) {
        PosX = posX;
        PosY = posY;
        Rotation = rotation;
        Width = width;
        Height = height;
        this.sprite = sprite;
        imageView = new ImageView(sprite);
        imageView.setFitWidth(Width);
        imageView.setFitHeight(Height);
    }

    public void ChangePosition(double newX, double newY) {
        PosX = newX;
        PosY = newY;
        imageView.setLayoutX(newX);
        imageView.setLayoutY(newY);
    }

    public void ChangeSize(double newWidth, double newHeight) {
        if(newWidth > 50)
            Width = newWidth;
        else
            Width = 50;
        if(newHeight > 25)
            Height = newHeight;
        else
            Height = 25;
        imageView.setFitHeight(Width);
        imageView.setFitWidth(Height);
    }
}

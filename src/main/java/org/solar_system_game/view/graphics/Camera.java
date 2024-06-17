package org.solar_system_game.view.graphics;

public class Camera {
    public double TopLeftRealXCord;
    public double TopLeftRealYCord;
    public double RealWidth;
    public double RealHeight;
    public double PixelWidth;
    public double PixelHeight;

    public Camera(double topLeftRealXCord, double topLeftRealYCord, double realWidth, double realHeight, double pixelWidth, double pixelHeight) {
        TopLeftRealXCord = topLeftRealXCord;
        TopLeftRealYCord = topLeftRealYCord;
        RealWidth = realWidth;
        RealHeight = realHeight;
        PixelWidth = pixelWidth;
        PixelHeight = pixelHeight;
    }

    public double GetXFactor() {return RealWidth / PixelWidth; }
    public double GetYFactor() {return RealHeight / PixelHeight; }

}

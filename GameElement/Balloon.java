package GameElement;

import java.awt.*;

public class Balloon {
    private double size;
    private double heightWidthRatio;
    private double speed;
    private double speedup;
    private double maxSpeed;
    private Color color;
    private int point;
    private int gameControlParameter;
    private String displayText;


    private static final double HEIGHT = 4;
    private static final double WIDTH = 3;

    public Balloon(double size, double heightWidthRatio, double speed, double speedup, double maxSpeed, Color color, int point, int gameControlParameter, String displayText) {
        this.size = size;
        this.heightWidthRatio = heightWidthRatio;
        this.speed = speed;
        this.speedup = speedup;
        this.maxSpeed = maxSpeed;
        this.color = color;
        this.point = point;
        this.gameControlParameter = gameControlParameter;
        this.displayText = displayText;
    }

    public double getSize() {
        return size;
    }

    public double getHeightWidthRatio() { return heightWidthRatio; }

    public Color getColor() {
        return color;
    }

    public double getSpeed() { return speed; }

    public double getSpeedup() { return speedup; }

    public int getPoint() { return point; }

    public double getGameControlParameter() { return gameControlParameter; }

    public void updateSpeed() {
        if (speed < maxSpeed) {
            speed += speedup;
        }
    }

    public String getText() {
        if (point > 0) {
            return String.valueOf(Math.round(point));
        } else {
            return displayText;
        }
    }

}

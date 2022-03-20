package Animation;
import Core.Engine;
import GameElement.Coordinate;
import GameElement.Balloon;
import edu.princeton.cs.introcs.StdDraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class Animation extends JComponent {
    private static final int canvasWidth = 1200;
    private static final int canvasHeight = 600;
    private static final double xScale = 200.0;
    private static final double yScale = 100.0;

    private static final double xIndent = 5.0;
    private static final double yIndent = 5.0;

    private int gameLevel;

    public Animation() {

    }

    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }

    public double getXScale() {
        return xScale;
    }
    public double getYScale() {
        return yScale;
    }

    public void gameDisplayInitialize() {
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);

        StdDraw.setXscale(0, xScale);
        StdDraw.setYscale(0, yScale);

        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.005);

        StdDraw.clear();
    }

    public void pointsDisplay(int points) {
        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("ITALIC", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.text(xScale  - 20, yScale - 5.0, "Points: ");
        StdDraw.text(xScale  - 10, yScale - 5.0, Integer.toString(points));
    }

    public void clockDisplay(int timeRemaining) {
        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("ITALIC", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.text(10, yScale - 5.0, "Time: ");
        StdDraw.text(20, yScale - 5.0, Integer.toString(timeRemaining));
    }

    public void backgroundDisplay(int gameLevel) {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(xScale / 2, yScale / 2, xScale / 2, yScale / 2);

        StdDraw.picture(xScale / 2, yScale / 2, "Animation/background" + Integer.toString(gameLevel) + ".jpg", xScale, yScale);

    }

    public void gameDisplay(int timeRemaining, int points,
                            List<Balloon> balloons, List<GameElement.Coordinate> balloonLocations,
                            int gameIntervalTime) {
        backgroundDisplay(gameLevel);
        pointsDisplay(points);
        clockDisplay(timeRemaining);

        for (int i = 0; i < balloons.size(); i++) {
            displayBalloon(balloons.get(i), balloonLocations.get(i));
        }

        StdDraw.show();
        StdDraw.pause(gameIntervalTime);
    }

    public void displayBalloon(Balloon balloon, GameElement.Coordinate balloonLocation) {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.ellipse(balloonLocation.getX(), balloonLocation.getY(), balloon.getSize(), balloon.getSize() * balloon.getHeightWidthRatio());
        StdDraw.arc(balloonLocation.getX(), balloonLocation.getY() - balloon.getSize() * balloon.getHeightWidthRatio() - balloon.getSize() / 2.0
                , balloon.getSize() / 2.0, 90, 270);
        StdDraw.arc(balloonLocation.getX(), balloonLocation.getY() - balloon.getSize() * balloon.getHeightWidthRatio() - balloon.getSize() * 3.0 / 2.0
                , balloon.getSize() / 2.0, -90, 90);

        if (gameLevel < 10) {
            StdDraw.setPenColor(balloon.getColor());
        } else {
            StdDraw.setPenColor(new Color(0,153,0));
        }

        StdDraw.filledEllipse(balloonLocation.getX(), balloonLocation.getY(), balloon.getSize(), balloon.getSize() * balloon.getHeightWidthRatio());

        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("ITALIC", Font.BOLD, (int) balloon.getSize() * 8);
        StdDraw.setFont(font);
        StdDraw.text(balloonLocation.getX(), balloonLocation.getY(), balloon.getText());

    }

    public void explodeBalloon(Balloon balloon, GameElement.Coordinate balloonLocation) {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.ellipse(balloonLocation.getX(), balloonLocation.getY(), balloon.getSize(), balloon.getSize() * balloon.getHeightWidthRatio());

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledEllipse(balloonLocation.getX(), balloonLocation.getY(), balloon.getSize(), balloon.getSize() * balloon.getHeightWidthRatio());
    }

    public GameElement.Coordinate checkMouse() {
        if (StdDraw.isMousePressed()) {
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();
            return new GameElement.Coordinate(mouseX, mouseY);
        } else {
            return null;
        }

    }

    public void gameEndDisplay(int points, boolean gamePassStatus) {
        StdDraw.clear();

        if (gamePassStatus) {
            StdDraw.picture(xScale / 2, yScale * 4 / 5, "Animation/gamePassTrue.jpg", 30, 30);
        } else {
            StdDraw.picture(xScale / 2, yScale * 4 / 5, "Animation/gamePassFalse.jpg", 30, 30);
        }

        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("ITALIC", Font.BOLD, 45);
        StdDraw.setFont(font);
        if (gamePassStatus) {
            StdDraw.text(xScale  / 2, yScale * 3 / 5, "Level Passed.");
        } else {
            StdDraw.text(xScale  / 2, yScale * 3 / 5, "Game Over.");
        }

        font = new Font("ITALIC", Font.BOLD, 55);
        StdDraw.setFont(font);
        StdDraw.text(xScale  / 2, yScale * 2 / 5, "Your Points: ");
        StdDraw.text(xScale  / 2 + 30 + 4 * Integer.toString(points).length(), yScale * 2 / 5 , Integer.toString(points));

        font = new Font("ITALIC", Font.BOLD, 35);
        StdDraw.setFont(font);
        if (gamePassStatus) {
            StdDraw.text(xScale  / 2, yScale / 7, "Press Enter for the next level.");
        } else {
            StdDraw.text(xScale  / 2, yScale / 7, "Press Enter to exit.");
        }

        StdDraw.show();
    }


}

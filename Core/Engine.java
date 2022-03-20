package Core;

import Animation.Animation;
import GameElement.*;
import edu.princeton.cs.introcs.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine {
    private Random rd;
    private int seed;

    private int gameIntervalTime;
    private double balloonBaselineSpeed;
    private double balloonCreationSpeed;
    private int points;
    private int balloonBaselinePointsFactor;
    private int clock;
    private int gameEndingTime;
    private int boosterStartTime;
    private int boosterDuration;
    private boolean isMouseDown;
    private double boosterProb;
    private boolean boosterStatus;

    private int gameLevel;
    private static final int[] gamePassRequirement = new int[]{100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 2000};

    private List<Balloon> balloons;
    private List<GameElement.Coordinate> balloonLocations;

    private Animation animationImplementor;

    public Engine(){
        animationImplementor = new Animation();
        gameLevel = 0;
    }

    public void startGame() {
        configNewGame();
        animationImplementor.gameDisplayInitialize();
        animationImplementor.setGameLevel(gameLevel);
        playGame();
    }

    private void configNewGame() {
        seed = gameLevel;
        rd = new Random(seed);
        points = 0;
        gameIntervalTime = 15;
        clock = 0;
        balloonBaselineSpeed = 0.4 + 0.01 * gameLevel;
        balloonCreationSpeed = 0.3 + 0.01 * gameLevel;
        gameEndingTime = 3000 + 500 * gameLevel;
        balloonBaselinePointsFactor = 1;
        boosterStartTime = 0;
        boosterDuration = 500 + 50 * gameLevel;
        boosterProb = 0.025 + 0.005 * gameLevel;
        isMouseDown = false;
        boosterStatus = false;
        balloons = new ArrayList<>();
        balloonLocations = new ArrayList<>();
    }

    private void playGame() {
        while (true) {
            animationImplementor.gameDisplay(gameEndingTime - clock, points,
                    balloons, balloonLocations, gameIntervalTime);
            GameElement.Coordinate mousePosition = animationImplementor.checkMouse();
            if (mousePosition != null && !isMouseDown) {
                isMouseDown = true;
                checkHitBalloons(mousePosition);
            } else if (mousePosition == null) {
                isMouseDown = false;
            }
            moveBalloons();
            clock += 1;
            if (clock % 10 == 0) {
                createBalloon();
            }
            if (boosterStartTime > 0 && clock - boosterStartTime > boosterDuration) {
                balloonDeBoost();
            }
            if (clock >= gameEndingTime || points > gamePassRequirement[gameLevel]) {
                endGame();
            }
        }
    }

    private void endGame() {
        boolean gamePassStatus = points > gamePassRequirement[gameLevel];
        animationImplementor.gameEndDisplay(points, gamePassStatus);
        while (!StdDraw.isKeyPressed(10)) {
            StdDraw.pause(1);
        }
        if (gamePassStatus && gameLevel < 10) {
            gameLevel += 1;
            startGame();
        } else {
            System.exit(0);
        }
    }

    private void createBalloon() {
        while (rd.nextDouble() <= balloonCreationSpeed) {
            double balloonIndex = rd.nextDouble();
            if (balloonIndex <= 0.35) {
                balloons.add(BalloonSet.balloon3);
            } else if (balloonIndex <= 0.65) {
                balloons.add(BalloonSet.balloon5);
            } else if (balloonIndex <= 0.85) {
                balloons.add(BalloonSet.balloon7);
            } else if (balloonIndex <= 1 - boosterProb || boosterStartTime > 0) {
                balloons.add(BalloonSet.balloon9);
            } else {
                balloons.add(BalloonSet.balloonDouble);
            }
            balloonLocations.add(new Coordinate(rd.nextFloat() * animationImplementor.getXScale(), 0.0));
        }
    }

    private void moveBalloons() {
        for (int i = 0; i < balloons.size(); i++) {
            if (isOutOfWindow(balloonLocations.get(i))) {
                deleteBalloon(i);
            } else {
                balloonLocations.get(i).move(0, balloonBaselineSpeed + balloons.get(i).getSpeed());
                balloons.get(i).updateSpeed();
            }
        }
    }

    private void checkHitBalloons(GameElement.Coordinate mousePosition) {
        for (int i = 0; i < balloons.size(); i++) {
            if (hitBalloon(mousePosition, balloons.get(i), balloonLocations.get(i))) {
                points += balloons.get(i).getPoint() * balloonBaselinePointsFactor;
                if (balloons.get(i).getGameControlParameter() == 1 && !boosterStatus) {
                    balloonBoost();
                }
                explodeBalloon(i);
            }
        }
    }

    private void balloonBoost() {
        balloonBaselineSpeed *= 2.0;
        balloonCreationSpeed *= 2.0;
        balloonBaselinePointsFactor = 2;
        boosterStartTime = clock;
        boosterStatus = true;
    }

    private void balloonDeBoost() {
        balloonBaselineSpeed /= 2.0;
        balloonCreationSpeed /= 2.0;
        balloonBaselinePointsFactor = 1;
        boosterStartTime = 0;
        boosterStatus = false;
    }

    private boolean hitBalloon(GameElement.Coordinate mousePosition, Balloon balloon, Coordinate balloonLocation) {
        if (Math.pow(mousePosition.getX() - balloonLocation.getX(), 2)/Math.pow(balloon.getSize(), 2)
                + Math.pow(mousePosition.getY() - balloonLocation.getY(), 2)/Math.pow(balloon.getSize() * balloon.getHeightWidthRatio(), 2) <= 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOutOfWindow(Coordinate balloonLocation) {
        if (balloonLocation.getX() < 0.0 || balloonLocation.getX() > animationImplementor.getXScale()
                || balloonLocation.getY() < 0.0 || balloonLocation.getY() > animationImplementor.getYScale()) {
            return true;
        } else {
            return false;
        }
    }

    private void explodeBalloon(int balloonIndex) {
        animationImplementor.explodeBalloon(balloons.get(balloonIndex), balloonLocations.get(balloonIndex));
        deleteBalloon(balloonIndex);
    }

    private void deleteBalloon(int balloonIndex) {
        rd.nextDouble(balloonIndex + 1); // Add some randomness in the game.
        balloons.remove(balloonIndex);
        balloonLocations.remove(balloonIndex);
    }


}




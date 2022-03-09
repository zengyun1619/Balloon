package GameElement;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class BalloonSet {
    public static final Balloon balloon3 = new Balloon(
            6, 1.2,0.5, 0,0.5, Color.CYAN, 3, 0, null
    );

    public static final Balloon balloon5 = new Balloon(
            5.5, 1.2,0.75, 0, 1.0,Color.PINK, 5, 0, null
    );

    public static final Balloon balloon7 = new Balloon(
            5, 1.2,1.0, 0,1.0, Color.MAGENTA, 7, 0, null
    );

    public static final Balloon balloon9 = new Balloon(
            4.5, 1.2,1.0, 0.0001,1.5, Color.ORANGE, 9, 0, null
    );

    public static final Balloon balloonDouble = new Balloon(
            4, 1,1.0, 0.0001,1.5, Color.YELLOW, 0, 1, "X2"
    );



}

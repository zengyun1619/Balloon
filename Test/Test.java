package Test;

import Core.Engine;
import edu.princeton.cs.introcs.StdDraw;

public class Test {

    public Test () {
    }

    public void runTest() {
        StdDraw.setCanvasSize(1200, 600);
        StdDraw.setXscale(0, 2);
        StdDraw.setYscale(0, 2);
        StdDraw.enableDoubleBuffering();
        StdDraw.arc(1, 1
                , 0.2, 90, 270);



        StdDraw.show();
        for (double t = 0.0; true; t += 0.02) {
            double x = Math.sin(t);
            double y = Math.cos(t);
            StdDraw.clear();
            StdDraw.filledCircle(x, y, 0.05);
            StdDraw.filledCircle(-x, -y, 0.05);

            StdDraw.pause(20);
        }
    }

    public static void main(String args[]) {
        Test test = new Test();
        test.runTest();
    }
}

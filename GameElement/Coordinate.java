package GameElement;

public class Coordinate {
    double x;
    double y;
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void move(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
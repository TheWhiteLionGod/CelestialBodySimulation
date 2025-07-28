package bodies;

import java.util.ArrayList;
import std.StdDraw;
import space.Quad;

public class Body {
    double x, y, vx, vy, m, ax, ay, forcex, forcey;
    public Body(double x, double y, double m, double vx, double vy) {
        this.x = x; // X Position
        this.y = y; // Y Position
        this.vx = vx; // Velocity X
        this.vy = vy; // Velocity Y
        this.m = m; // Mass
        this.ax = 0; // Acceleration X
        this.ay = 0; // Acceleration Y
        this.forcex = 0; // Force X
        this.forcey = 0; // Force Y
    }

    public Body(double x, double y, double m) {
        this.x = x; // X Position
        this.y = y; // Y Position
        this.vx = 0; // Velocity X
        this.vy = 0; // Velocity Y
        this.m = m; // Mass
        this.ax = 0; // Acceleration X
        this.ay = 0; // Acceleration Y
        this.forcex = 0; // Force X
        this.forcey = 0; // Force Y
    }

    public boolean in(Quad q) {
        return q.contains(x, y);
    }

    public Body plus(Body b) {
        double mass = this.m + b.m;
        double x_pos = ((this.x * this.m) + (b.x * b.m)) / mass;
        double y_pos = ((this.y * this.m) + (b.y * b.m)) / mass;
        return new Body(x_pos, y_pos, mass);
    }

    public void resetForces() {
        this.forcex = 0;
        this.forcey = 0;
    }

    public void updatePosition(double deltatime) {
        this.ax = this.forcex / this.m;
        this.ay = this.forcey / this.m;

        this.vx += deltatime * this.ax;
        this.vy += deltatime * this.ay;

        this.x += deltatime * this.vx;
        this.y += deltatime * this.vy;
    }

    public void drawToScreen(ArrayList<Integer> color) {
        StdDraw.setPenColor(color.get(0), color.get(1), color.get(2));
        StdDraw.filledCircle(this.x, this.y, Math.max(3 * this.m / Math.pow(10, 8), 3));
    }

    public static void main(String[] args) {
        Body b = new Body(0, 0, 0);
    }
}

package bodies;

import bodies.BHTree;
import bodies.Body;
import space.Quad;
import std.StdDraw;
import java.util.ArrayList;

public class Observer {
    public static void main(String[] args) {
        BHTree tree = new BHTree(new Quad(0, 0, 2000));
        ArrayList<Body> bodies = new ArrayList<Body>();
        ArrayList<ArrayList<Integer>> colors = new ArrayList<ArrayList<Integer>>();

        // Solar System Scaled Down
        bodies.add(new Body(0, 0, 1.9891 * Math.pow(10, 8))); // Sun
        bodies.add(new Body(13, -13, 3.285 * Math.pow(10, 1), -1, -1)); // Mercury
        bodies.add(new Body(-22, 22, 4.867 * Math.pow(10, 2), 1, 1)); // Venus
        bodies.add(new Body(31, -31, 5.97219 * Math.pow(10, 2), -1, -1)); // Earth
        bodies.add(new Body(-50, 50, 6.39 * Math.pow(10, 1), 1, 1)); // Mars

        // Red
        ArrayList<Integer> color = new ArrayList<Integer>(3);
        color.add(255);
        color.add(0);
        color.add(0);
        colors.add(new ArrayList<Integer>(color));

        // Orange
        color.set(1, 165);
        colors.add(new ArrayList<Integer>(color));

        // Yellow
        color.set(1, 255);
        colors.add(new ArrayList<Integer>(color));

        // Green
        color.set(0, 0);
        colors.add(new ArrayList<Integer>(color));

        // Blue
        color.set(1, 0);
        color.set(2, 255);
        colors.add(new ArrayList<Integer>(color));

        StdDraw.setTitle("Java Celestial Body Simulator");
        StdDraw.setScale(-250, 250);

        while (true) {
            tree.resetTree(bodies);
            StdDraw.setPenColor(0, 0, 0);
            StdDraw.filledRectangle(0, 0, 250, 250);

            for (Body b : bodies) { 
                b.resetForces();
                tree.updateForce(b);
                b.updatePosition(0.5);
                b.drawToScreen(colors.get(bodies.indexOf(b))); // Gets Corresponding Color
            }

            try {
                Thread.sleep(16);
            }
            catch (InterruptedException f) {

            }
        }
    }
}

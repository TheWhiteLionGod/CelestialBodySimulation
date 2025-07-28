package space;

import space.Point;

public class Quad {
    Point SW, NW, NE, SE;
    public Point center;
    double length;

    public Quad(double xmid, double ymid, double length) {
        this.SW = new Point(xmid - (0.5*length), ymid - (0.5*length));
        this.NW = new Point(xmid - (0.5*length), ymid + (0.5*length));
        this.NE = new Point(xmid + (0.5*length), ymid + (0.5*length));
        this.SE = new Point(xmid + (0.5*length), ymid - (0.5*length));
        this.center = new Point(xmid, ymid);
        this.length = length;
    }

    public boolean contains(double x, double y) {
        return NW.x <= x && x <= NE.x && SW.y <= y && y <= NW.y;
    }

    public double length() {
        return length;
    }

    public Quad NW() {
        return new Quad(
            NW.x + (0.25*length), 
            NW.y - (0.25*length), 
            (0.5*length)
        );
    }

    public Quad NE() {
        return new Quad(
            NE.x - (0.25*length), 
            NE.y - (0.25*length), 
            (0.5*length)
        );
    }

    public Quad SE() {
        return new Quad(
            SE.x - (0.25*length), 
            SE.y + (0.25*length), 
            (0.5*length)
        );
    }

    public Quad SW() {
        return new Quad(
            SW.x + (0.25*length), 
            SW.y + (0.25*length), 
            (0.5*length)
        );
    }

    public static void main(String[] args) {
        Quad quad = new Quad(0, 0, 5).SE();
        // System.out.println(quad.SW.x);
        // System.out.println(quad.SW.y);
        System.out.println(quad.contains(2.5, -2.5));
    }
}

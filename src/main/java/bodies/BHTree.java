package bodies;

import java.awt.print.Book;
import java.lang.reflect.Array;
import java.util.ArrayList;
import bodies.Body;
import space.Quad;
import space.Point;
import std.StdDraw;

public class BHTree {
    Body body;
    Quad quad;
    ArrayList<BHTree> trees;

    public BHTree(Quad q) {
        this.quad = q;
        this.trees = new ArrayList<BHTree>();
    }

    public BHTree(Quad q, Body b) {
        this.quad = q;
        this.body = b;
        this.trees = new ArrayList<BHTree>();
    }

    Quad findSuitableQuadrant(Body b) {
        Quad[] quadrants = {quad.NE(), quad.SE(), quad.SW(), quad.NW()};
        for (Quad quads : quadrants) {
            if (quads.contains(b.x, b.y)) {
                return quads;
            }
        }
        throw new IllegalStateException("Body Doesn't Fit Inside Quadrant");
    }

    public void resetTree(ArrayList<Body> bodies) {
        this.body = null;
        this.trees = new ArrayList<BHTree>();

        for (Body b : bodies) {
            insert(b);
        }
    }

    public void insert(Body b) {
        if (this.body == null && this.trees.size() == 0) {
            this.body = b;
            return;
        }

        Quad quads = findSuitableQuadrant(b);
        
        // If Node is External
        if (isExternalNode()) { 
            trees.add(new BHTree(quads, b));
            
            // Converting Node from External to Internal
            quads = findSuitableQuadrant(this.body);
            for (BHTree tree : new ArrayList<>(trees)) {
                if (tree.quad.center.x == quads.center.x && tree.quad.center.y == quads.center.y) {
                    tree.insert(this.body);
                    break;
                }
                
                // Is this the last object in the list?
                else if (trees.indexOf(tree) == trees.size()-1) {
                    trees.add(new BHTree(quads, this.body));
                }
            }
            this.body = null;
        }

        // If Node is Internal
        else {
            for (BHTree tree : new ArrayList<>(trees)) {
                if (tree.quad.center.x == quads.center.x && tree.quad.center.y == quads.center.y) {
                    tree.insert(b);
                    break;
                }
                
                // Is this the last object in the list?
                else if (trees.indexOf(tree) == trees.size()-1) {
                    trees.add(new BHTree(quads, b));
                }
            }
        }
    }

    boolean isExternalNode() {
        return trees.size() == 0;
    }

    Body getCenterOfMass(Body center_of_mass, Body exempt) {
        if (isExternalNode()) {
            if (this.body == exempt) { return center_of_mass; }
            else { return center_of_mass.plus(this.body); }
        }
        else {
            for (BHTree tree : trees) {
                center_of_mass = tree.getCenterOfMass(center_of_mass, exempt);
            }
            return center_of_mass;
        }
    }

    public void updateForce(Body b) {
        if (isExternalNode() && this.body != b) {
            double deltax = this.body.x - b.x;
            double deltay = this.body.y - b.y;
            double d = Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2));

            b.forcex += deltax / d;
            b.forcey += deltay / d;
        }
        else {
            Body center_of_mass = getCenterOfMass(new Body(0, 0, 0), b);
            double deltax = center_of_mass.x - b.x;
            double deltay = center_of_mass.y - b.y;

            double s = quad.length();
            double d = Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2));
            
            if ((s / d) < 0.5) {
                b.forcex += deltax / d;
                b.forcey += deltay / d;
            }
            else {
                for (BHTree tree : trees) {
                    tree.updateForce(b);
                }
            }
        }
    }
}

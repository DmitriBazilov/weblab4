package com.dmitri.backend.model;

import javax.ejb.Stateless;

@Stateless
public class HitChecker {

    public boolean checkHit(Hit hit) {
        double x = hit.getX();
        double y = hit.getY();
        double r = hit.getR();
        return checkTriangle(x, y, r)
                || checkCircle(x, y, r)
                || checkRectangle(x, y, r);
    }

    public boolean checkTriangle(double x, double y, double r) {
        if (r < 0) {
            return x >= 0 && x <= Math.abs(r) && y >= 0 && y <= Math.abs(r) && x + y <= -r;
        } else {
            return x <= 0 && x >= -r && y <= 0 && y >= -r && x + y >= -r;
        }
    }

    public boolean checkCircle(double x, double y, double r) {
        boolean circleEquation = (x * x + y * y) <= (r * r);
        if (r < 0) {
            return x >= 0 && x <= Math.abs(r) && y <= 0 && y >= r && circleEquation;
        } else {
            return x <= 0 && x >= -r && y >= 0 && y <= r && circleEquation;
        }
    }

    public boolean checkRectangle(double x, double y, double r) {
        if (r < 0) {
            return x >= r/2 && x <= 0 && y >= 0 && y <= Math.abs(r);
        } else {
            return x >= 0 && x <= r/2 && y <= 0 && Math.abs(y) <= r;
        }
    }
}

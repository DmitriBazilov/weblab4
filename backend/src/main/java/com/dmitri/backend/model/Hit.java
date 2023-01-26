package com.dmitri.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hit {

    private double x;

    private double y;

    private double r;

    public Hit() {}

    public Hit(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
}

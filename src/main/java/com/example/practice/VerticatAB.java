package com.example.practice;

import io.vertx.core.AbstractVerticle;

public class VerticatAB  extends AbstractVerticle{

    @Override
    public void start() throws Exception {
        vertx.setPeriodic(2000, id->{System.out.println("vetical AB");});
    }
}

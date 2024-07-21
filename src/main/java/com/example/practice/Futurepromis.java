package com.example.practice;

import io.vertx.core.Promise;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class Futurepromis{

    public static void main(String[] args) {
        Vertx vertx =Vertx.vertx();
        Future<String> future=method(vertx);

        future.onSuccess(handler->{
            System.out.println(handler);
        }).onFailure(err->{
            System.out.println(err.getMessage());
        });

    }

    private static Future<String> method(Vertx vertx) {
        Promise<String> promise=Promise.promise();

        vertx.setTimer(10000,id->{
            promise.complete("yo yo honey singh");
        });
        return promise.future();
    }

}

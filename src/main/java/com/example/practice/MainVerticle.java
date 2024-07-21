package com.example.practice;

import com.example.practice.eventBusPractice.EventbusExample;
import com.example.practice.eventBusPractice.WebServices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx vertx=Vertx.vertx();
   // vertx.deployVerticle(new MainVerticle());
    // vertx.deployVerticle(new EventbusExample());
    // vertx.deployVerticle(new WebServices());
    vertx.deployVerticle(new VerticatA());

    vertx.deployVerticle(new VerticatAA());

    vertx.deployVerticle(VerticatAB.class.getName(),new DeploymentOptions().setInstances(3));



  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8888).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}

package com.example.practice.eventBusPractice;

import javax.sound.midi.Receiver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class PTP extends AbstractVerticle{
 
    public static void main(String[] args) {
       Vertx vertx =Vertx.vertx();

       vertx.deployVerticle(new Sender());
       vertx.deployVerticle(new Receiver());

    }

    public static class Sender extends AbstractVerticle{

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();

            vertx.setPeriodic(1000, id->{
              vertx.eventBus().send(Sender.class.getName(), "hi i am saket");
            });
        }
    }

    public static class Receiver extends AbstractVerticle{

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();

        
              vertx.eventBus().<String>consumer(Sender.class.getName(), message->{
                System.out.println("Recived: " +message.body());
              });
            
        }
    }
}

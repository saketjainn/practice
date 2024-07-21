package com.example.practice.eventBusPractice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class P2S extends AbstractVerticle {
    
    public static void main(String[] args) {
       Vertx vertx =Vertx.vertx();
       
       vertx.deployVerticle(Publisher.class.getName(),new DeploymentOptions().setInstances(2));
       vertx.deployVerticle(Suscriber1.class.getName(),new DeploymentOptions().setInstances(2));
       vertx.deployVerticle(Suscriber2.class.getName(),new DeploymentOptions().setInstances(2));

    }

    public static class Publisher extends AbstractVerticle {
     @Override
     public void start(Promise<Void> startPromise) throws Exception {
         startPromise.complete();

         vertx.setPeriodic(2000,id->{
          vertx.eventBus().publish(Publisher.class.getName(), "Hi saket this side");
         });
     }
    }
    public static class Suscriber1 extends AbstractVerticle {
        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
   
            
             vertx.eventBus().consumer(Publisher.class.getName(), message->{
                System.out.println("hello saket 1");
             });
            
        }
       }

       public static class Suscriber2 extends AbstractVerticle {
        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
   
            
             vertx.eventBus().consumer(Publisher.class.getName(), message->{
                System.out.println("hello saket 2");
             });
            
        }
       }
}

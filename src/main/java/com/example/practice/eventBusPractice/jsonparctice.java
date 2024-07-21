package com.example.practice.eventBusPractice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class jsonparctice extends AbstractVerticle {
    int id;
    String name;
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public jsonparctice() {
    }
    public jsonparctice(int id, String name) {
        this.id = id;
        this.name = name;
    }

     @Override
     public void start() throws Exception {
         
       // Vertx vertx=Vertx.vertx();
       

        vertx.createHttpServer().requestHandler(request->{
            // request.bodyHandler(buffer->{
            //     JsonObject json=buffer.toJsonObject();
            //     jsonparctice jp=json.mapTo(jsonparctice.class);
            //     System.out.println("User id"+jp.getId()+"user name"+jp.getName());
            //     JsonObject response=JsonObject.mapFrom(jp);
            request.response().putHeader("content-type","text/plain").end(new JsonObject().put("name", "ali").encodePrettily());
        })
            
        .listen(8085,res->{
            if(res.succeeded()){
                System.out.println("serer started at 8085");
            }
            else{
                System.out.println("Failed" + res.cause());
            }
        });
    }

    public static void main(String[] args) {
       Vertx vertx=Vertx.vertx();

       vertx.deployVerticle(jsonparctice.class.getName());
    }

}

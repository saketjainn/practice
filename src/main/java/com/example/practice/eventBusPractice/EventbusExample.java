package com.example.practice.eventBusPractice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
public class EventbusExample extends AbstractVerticle{
    
   
@Override
public void start() throws Exception {
    EventBus eventBus=vertx.eventBus();
        eventBus.consumer("saket2", msg->{
            JsonObject userJson= (JsonObject) msg.body();
            User user =new User(userJson.getInteger("id"),userJson.getString("name"));
        System.out.println("Received message :"+ "id:" + user.getId() + "name:"+user.getName());

        msg.reply("Got the messgae");
    });
    eventBus.consumer("saket", msg->{
        System.out.println("Received message :"+ msg.body());

        msg.reply("Got the messgae");
    });

   JsonObject jp=new JsonObject()
     .put("name", "saket")
     .put("name", "antim");

     User user = new User(1,"ali");
     JsonObject jp2=user.toJson();
     //System.out.println("jp2 is :" +jp2.encodePrettily());

     vertx.setPeriodic(1000,id->{
    eventBus.send("saket", "hello form saket");
    eventBus.send("saket", jp);
    eventBus.send("saket", jp2);
});


    eventBus.request("saket", "antim is on call",reply->{
         System.out.println("sent msg :"+reply.result().body());
    });

    eventBus.request("saket", jp,reply->{
        System.out.println("sent msg :"+reply.result().body());
   });
    eventBus.publish("saket", "hello form yash");
    eventBus.publish("saket", jp);


}

public class User{
    int id;
    String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public User() {
    }

    public JsonObject toJson() {
        return new JsonObject()
          .put("id", this.id)
          .put("name", this.name);
      }
    
}
}

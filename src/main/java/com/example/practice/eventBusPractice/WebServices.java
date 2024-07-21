package com.example.practice.eventBusPractice;

import io.vertx.core.AbstractVerticle;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.*;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.Pool;

public class WebServices  extends AbstractVerticle{
    Router router =Router.router(vertx);

    MySQLConnectOptions connectOptions=new MySQLConnectOptions()
      .setPort(3306)
      .setHost("127.0.0.1")
      .setDatabase("vertx_crud")
      .setUser("root")
      .setPassword("root");

      PoolOptions poolOptions=new PoolOptions().setMaxSize(5);

      Pool client=Pool.pool(vertx, connectOptions, poolOptions);


    @Override
    public void start() throws Exception {
    router.route().handler(BodyHandler.create());

        client.getConnection(ar->{
              String name="hjgjkagf";
              String email="yash@p";
              int id=4;
            if(ar.succeeded()){
                SqlConnection sq=ar.result();
                sq.preparedQuery("INSERT INTO users (name,email) VALUES (?,?)")
                .execute(Tuple.of(name,email),res->{
                    sq.close();
                    if(res.succeeded()){
                        System.out.println("inserted successfully");
                    }
                    else{
                        System.out.println(" insertion failed");
                    }
                });

                sq.preparedQuery("SELECT name,email FROM users Where id=? ")
                .execute(Tuple.of(id),res->{
                    if(res.succeeded()){
                        sq.close();
                        if(res.result().size()>0)
                        System.out.println("Output :" + res.result().iterator().next().toJson());
                    }
                    else{
                        System.out.println("failed");
                    }
                });
                System.out.println("connection established");
            }else{
                System.out.println("DB connection falied:"+ar.cause().getMessage());
            }
        });

        router.post("/user").handler(context->{
            String name=context.getBodyAsJson().getString("name");
            String email=context.getBodyAsJson().getString("email");

            client.getConnection(ar->{
                if(ar.succeeded()){
                SqlConnection sq=ar.result();
            
              sq.preparedQuery("INSERT INTO users (name,email) VALUES(?,?)")
              .execute(Tuple.of(name,email),res->{
                if(res.succeeded()){
                    System.out.println("Inserted" + name + email);
                    context.response().setStatusCode(201).end("data inserted");
                }
                else{
                    System.out.println("failed"+res.cause());
                    context.response().setStatusCode(500).end("data not istersted"+ res.cause());
                }
              });
            }
            else{
                context.response().end("DB connection not established :" + ar.cause());
            }
                
            
            
            });
            

        });

        router.get("/hehe").handler(context->{
            context.request().response().putHeader("content-type","text/plain").end("hello from saket");
            });

            router.get("/json").handler(context->{
                JsonArray ja=new JsonArray()
                              .add("ashu")
                              .add(1)
                              .add(45.09)
                              .add(true);
                context.request().response().putHeader("content-type","text/plain").end(ja.encodePrettily());
                });
        
            vertx.createHttpServer().requestHandler(router).listen(8085,res->{
                if(res.succeeded()){
                    System.out.println("Server Started at 8085");
                }
                else{
                    System.out.println("Server Fail :" + res.cause());
                }
            });
    }

}

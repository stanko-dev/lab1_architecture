// File: com/example/config/MongoConnectionManager.java
package com.example.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

public class MongoConnectionManager {
    private static volatile MongoConnectionManager instance;
    private final MongoDatabaseConfig config;
    private final MongoClient mongoClient;

    private MongoConnectionManager(MongoDatabaseConfig config) {
        this.config = config;
        this.mongoClient = MongoClients.create(config.getConnectionString());
        initializeCollection();
    }

    public static MongoConnectionManager getInstance(MongoDatabaseConfig config) {
        if (instance == null) {
            synchronized (MongoConnectionManager.class) {
                if (instance == null) {
                    instance = new MongoConnectionManager(config);
                }
            }
        }
        return instance;
    }

    public MongoCollection<Document> getCollection() {
        return mongoClient
                .getDatabase(config.getDatabaseName())
                .getCollection(config.getCollectionName());
    }

    private void initializeCollection() {
        MongoCollection<Document> collection = getCollection();
        if (collection.countDocuments() == 0) {
            collection.insertMany(List.of(
                    new Document("id", 101)
                            .append("name", "Carla Gomez")
                            .append("department", "Sales")
                            .append("type", "SALES_MANAGER")
                            .append("base_salary", 4300.0)
                            .append("bonus", 0.0)
                            .append("hourly_rate", 0.0)
                            .append("hours_per_month", 0)
                            .append("commission_rate", 0.08)
                            .append("monthly_sales", 28000.0)
                            .append("on_call_hours", 0)
                            .append("on_call_rate", 0.0),
                    new Document("id", 102)
                            .append("name", "Derek Stone")
                            .append("department", "Sales")
                            .append("type", "MANAGER")
                            .append("base_salary", 6100.0)
                            .append("bonus", 1100.0)
                            .append("hourly_rate", 0.0)
                            .append("hours_per_month", 0)
                            .append("commission_rate", 0.0)
                            .append("monthly_sales", 0.0)
                            .append("on_call_hours", 0)
                            .append("on_call_rate", 0.0)
            ));
        }
    }
}

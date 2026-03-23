// File: com/example/config/MongoDatabaseConfig.java
package com.example.config;

public class MongoDatabaseConfig {
    private final String connectionString;
    private final String databaseName;
    private final String collectionName;

    private MongoDatabaseConfig(Builder builder) {
        this.connectionString = builder.connectionString;
        this.databaseName = builder.databaseName;
        this.collectionName = builder.collectionName;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public static class Builder {
        private String connectionString;
        private String databaseName;
        private String collectionName;

        public Builder connectionString(String connectionString) {
            this.connectionString = connectionString;
            return this;
        }

        public Builder databaseName(String databaseName) {
            this.databaseName = databaseName;
            return this;
        }

        public Builder collectionName(String collectionName) {
            this.collectionName = collectionName;
            return this;
        }

        public MongoDatabaseConfig build() {
            return new MongoDatabaseConfig(this);
        }
    }
}

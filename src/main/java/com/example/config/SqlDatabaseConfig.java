// File: com/example/config/SqlDatabaseConfig.java
package com.example.config;

public class SqlDatabaseConfig {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    private SqlDatabaseConfig(Builder builder) {
        this.jdbcUrl = builder.jdbcUrl;
        this.username = builder.username;
        this.password = builder.password;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private String jdbcUrl;
        private String username;
        private String password;

        public Builder jdbcUrl(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public SqlDatabaseConfig build() {
            return new SqlDatabaseConfig(this);
        }
    }
}

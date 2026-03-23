// File: com/example/config/ExcelDatabaseConfig.java
package com.example.config;

import java.nio.file.Path;

public class ExcelDatabaseConfig {
    private final Path filePath;
    private final String sheetName;

    private ExcelDatabaseConfig(Builder builder) {
        this.filePath = builder.filePath;
        this.sheetName = builder.sheetName;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getSheetName() {
        return sheetName;
    }

    public static class Builder {
        private Path filePath;
        private String sheetName;

        public Builder filePath(Path filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder sheetName(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }

        public ExcelDatabaseConfig build() {
            return new ExcelDatabaseConfig(this);
        }
    }
}

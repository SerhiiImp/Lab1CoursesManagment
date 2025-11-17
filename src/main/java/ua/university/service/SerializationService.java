package ua.university.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ua.university.exception.DataSerializationException;

public class SerializationService {
    private static final Logger logger = Logger.getLogger(SerializationService.class.getName());
    private final ObjectMapper jsonMapper;
    private final ObjectMapper yamlMapper;

    public SerializationService() {
        this.jsonMapper = new ObjectMapper();
        this.jsonMapper.registerModule(new JavaTimeModule());
        this.jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

        this.yamlMapper = new ObjectMapper(new YAMLFactory());
        this.yamlMapper.registerModule(new JavaTimeModule());
        this.yamlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public <T> void saveToJson(List<T> data, String filePath) throws DataSerializationException {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            jsonMapper.writeValue(file, data);
            logger.info("Successfully saved data to JSON: " + filePath);
        } catch (IOException e) {
            logger.severe("Failed to save data to JSON: " + e.getMessage());
            throw new DataSerializationException("Failed to serialize data to JSON", e);
        }
    }

    public <T> List<T> loadFromJson(String filePath, TypeReference<List<T>> typeReference) throws DataSerializationException {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new DataSerializationException("File not found: " + filePath);
            }
            List<T> data = jsonMapper.readValue(file, typeReference);
            logger.info("Successfully loaded data from JSON: " + filePath);
            return data;
        } catch (IOException e) {
            logger.severe("Failed to load data from JSON: " + e.getMessage());
            throw new DataSerializationException("Failed to deserialize data from JSON", e);
        }
    }

    public <T> void saveToYaml(List<T> data, String filePath) throws DataSerializationException {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            yamlMapper.writeValue(file, data);
            logger.info("Successfully saved data to YAML: " + filePath);
        } catch (IOException e) {
            logger.severe("Failed to save data to YAML: " + e.getMessage());
            throw new DataSerializationException("Failed to serialize data to YAML", e);
        }
    }

    public <T> List<T> loadFromYaml(String filePath, TypeReference<List<T>> typeReference) throws DataSerializationException {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new DataSerializationException("File not found: " + filePath);
            }
            List<T> data = yamlMapper.readValue(file, typeReference);
            logger.info("Successfully loaded data from YAML: " + filePath);
            return data;
        } catch (IOException e) {
            logger.severe("Failed to load data from YAML: " + e.getMessage());
            throw new DataSerializationException("Failed to deserialize data from YAML", e);
        }
    }
}

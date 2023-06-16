package ru.nsu.khlebnikov.model.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

/**
 * Class that is needed to get data from json config file.
 */
public class JsonGetter {
    /**
     * Method to get data from json config file.
     *
     * @param fileName - name of the config file
     * @return - json dto that contains all data to initialize the game
     */
    public JsonDto getData(String fileName) {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass()
                .getClassLoader().getResourceAsStream(fileName)))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<JsonDto>() {}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package ru.nsu.khlebnikov.model.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class JsonGetter {
    public JsonDTO getData(String fileName) {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName)))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<JsonDTO>() {}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

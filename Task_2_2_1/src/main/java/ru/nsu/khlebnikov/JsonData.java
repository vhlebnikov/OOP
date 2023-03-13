package ru.nsu.khlebnikov;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonData {
    private static class DeliverymanInfo {
        private final String name;
        private final int capacity;

        private DeliverymanInfo(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }
    }

    private final List<Baker> bakers;
    private final List<DeliverymanInfo> deliverymen;
    private final int storageCapacity;

    public JsonData(String fileName) throws IOException {
        Gson gson = new Gson();
        JsonData jsonData;
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(
                Pizzeria.class.getClassLoader().getResourceAsStream(fileName)))) {
            jsonData = gson.fromJson(reader, JsonData.class);
        }
        this.bakers = jsonData.bakers;
        this.deliverymen = jsonData.deliverymen;
        this.storageCapacity = jsonData.storageCapacity;
    }

    public List<Baker> getBakers() {
        return bakers;
    }

    public List<Deliveryman> getDeliverymen() {
        List<Deliveryman> deliverymenOutput = new ArrayList<>();
        for (DeliverymanInfo deliveryman : deliverymen) {
            deliverymenOutput.add(new Deliveryman(deliveryman.name, deliveryman.capacity));
        }
        return deliverymenOutput;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }
}

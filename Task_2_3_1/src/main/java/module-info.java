module ru.nsu.khlebnikov {
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.controls;
    requires com.google.gson;
    requires java.desktop;

    exports ru.nsu.khlebnikov;
    opens ru.nsu.khlebnikov to javafx.graphics;
    exports ru.nsu.khlebnikov.model.json;
    opens ru.nsu.khlebnikov.model.json to com.google.gson;
}
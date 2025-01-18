module org.fsb {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.fsb to javafx.fxml;
    exports org.fsb;
}

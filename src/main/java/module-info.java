module org.fsb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.dotenv;
    requires mysql.connector.j;
    requires jdk.jdi;

    opens org.fsb to javafx.fxml;
    exports org.fsb;
}

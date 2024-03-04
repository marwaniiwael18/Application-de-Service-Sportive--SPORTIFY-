module com.example.sportify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires twilio;
    requires org.controlsfx.controls;
    requires jbcrypt;
    requires javafx.media;
    requires javafx.web;
    requires java.mail;
    requires javafx.graphics;
    requires javafx.swing;
    requires org.json;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires jdk.jsobject;


    opens entities to javafx.base;
    opens com.example.sportify to javafx.fxml;
    exports com.example.sportify;

    opens com.example.sportify.controller to javafx.fxml; // Add this line to export the controller package

    // Add any additional module dependencies or exports as needed
    exports com.example.sportify.controller;
    opens controllers to javafx.fxml;

    // Open the test package to both javafx.fxml and javafx.graphics
    opens test to javafx.fxml, javafx.graphics;
    requires org.apache.poi.ooxml;
    // requires javase.java6;
    requires com.fasterxml.jackson.databind;
    requires MaterialFX;
    // requires core.java6;
    //requires poi;


    exports controllers;
    exports test;

}
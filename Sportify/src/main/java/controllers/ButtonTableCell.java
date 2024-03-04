package controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class ButtonTableCell<S, T> extends TableCell<S, T> {
    private final Button button;

    public ButtonTableCell() {
        this.button = new Button("JOIN");
        this.button.setStyle("-fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%), linear-gradient(#20262b, #191d22), radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0)); -fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0; -fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-font-family: Arial; -fx-text-fill: linear-gradient(white, #d0d0d0); -fx-font-size: 12px; -fx-padding: 10 20 10 20;");
        this.button.setOnAction(event -> {

            System.out.println("Button clicked for: " + getItem());
        });
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(button);
        }
    }
}
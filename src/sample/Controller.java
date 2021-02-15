package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class Controller {

    public Label demo_label;

    public void sayHello(ActionEvent actionEvent) {
        demo_label.setText("Hello World!");
    }
}

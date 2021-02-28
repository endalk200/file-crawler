package sample;

import crawler.Scan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.File;
import java.util.List;

public class Controller {

    public Button scan_btn;
    public TextField filename_field;
    public CheckBox shallow_scan_field;
    public CheckBox deep_scan_field;
    public Label message_board;
    public ListView<String> result_list;

    public void scan(ActionEvent actionEvent) {
        String filename = filename_field.getText();
        boolean deep_scan = deep_scan_field.isSelected();
        boolean shallow_scan = shallow_scan_field.isSelected();

        if (deep_scan) {
            filename_field.setText("scanning for " + filename + " in deep scan mode");
            try {
                Scan scanner = new Scan(filename, Integer.MAX_VALUE);
                List<File> found_files = scanner.scan();
                int found_files_size = found_files.size();

                ObservableList<String> listItems = FXCollections.observableArrayList();

                for(File file : found_files) {
                    listItems.add(file.toString());
                }
                result_list.setItems(listItems);

                message_board.setText("Found " + found_files_size + " files");
            } catch (Exception error_message) {

            }
        } else {
            message_board.setText("scanning for " + filename + " in shallow scan mode");
            try {
                Scan scanner = new Scan(filename);
                List<File> found_files = scanner.scan();
                int found_files_size = found_files.size();

                ObservableList<String> listItems = FXCollections.observableArrayList();

                for(File file : found_files) {
                    listItems.add(file.toString());
                }
                result_list.setItems(listItems);

                message_board.setText("Found " + found_files_size + " files");
            } catch (Exception error_message) {

            }
        }
    }

    public void change_deep_scan(ActionEvent actionEvent) {
        message_board.setText("Deep scan may consume a lot of CPU and Disk resource");
        shallow_scan_field.setSelected(false);
    }

    public void change_shallow_scan(ActionEvent actionEvent) {
        message_board.setText("Enter filename and scan.");
        deep_scan_field.setSelected(false);
    }
}

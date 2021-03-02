package sample;

import crawler.Scan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class Controller {

    public Button scan_btn;
    public TextField filename_field;
    public CheckBox shallow_scan_field;
    public CheckBox deep_scan_field;
    public Label message_board;
    public ListView<String> result_list;
    public Button filter_btn;
    public TextField file_extension_field;
    public ListView<String> recent_list;

    public void scan(ActionEvent actionEvent) {
        String filename = filename_field.getText();
        boolean deep_scan = deep_scan_field.isSelected();
        boolean shallow_scan = shallow_scan_field.isSelected();

        if (deep_scan == true) {
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
                JFrame parent_frame = new JFrame();
                JOptionPane.showMessageDialog(parent_frame,"ERROR" + error_message);
            }
        } else if (shallow_scan == true) {
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
                JFrame parent_frame = new JFrame();
                JOptionPane.showMessageDialog(parent_frame,"ERROR" + error_message);
            }
        }
    }

    public void scan_filter(ActionEvent actionEvent) {
        String filename = filename_field.getText();
        String file_extension = file_extension_field.getText();
        boolean deep_scan = deep_scan_field.isSelected();
        boolean shallow_scan = shallow_scan_field.isSelected();

        if (deep_scan == true) {
            filename_field.setText("scanning for " + filename + " filtered by " + file_extension + " in deep scan mode");

            try {
                Scan scanner = new Scan(filename, Integer.MAX_VALUE);
                List<File> filtered_files = scanner.scan(file_extension);
                int found_files_size = filtered_files.size();

                ObservableList<String> listItems = FXCollections.observableArrayList();

                for(File file : filtered_files) {
                    listItems.add(file.toString());
                }
                result_list.setItems(listItems);

                message_board.setText("Found " + found_files_size + " files");
            } catch (Exception error_message) {
                JFrame parent_frame = new JFrame();
                JOptionPane.showMessageDialog(parent_frame,"ERROR" + error_message);
            }
        } else if (shallow_scan == true) {
            message_board.setText("scanning for " + filename + " in shallow scan mode");
            try {
                Scan scanner = new Scan(filename);
                List<File> found_files = scanner.scan(file_extension);
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

    public void show_recent_searches(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recent.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Recently Searched files");
            stage.setScene(new Scene(root1, 400, 500));

            ObservableList<String> recent_items = FXCollections.observableArrayList();

            recent_items.add("Hey there");
            recent_items.add("Hey there");
            recent_items.add("Hey there");
            recent_items.add("Hey there");
            recent_items.add("Hey there");
            recent_items.add("Hey there");
            recent_items.add("Hey there");

//            recent_list.setItems(recent_items);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

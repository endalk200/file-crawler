package View;

import crawler.Scan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.net.URI;
import java.util.List;

public class Controller {

    public MenuItem close;
    public Button scan_btn;
    public TextField filename_field;
    public Label message_board;
    public ListView<URI> search_result;

    public void scan(ActionEvent actionEvent) {
        String filename = filename_field.getText();

        if(filename.length() == 0) {
            message_board.setText("ERROR: Enter file name correctly");
        }

        message_board.setText("INFO: searching for " + filename);

        try {
            Scan scanner = new Scan(filename);
            List<File> files = scanner.scan();

            if (files.size() == 0) {
                message_board.setText("INFO: There are no files containing that filename. Try other file name");
            } else {
                message_board.setText("INFO: Found " + files.size() + " files matching your query. Select to open file location");
            }

            ObservableList<URI> listItems = FXCollections.observableArrayList();

            for(File file : files) {
                listItems.add(file.toURI());
            }
            search_result.setItems(listItems);

        } catch (Exception error_message) {
            message_board.setText("ERROR: " + error_message);
            JFrame parent_frame = new JFrame();
            JOptionPane.showMessageDialog(parent_frame,"ERROR" + error_message);
        }
    }


    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void clear(ActionEvent actionEvent) {
        filename_field.setText("");
        message_board.setText("INFO: Enter filename and click search to search for the file");
        search_result.refresh();
    }

    public void about(ActionEvent actionEvent) {
        try {
            Stage aboutStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("about.fxml"));
            aboutStage.setTitle("File Crawler");
            aboutStage.setScene(new Scene(root, 472, 467));
            aboutStage.setResizable(false);
            aboutStage.show();
        } catch (Exception error_message) {
            message_board.setText("ERROR: " + error_message);
            JFrame parent_frame = new JFrame();
            JOptionPane.showMessageDialog(parent_frame,"ERROR" + error_message);
        }
    }

    public void handle_mouse_clicked(MouseEvent mouseEvent) {
        try {
            Runtime.getRuntime().exec("explorer.exe /select," + search_result.getSelectionModel().getSelectedItem());
        } catch (Exception error_message)    {
            message_board.setText("ERROR: " + error_message);
            JFrame parent_frame = new JFrame();
            JOptionPane.showMessageDialog(parent_frame,"ERROR" + error_message);
        }
    }
}

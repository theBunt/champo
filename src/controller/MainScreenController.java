package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable{

    public MainScreenController() {
    }

    @FXML
    private Button btn1;
    @FXML
    private Button btn2;


    public void changeImage(ActionEvent actionEvent) {
        //if (actionEvent.getSource().equals(play)) {
            System.out.println("moused over");

    }
@FXML
    public void kickOff(ActionEvent e) throws IOException {
    System.out.println("kick off button pressed");
    Button btn1;
    btn1  = (Button) e.getSource();
    Stage stage =(Stage) btn1.getScene().getWindow();
    Parent root;
    root = FXMLLoader.load(getClass().getResource("/controller/matchScreen.fxml"));
    Scene scene = new Scene(root);
    root.getStylesheets().add("styles.css");
    stage.setScene(scene);
    stage.show();

    }


    public void viewGroups(ActionEvent actionEvent) {
        System.out.println("gropu button pressed");
    }

    public void viewFixtures(ActionEvent actionEvent) {
        System.out.println("view fixtures button pressed");
    }

    public void playhover(Event event) {
        System.out.println("kick off button HOVERED");
    }

    public void viewHover(Event event) {
        System.out.println("view button HOVERED");
    }

    public void fixturesHover(Event event) {
        System.out.println("fixtures button HOVERED");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

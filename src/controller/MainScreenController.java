package controller;

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import test.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable, ControlledScreen{
    public ImageView defaultImage;
    public StackPane MainStack;
    public ImageView kickOffImg;
    public StackPane imageStack;
    public ImageView groupsImg;
    public ImageView fixturesImg;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    @FXML
    //private Image kickOffImg = new Image("kickOff.jpg");

    ScreensController myController;

    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    private Scene mainScreen, matchScreen, groupScreen;
    private Stage stage;
    private Parent root;

    public void changeImage(ActionEvent actionEvent) {
        //if (actionEvent.getSource().equals(play)) {
            System.out.println("moused over");

    }
@FXML
    public void kickOff(ActionEvent e) throws IOException {
        System.out.println("kick off button pressed");
        /*Button btn1;
        btn1  = (Button) e.getSource();
        stage =(Stage) btn1.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(SceneSelector.MATCH_SCREEN));
        matchScreen = new Scene(root,stage.getWidth(),stage.getHeight());
        root.getStylesheets().add("styles.css");
        stage.setScene(matchScreen);

        stage.show();*/
    //The method below replaces the above code using the stacked pane ScreensController
        myController.setScreen(MainApplication.matchScreenID);
    }


    public void viewFixtures(ActionEvent actionEvent) {

        System.out.println("fixtures button pressed");
    }

    public void viewGroups(ActionEvent e) throws IOException {
        System.out.println("View Group button pressed");
        myController.setScreen(MainApplication.groupScreenID);
    }

    public void playhover(Event event) {
        /*Path path = new Path();
        path.getElements().add(new MoveTo(20,20));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(kickOffImg);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();*/
        imageStack.getChildren().remove(0);
        imageStack.getChildren().add(kickOffImg);
        //defaultImage.setImage(kickOffImg);
        System.out.println("kick off button HOVERED");
    }

    public void buttonUnhover(Event event) {
        imageStack.getChildren().remove(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), defaultImage);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        /*fadeIn.setCycleCount(Timeline.INDEFINITE);
        fadeIn.setAutoReverse(true);*/
        imageStack.getChildren().add(defaultImage);


        System.out.println("kick off button UN-HOVERED");
    }

    public void viewHover(Event event) {
        imageStack.getChildren().remove(0);
        imageStack.getChildren().add(fixturesImg);
        System.out.println("view button HOVERED");
    }

    public void fixturesHover(Event event) {
        imageStack.getChildren().remove(0);
        imageStack.getChildren().add(groupsImg);
        System.out.println("fixtures button HOVERED");
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageStack.getChildren().clear();
        imageStack.getChildren().add(defaultImage);
    }


}

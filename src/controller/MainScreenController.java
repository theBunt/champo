package controller;

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.CubicCurveTo;
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
    public ImageView kickOffImg;
    //public ImageView ballAnim = new ImageView(new Image("kickOff.jpg"));
    public StackPane imageStack;
    public ImageView groupsImg;
    public ImageView fixturesImg;
    @FXML
    //private Image ball = new Image("kickOff.jpg");

    ScreensController myController;

    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    private Scene mainScreen, matchScreen, groupScreen;
    private Stage stage;
    private Parent root;




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
    kickOffImg.setSmooth(true);
    //KickOffAnim.start(kickOffImg, myController);

    myController.setScreen(MainApplication.matchScreenID);

        //The method below replaces the above code using the stacked pane ScreensController
        //myController.setScreen(MainApplication.matchScreenID);
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

    public void startAnimation(){
        Pane animPane = new Pane();
        System.out.printf("IMAGE SIZE: width = %.0f \t height = %.0f\n", kickOffImg.getFitWidth(),kickOffImg.getFitHeight());
        double tempX, tempY;

        Path path = new Path();
        tempX = kickOffImg.getX();
        tempY = kickOffImg.getY();
        path.getElements().add (new MoveTo ((kickOffImg.getFitWidth()/2), (kickOffImg.getFitHeight()/2)));
        path.getElements().add (new CubicCurveTo(-480f,200f, -490f, 100f, -400f, 50f));

        System.out.println("Position x = "+kickOffImg.getX()+"\tY = "+kickOffImg.getY());

        PathTransition pathTran = PathTransitionBuilder.create()
                .duration(new Duration(2000.0))
                .node(kickOffImg)
                .path(path)
                .orientation(PathTransition.OrientationType.NONE)
                .interpolator(Interpolator.LINEAR)
                .autoReverse(false)
                .cycleCount(1)
                .build();

        pathTran.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent AE) {
                kickOffImg.setX(0);
                kickOffImg.setY(0);
            }
        });

        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(1800), kickOffImg);
        rotateTransition.setByAngle(890f);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);

        //Scale Transition
        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(1900), kickOffImg);
        scaleTransition.setToX(3f);
        scaleTransition.setToY(3f);
        scaleTransition.setToZ(8f);
        scaleTransition.setCycleCount(1);
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent AE) {
                kickOffImg.setFitHeight(80);
                kickOffImg.setFitWidth(80);
            }
        });
        scaleTransition.setAutoReverse(false);

        ScaleTransition resetScale =
                new ScaleTransition(Duration.millis(100), kickOffImg);
        resetScale.setToX(-4f);
        resetScale.setToY(-4f);
        resetScale.setCycleCount(1);
        resetScale.setAutoReverse(false);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                pathTran,
                rotateTransition,
                scaleTransition
                //resetScale
        );
        parallelTransition.setCycleCount(1);
        parallelTransition.play();
        resetScale.play();

        parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent AE) {
                myController.setScreen(MainApplication.matchScreenID);
            }
        });
        //parallelTransition.stop();

        System.out.println("POSTION: \tX= " + kickOffImg.getX() + "\tY= " + kickOffImg.getY());
        System.out.println("SCALE: \tX= "+kickOffImg.getScaleX()+"\tY= "+kickOffImg.getScaleY());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ballAnim.setImage(ball);
        imageStack.getChildren().clear();
        imageStack.getChildren().add(defaultImage);
    }


}

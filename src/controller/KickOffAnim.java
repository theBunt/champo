package controller;

/**
 * Created by User on 06/09/2015.
 */
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import test.MainApplication;

import java.net.URL;
import java.util.ResourceBundle;

public class KickOffAnim implements Initializable {
    
    private ImageView img;

    public ImageView getBallImg() {
        return img;
    }

    public KickOffAnim() {
    }

    public static void start(ImageView img, ScreensController myController) {

        System.out.println("\n\t\tKickOffAnim started");
        System.out.printf("IMAGE SIZE: width = %.0f \t height = %.0f\n", img.getFitWidth(), img.getFitHeight());
        double tempX, tempY;

        Path path = new Path();
        Path pathReset = new Path();
        tempX = img.getX();
        tempY = img.getY();
        path.getElements().add(new MoveTo((img.getFitWidth() / 2), (img.getFitHeight() / 2)));
        path.getElements().add(new CubicCurveTo(-480f, 200f, -490f, 100f, -400f, 50f));

        pathReset.getElements().add(new MoveTo(0,0));
        //pathReset.getElements().add(new CubicCurveTo(480f, -200f, -490f, 100f, 400f, -50f));

        System.out.println("Position x = " + img.getX() + "\tY = " + img.getY());

        PathTransition pathTran = PathTransitionBuilder.create()
                .duration(new Duration(1980f))
                .node(img)
                .path(path)
                .orientation(PathTransition.OrientationType.NONE)
                .interpolator(Interpolator.LINEAR)
                .autoReverse(false)
                .cycleCount(1)
                .build();

        PathTransition pathReturn = new PathTransition();
        pathReturn.setDelay(new Duration(1980f));
        pathReturn.setDuration(Duration.millis(20));
        pathReturn.setNode(img);
        pathReturn.setPath(path);
        pathReturn.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathReturn.setCycleCount(1);
        pathReturn.setAutoReverse(true);


        /*pathTran.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent AE) {
                img.setX(0);
                img.setY(0);
            }
        });*/

        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(1980f), img);
        rotateTransition.setByAngle(890f);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);

        //Scale Transition
        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(1900), img);
        scaleTransition.setToX(3f);
        scaleTransition.setToY(3f);
        scaleTransition.setToZ(8f);
        scaleTransition.setCycleCount(1);
        scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent AE) {
                img.setFitHeight(80);
                img.setFitWidth(80);
            }
        });
        scaleTransition.setAutoReverse(false);

        ScaleTransition resetScale =
                new ScaleTransition(Duration.millis(100), img);
        resetScale.setToX(-4f);
        resetScale.setToY(-4f);
        resetScale.setCycleCount(1);
        resetScale.setAutoReverse(false);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                pathTran,
                rotateTransition,
                scaleTransition,
                pathReturn,
                resetScale
        );
        parallelTransition.setCycleCount(1);
        parallelTransition.play();


        parallelTransition.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent AE) {
                System.out.println("\t\ttransition finished");
                myController.setScreen(MainApplication.matchScreenID);
                System.out.println("POSITION: \tX= " + img.getX() + "\tY= " + img.getY());
                System.out.println("SCALE: \tX= " + img.getScaleX() + "\tY= " + img.getScaleY());
            }
        });
        //parallelTransition.stop();



    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

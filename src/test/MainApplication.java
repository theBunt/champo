package test;

import controller.ScreensController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Fixture;
import model.Match;
import model.Team;

public class MainApplication extends Application {

    Stage mainStage = new Stage();
    public static final String MAIN    = "/controller/mainScreen.fxml";
    public static String mainScreenID = "mainScreen";
    public static final String MATCH_SCREEN    = "/controller/matchScreen.fxml";
    public static String matchScreenID = "matchScreen";
    public static final String GROUP_SCREEN    = "/controller/groupScreen.fxml";
    public static String groupScreenID = "groupScreen";


    static final ObservableList<Team> groupA = FXCollections.observableArrayList(
            new Team("Liverpool","Anfield",82,79,82,82),
            new Team("Man Utd","Old Trafford",79,81,85,79),
            new Team("Real Madrid","Bernabeu",82,88,82,87),
            new Team("Barcelona","Camp Nou",84,82,81,91)
    );

    @Override
    public void start(Stage primaryStage) throws Exception{

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MainApplication.mainScreenID, MAIN);
        mainContainer.loadScreen(MainApplication.matchScreenID, MATCH_SCREEN);
        mainContainer.loadScreen(MainApplication.groupScreenID, GROUP_SCREEN);

        mainContainer.setScreen(MainApplication.mainScreenID);
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        mainContainer.getStylesheets().add("styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        /*mainStage = primaryStage;
        Parent root;
        Scene mainMenu, matchScreen;
        root = FXMLLoader.load(getClass().getResource("/controller/mainScreen.fxml"));
        //this screenbounds object makes the stage full screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        mainMenu = new Scene(root, 1000,800);
        //mainMenu = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
//        matchScreen = new Scene();
        Image bg = new Image("images/soccer-suit.jpg", true);

        //root.setId("pane");
        root.getStylesheets().add("styles.css");
       *//* Application.setUserAgentStylesheet(getClass().getResource("styles.css")
                .toExternalForm());*//*
        mainStage.setTitle("Champions League");
        mainStage.setScene(mainMenu);
        mainStage.setFullScreen(true);
        mainStage.show();*/
    }


    public static ObservableList<Team> getGroupA() {
        return groupA;
    }

    public static void main(String[] args) {
        launch(args);

    }

}

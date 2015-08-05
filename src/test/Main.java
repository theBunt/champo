package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Fixture;
import model.Match;
import model.Team;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;
        Scene mainMenu, matchScreen;
        root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        mainMenu = new Scene(root, 800,600);
//        matchScreen = new Scene();
        Image bg = new Image("images/soccer-suit.jpg", true);

        //root.setId("pane");
        root.getStylesheets().add("styles.css");
       /* Application.setUserAgentStylesheet(getClass().getResource("styles.css")
                .toExternalForm());*/
        primaryStage.setTitle("Champions League");
        primaryStage.setScene(mainMenu);
        primaryStage.show();
    }




    public static void main(String[] args) {
        Team lfc = new Team("Liverpool","Anfield",82,88,82,79);
        Team mufc = new Team("United","Old Trafford",78,85,78,81);
        Fixture tester = new Fixture(lfc,mufc);
        Match sample = new Match(tester);
        //sample.run();
        launch(args);

    }

}

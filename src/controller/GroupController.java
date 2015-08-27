package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Match;
import model.Team;
import test.MainApplication;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 23/08/2015.
 */
public class GroupController implements Initializable{


    public TableView groupA_table;
    public TableColumn teamStanding;
    public TableColumn teamName;
    public TableColumn winCol;
    public VBox groupVBox;
    public Label headers;
    private Match game;

    public GroupController() {
    }

    public GroupController(Match game) {
        this.game = game;
        //this.teamStanding = teamStanding;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamName.setCellValueFactory(new PropertyValueFactory<Team, String>("name"));
        winCol.setCellValueFactory(new PropertyValueFactory<Team, String>("name"));
        groupA_table.setItems(MainApplication.getGroupA());

        //headers.setText("Team\twins\tloss\tdraw");
    }
}

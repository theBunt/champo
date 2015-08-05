package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import model.Fixture;
import model.Match;
import model.Team;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by User on 19/07/2015.
 */
public class MatchScreenControl implements Initializable {

    public TextArea textCommentary;
    @FXML
    private ScrollPane scrollCommentary;
    private Match game;
    @FXML
    private Label lineUp;

    @FXML
    private Fixture teams;
    private Team homeTeam;
    private Team awayTeam;
    private int areaOfPitch;
    Random rand = new Random();
    private int changeovers = 0;
    private boolean possession;
    private int homeGoal;
    private int awayGoal;
    private int homeShots;
    private int awayShots;
    private boolean stop = false;

    public MatchScreenControl() {
        homeTeam = new Team("Liverpool","Anfield",82,88,82,79);
        awayTeam = new Team("United","Old Trafford",78,85,78,81);
        teams = new Fixture(homeTeam,awayTeam);
        game = new Match(teams);
    }

    /*public MatchScreenControl(Match game) {
        this.game = game;
    }*/

    public void setTeams() {
        Team homeTeam = new Team("Liverpool","Anfield",82,88,82,79);
        Team awayTeam = new Team("United","Old Trafford",78,85,78,81);
        teams = new Fixture(homeTeam,awayTeam);
        Match game = new Match(teams);
    }

    public void playMatch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("HHHHHHHHHHHHEEEEEEEEEEEEEEEEEEEEERRRRRRRRRRRRRREEEEEEEEEEEEEEEEEE\n\n\n\n888888888888888888888888\n\n\n");
                System.out.print(teams.toString());
                System.out.println("\nNEXT STEP !!!!!!!!!!!!!!!!!!\n\n\n");
                ArrayList<String> commentary = new ArrayList<String>();
                //flip coin to see who kicks off
                possession = flipCoin();
                //lineUp.setText("HELLO WORLD");
                if (possession) {
                    areaOfPitch = kickOff(homeTeam);
                    updateCommentary(String.format("%s to kick off.", homeTeam.getName()));
                } else {
                    areaOfPitch = kickOff(awayTeam);
                    updateCommentary(String.format(awayTeam.getName() + " to kick off."));
                }
                try {
                    for (int minutes = 0; minutes < 90; minutes++) {
                        Thread.sleep(200);
                        System.out.println(String.format(minutes + " mins"));
                        updateCommentary(String.format(minutes + " mins"));
                        areaOfPitch = advance(areaOfPitch, possession);
                        if (minutes == 45) {
                            System.out.println("\nHALF TIME");
                            System.out.println("changeovers = " + changeovers);
                            System.out.println(homeTeam.getName() + " " + teams.getHomeGoal() + " - " + teams.getAwayGoal() + " " + awayTeam.getName());
                            System.out.println("Press enter to continue:");
                            //in.nextLine();
                            if (!possession) {
                                System.out.println(homeTeam.getName() + " to kick off the 2nd half.");
                                updateCommentary(String.format(homeTeam.getName() + " to kick off the 2nd half."));
                            } else {
                                System.out.printf("%s to kick off the 2nd half.%n", awayTeam.getName());
                                updateCommentary(String.format(awayTeam.getName() + " to kick off the 2nd half."));
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("exception");
                }

                System.out.println("\nchangeovers = " + changeovers);
                System.out.println("FULL TIME");
                System.out.println("Shots: \n\t home = " + homeShots + "\taway = " + awayShots);
                System.out.println(homeTeam.getName() + " " + teams.getHomeGoal() + " - " + teams.getAwayGoal() + " " + awayTeam.getName());
            }
        }).start();
    }






    public void updateCommentary(String s){
        Platform.runLater(() ->
                textCommentary.appendText(s + "\n"));
        //textCommentary.appendText("\n");
    }

    public boolean flipCoin() {
        Random flip = new Random();
        int result = flip.nextInt(2);
        boolean heads;
        if (result == 0) {
            heads = true;
        } else {
            heads = false;
        }
        return heads;
    }

    public void shoot(boolean possession) {
        Team teamShooting;
        Team teamDefending;
        if(possession) {
            teamShooting = homeTeam;
            teamDefending = awayTeam;
            homeShots++;
        }
        else {
            teamShooting = awayTeam;
            teamDefending = homeTeam;
            awayShots++;
        }

        System.out.println(String.format(teamShooting.getName()+ " are shooting"));
        //flip coin to decide outcome until goalkeeping and shooting are assigned values and method coded
        //flip coin to decide if shot is on target
        if(flipCoin()) {
            goalScored(teamShooting, teamDefending);
        }
        else
        {
            System.out.println(String.format("Shot saved!!"));
            save();
        }
    }

    public void save() {
        int outcome = rand.nextInt(3);
        switch (outcome){
            case 0:
                //the outcome is a rebound
                rebound();
                break;
            case 1:
                //the keeper catches it
                System.out.println("the keeper has caught it");
                turnover(possession);
                break;
            case 2:
                corner();
        }
    }

    public void corner() {
        if(possession)
            System.out.println("its a corner for "+homeTeam.getName());
        else
            System.out.println("its a corner for "+awayTeam.getName());
        int cornerOutcome = rand.nextInt(3);
        switch(cornerOutcome){
            case 0:
                shoot(possession);
                break;
            case 1:
                turnover(possession);
                break;
            case 2:
                caughtByKeeper();
                break;
        }
    }

    private void goalScored(Team scored, Team conceeded){
        System.out.println("GOAL SCORED!!");
        updateCommentary(String.format("GOAL SCORED!!"));
        if(possession){
            homeGoal++;
            teams.increaseHomeGoal();
        }
        else
        {
            awayGoal++;
            teams.increaseAwayGoal();
        }
        scored.increaseGoalsFor();
        conceeded.increaseGoalsAgainst();
        kickOff(conceeded);
    }

    private void caughtByKeeper() {
        turnover(possession);
        if(flipCoin()){
            System.out.println("the keeper kicks it out");
            if(possession)
                areaOfPitch+=2;
            else
                areaOfPitch-=2;
            //ball is kicked further up the pitch but chance of loosing possession
            if(flipCoin()){
                System.out.println("the keeper kicks it to his own player");
            }
            else
            {
                System.out.println("the kick is intercepted");
                turnover(possession);
            }
        }
        else {
            System.out.println("the keeper throws it out");
            if(possession)
                areaOfPitch++;
            else
                areaOfPitch--;
        }
    }

    public void rebound() {
        if(flipCoin()){
            shoot(possession);
            System.out.println("gets the rebound");
        }
        else
        {
            turnover(possession);
            System.out.println("the chance is gone");
        }
    }

    private void turnover(boolean p) {
        possession = !p;
        changeovers++;
    }

    public int kickOff(Team tip) {
        int area = 3;
        System.out.println(tip.getName()+" to kick off");
        advance(3,possession);
        return area;
    }

    public int advance(int areaOfPitch, boolean poss){
        System.out.println();
        int home = 0;
        int away = 0;
        boolean possession = poss;
        //updateCommentary(String.format("TEST\n"));
        if(possession)
            System.out.print("home have the ball ");
        else
            System.out.print("away have the ball ");
        switch (areaOfPitch) {
            case 1:
                System.out.println("ball in area 1");
                home = rand.nextInt(homeTeam.getDefence());
                away = rand.nextInt(awayTeam.getAttack());
                System.out.printf("%d home -- away %d\n",home,away);
                if (possession && (home>away))
                    areaOfPitch++;
                else if (possession && (home<away)) {
                    turnover(possession);
                } else if (!possession && (away > home))
                    shoot(possession);
                else if (!possession && (home>away)) {
                    turnover(possession);
                }
                break;
            case 2:
                System.out.println("ball in area 2");
                home = rand.nextInt((homeTeam.getDefence()+ homeTeam.getMidfield()) / 2);
                away = rand.nextInt((awayTeam.getAttack()+ awayTeam.getMidfield()) / 2);
                System.out.printf("%d home -- away %d\n",home,away);
                if (possession && (home>away))
                    areaOfPitch++;
                else if (possession && (home<away)) {
                    turnover(possession);
                } else if (!possession && (away > home))
                    areaOfPitch--;
                else if (!possession && (home>away)) {
                    turnover(possession);
                }
                break;
            case 3:
                System.out.println("ball in area 3");
                home = rand.nextInt(homeTeam.getMidfield());
                away = rand.nextInt(awayTeam.getMidfield());
                System.out.printf("%d home -- away %d\n",home,away);
                if (possession && (home>away))
                    areaOfPitch++;
                else if (possession && (home<away)) {
                    turnover(possession);
                } else if (!possession && (away > home))
                    areaOfPitch--;
                else if (!possession && (home>away)) {
                    turnover(possession);
                }
                break;
            case 4:
                System.out.println("ball in area 4");
                home = rand.nextInt((homeTeam.getMidfield()+homeTeam.getAttack())/2);
                away = rand.nextInt((awayTeam.getMidfield()+awayTeam.getDefence())/2);
                System.out.printf("%d home -- away %d\n",home,away);
                if (possession && (home>away))
                    areaOfPitch++;
                else if (possession && (home<away)) {
                    turnover(possession);
                } else if (!possession && (away > home))
                    areaOfPitch--;
                else if (!possession && (home>away)) {
                    turnover(possession);
                }
                break;
            case 5:
                System.out.println("ball in area 5");
                home = rand.nextInt(homeTeam.getAttack());
                away = rand.nextInt(awayTeam.getDefence());
                System.out.printf("%d home -- away %d\n", home, away);
                if (possession && (home>away))
                    shoot(possession);
                else if (possession && (home<away)) {
                    turnover(possession);
                } else if (!possession && (away > home))
                    areaOfPitch--;
                else if (!possession && (home>away)) {
                    turnover(possession);
                }
                break;
        }
        return areaOfPitch;
    }

    /*@Override
    public void initialize() {

    }*/

    public void startGame(ActionEvent actionEvent) {
        playMatch();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lineUp.setText(homeTeam.getName()+"\tv\t"+awayTeam.getName());
    }
}

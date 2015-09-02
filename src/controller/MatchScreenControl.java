package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Fixture;
import model.GroupMatch;
import model.Match;
import model.Team;
import javafx.fxml.FXML;
import test.MainApplication;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by User on 19/07/2015.
 */
public class MatchScreenControl implements Initializable, ControlledScreen {

    public TextArea textCommentary;
    public Label homeShotStat;
    public Label awayShotStat;
    @FXML
    public Label homeGoalStat;
    public Label awayGoalStat;
    public Label homeScoreBoard;
    public Label awayScoreBoard;
    public Label totalShotStat;
    public ImageView ballArea;
    public Label lineUp1;
    public Label totalGoalStat;
    public Label homeCornerStat;
    public Label awayCornerStat;
    public Label totalCornerStat;
    public Label homeSaveStat;
    public Label awaySaveStat;
    public Label totalSaveStat;
    public HBox possessionBox;
    public Rectangle r1;
    public Rectangle r2;
    public ProgressBar possessionBar;
    public SplitPane commentaryPanel;
    private SimpleDoubleProperty prop;
    @FXML
    //private ScrollPane scrollCommentary;
    private Match game;
    @FXML
    private Label lineUp;
    @FXML
    private Fixture teams;
    private Team homeTeam;
    private StringProperty hg;
    private StringProperty ag;
    private StringProperty hs;
    private StringProperty as;
    private StringProperty totalScore;
    private StringProperty homeCorner;
    private StringProperty awayCorner;
    private StringProperty totalCorner;
    private StringProperty homeSave;
    private StringProperty awaySave;
    private StringProperty totalSave;
    private Team awayTeam;
    private int areaOfPitch;
    Random rand = new Random();
    private int changeovers = 0;
    private boolean possession;
    private int homeCornerCount;
    private int awayCornerCount;
    private int homeGoal = 0;
    private int awayGoal;
    private int homeShots;
    private int awayShots;
    private int homeSaveCounter;
    private int awaySaveCounter;
    private int teamPossessionCounter;
    private double possessionPercent;
    private boolean stop = false;
    private SimpleStringProperty ts;

    public MatchScreenControl() {
        homeTeam = new Team("Liverpool","Anfield",82,88,82,79);
        awayTeam = new Team("United","Old Trafford",78,85,78,81);
        //teams = new Fixture(homeTeam,awayTeam);
        game = new Match(homeTeam, awayTeam);
    }

    ScreensController myController;

    public StringProperty counterHomeGoal() {
        return hg;
    }

    public void playMatch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //System.out.print(teams.toString());
                //flip coin to see who kicks off
                possessionBar.progressProperty().bind(prop);
                possession = flipCoin();
                double mm;
                //teamPossessionCounter = 1;
                if (possession) {
                    areaOfPitch = kickOff(homeTeam);
                    updateCommentary(String.format("%s to kick off.", homeTeam.getName()));
                } else {
                    areaOfPitch = kickOff(awayTeam);
                    updateCommentary(String.format(awayTeam.getName() + " to kick off."));
                }
                try {
                    for (int minutes = 1; minutes < 91; minutes++) {
                        Thread.sleep(10);
                        possessionPercent = calcTeamPossession(possession, minutes, teamPossessionCounter);
                        System.out.println("team poss count = "+teamPossessionCounter);
                        System.out.println("team poss percentage = "+possessionPercent);
                        System.out.println(String.format(minutes + " mins"));
                        updateCommentary(String.format(minutes + " mins"));
                        areaOfPitch = advance(areaOfPitch, possession);
                        if (minutes == 45) {
                            updateCommentary("\nHALF TIME");
                            System.out.println("\nHALF TIME");
                            System.out.println("changeovers = " + changeovers);
                            System.out.println(homeTeam.getName() + " " + homeGoal + " - " + awayGoal + " " + awayTeam.getName());
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
                        final int finalMinutes = minutes;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // Update/Query the FX classes here
                                hg.set(String.valueOf(homeGoal));
                                hs.set(String.valueOf(homeShots));
                                ag.set(String.valueOf(awayGoal));
                                as.set(String.valueOf(awayShots));
                                ts.set(String.valueOf(homeShots+awayShots));
                                homeCorner.set(String.valueOf(homeCornerCount));
                                awayCorner.set(String.valueOf(awayCornerCount));
                                totalCorner.set(String.valueOf(homeCornerCount+awayCornerCount));
                                homeSave.set(String.valueOf(homeSaveCounter));
                                awaySave.set(String.valueOf(awaySaveCounter));
                                totalSave.set(String.valueOf(homeSaveCounter+awaySaveCounter));
                                ballArea.setX(positionBallX());
                                System.out.println("Ball position = "+areaOfPitch+"\nx = "+ballArea.getX()+"\ty = "+ballArea.getY());
                                //possessionBar.setProgress(possessionPercent);
                                prop.set(possessionPercent);
                                System.out.println("Progress = "+possessionBar.getProgress());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    System.out.println("exception");
                }
                catch (ArithmeticException ex) {
                    System.out.println("0 minutes exception");
                }

                System.out.println("\nchangeovers = " + changeovers);
                updateCommentary("\nFULL TIME");
                System.out.println("FULL TIME");
                System.out.println("Shots: \n\t home = " + homeShots + "\taway = " + awayShots);
                System.out.println("Home goals = "+homeGoal);
                System.out.println(homeTeam.getName() + " " + homeGoal + " - " + awayGoal + " " + awayTeam.getName());
            }

            private double calcTeamPossession(boolean possession, int minutes, int count) {
                /*possessionPercent = (int) (teamPossessionCounter/minutes);
                NumberFormat defaultFormat = NumberFormat.getPercentInstance();
                defaultFormat.setMinimumFractionDigits(1);
                System.out.println("Percent format: " + defaultFormat.format(possessionPercent));
                if(possession)
                    teamPossessionCounter++;
                System.out.println("Team possession = "+possessionPercent);
                return possessionPercent;*/
                System.out.println("minutes in poss calc = "+minutes);
                possessionPercent = (double) (count/minutes);
                if(possession)
                    teamPossessionCounter++;
                //System.out.println("Team possession = "+possessionPercent);

                return possessionPercent;
            }

            private int positionBallX() {
                int x = areaOfPitch;
                switch (x){
                    case 1:
                        x= -100;
                        break;
                    case 2:
                        x= -50;
                        break;
                    case 3:
                        x= 0;
                        break;
                    case 4:
                        x= 50;
                        break;
                    case 5:
                        x= 100;
                        break;
                }
                return x;
            }

        }).start();
    }

    public void updateCommentary(String s){
        try {
            Thread.sleep(450);
        } catch(InterruptedException e) {
            throw new RuntimeException("Don't know how to handle this", e);
        }
        Platform.runLater(() -> textCommentary.appendText(s + "\n"));
    }

    public double getTeamPossession() {
        return teamPossessionCounter;
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
        updateCommentary(String.format(teamShooting.getName()+ " are shooting"));
        System.out.println(String.format(teamShooting.getName()+ " are shooting"));
        //flip coin to decide outcome until goalkeeping and shooting are assigned values and method coded
        //flip coin to decide if shot is on target
        if(flipCoin()) {
            goalScored(teamShooting, teamDefending);
        }
        else
        {
            updateCommentary("Shot saved!!");
            System.out.println(String.format("Shot saved!!"));
            save();
        }
    }

    public void save() {
        int outcome = rand.nextInt(3);
        if(possession)
            homeSaveCounter++;
        else
            awaySaveCounter++;
        switch (outcome){
            case 0:
                //the outcome is a rebound
                updateCommentary("the ball is loose");
                rebound();
                break;
            case 1:
                //the keeper catches it
                updateCommentary("the keeper has caught it");
                System.out.println("the keeper has caught it");
                turnover(possession);
                break;
            case 2:
                corner();
        }
    }

    public void corner() {
        if(possession) {
            updateCommentary("its a corner for " + homeTeam.getName());
            System.out.println("its a corner for " + homeTeam.getName());
            homeCornerCount++;
        }
        else {
            updateCommentary("its a corner for " + awayTeam.getName());
            System.out.println("its a corner for " + awayTeam.getName());
            awayCornerCount++;
        }
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
            //teams.increaseHomeGoal();
            areaOfPitch = 3;
        }
        else
        {
            awayGoal++;
            //teams.increaseAwayGoal();
            areaOfPitch = 3;
        }

        scored.increaseGoalsFor();
        conceeded.increaseGoalsAgainst();
        kickOff(conceeded);
    }

    private void caughtByKeeper() {
        turnover(possession);
        if(flipCoin()){
            updateCommentary("the keeper kicks it out");
            System.out.println("the keeper kicks it out");
            if(possession)
                areaOfPitch+=2;
            else
                areaOfPitch-=2;
            //ball is kicked further up the pitch but chance of loosing possession
            if(flipCoin()){
                updateCommentary("the keeper kicks it to his own player");
                System.out.println("the keeper kicks it to his own player");
            }
            else
            {
                updateCommentary("the kick is intercepted");
                System.out.println("the kick is intercepted");
                turnover(possession);
            }
        }
        else {
            updateCommentary("the keeper throws it out");
            System.out.println("the keeper throws it out");
            if(possession)
                areaOfPitch++;
            else
                areaOfPitch--;
        }
    }

    public void rebound() {
        if(flipCoin()){
            updateCommentary("they get the rebound");
            shoot(possession);
            System.out.println("gets the rebound");
        }
        else
        {
            updateCommentary("the defender gets the ball");
            updateCommentary("the chance is gone");
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
        updateCommentary(tip.getName()+" to kick off");
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


    public void startGame(ActionEvent actionEvent) {
        /*homeShotStat.setText("0");
        awayShotStat.setText("0");
        homeGoalStat.setText("0");
        awayGoalStat.setText("0");
        homeScoreBoard.setText("0");
        awayScoreBoard.setText("0");
        totalShotStat.setText("0");*/
        playMatch();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lineUp.setText(homeTeam.getName());
        lineUp1.setText(awayTeam.getName());
        //Image ball = new Image("images/soccer-ball.png");


        //assign value to StringProperty
        //goals
        hg = new SimpleStringProperty(String.valueOf(homeGoal));
        ag = new SimpleStringProperty(String.valueOf(awayGoal));
        totalScore = new SimpleStringProperty(String.valueOf(homeGoal+awayGoal));
        //shots
        hs = new SimpleStringProperty(String.valueOf(homeShots));
        as = new SimpleStringProperty(String.valueOf(awayShots));
        ts = new SimpleStringProperty(String.valueOf(homeShots+awayShots));
        //corners
        homeCorner = new SimpleStringProperty(String.valueOf(homeCornerCount));
        awayCorner = new SimpleStringProperty(String.valueOf(awayCornerCount));
        totalCorner = new SimpleStringProperty(String.valueOf(homeCornerCount+awayCornerCount));
        //saves
        homeSave = new SimpleStringProperty(String.valueOf(homeSaveCounter));
        awaySave = new SimpleStringProperty(String.valueOf(awaySaveCounter));
        totalSave = new SimpleStringProperty(String.valueOf(homeSaveCounter+awaySaveCounter));
        //bind label to StringProperty
        //goals
        homeScoreBoard.textProperty().bind(hg);
        awayScoreBoard.textProperty().bind(ag);
        homeGoalStat.textProperty().bind(hg);
        awayGoalStat.textProperty().bind(ag);
        totalGoalStat.textProperty().bind(totalScore);

      //shots
        homeShotStat.textProperty().bind(hs);
        awayShotStat.textProperty().bind(as);
        totalShotStat.textProperty().bind(ts);
        //corners
        homeCornerStat.textProperty().bind(homeCorner);
        awayCornerStat.textProperty().bind(awayCorner);
        totalCornerStat.textProperty().bind(totalCorner);
        //saves
        homeSaveStat.textProperty().bind(homeSave);
        awaySaveStat.textProperty().bind(awaySave);
        totalSaveStat.textProperty().bind(totalSave);

        prop = new SimpleDoubleProperty(0);
        possessionBar = new ProgressBar();
        possessionBox = new HBox(possessionBar);


        r1 = new Rectangle();
        //r2 = new Rectangle();
        r1.setWidth(teamPossessionCounter);
        //r1.heightProperty().bind(this.getTableRow().heightProperty().multiply(0.5));
        //r1.setStyle("-fx-fill:#f3622d;");
        r2 = new Rectangle();
        //r2.widthProperty().bind(param.widthProperty().multiply(ratio2));
        //r2.setStyle("-fx-fill:#fba71b;");
        r2.setFill(Color.BLACK);


    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    public void returnToMain(ActionEvent actionEvent) {
        myController.setScreen(MainApplication.mainScreenID);
    }
}

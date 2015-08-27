package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by User on 08/07/2015.
 */
public class Team implements FootInt{

    private StringProperty teamName;
    private String name;
    private int wins ;
    private int loss ;
    private int draw ;
    private int goalsFor;
    private int goalsAgainst ;
    private int points;
    private int goalDiff;
    /*private int winCount;
    private int drawCount;
    private int lossCount;*/
    private String stadium;
    private int goalkeeping;
    private int defence;
    private int midfield;
    private int attack;

    //default Constructor
    Team(){
       /* wins.setValue(0);
        loss.setValue(0);
        draw.setValue(0);
        points.setValue(0);
        goalsFor.setValue(0);
        goalsAgainst.setValue(0);
        points.setValue(0);*/
        stadium = "";
    }

    public Team(String name, String stadium, int goalkeeping, int defence, int midfield, int attack) {
        this.teamName = new SimpleStringProperty(name);
        this.stadium = stadium;
        /*this.goalkeeping.setValue(goalkeeping);
        this.defence.setValue(defence);
        this.midfield.setValue(midfield);
        this.attack.setValue(attack);*/
        this.goalkeeping=goalkeeping;
        this.defence=defence;
        this.midfield=midfield;
        this.attack=attack;
    }

    /*public SimpleStringProperty printTeam(){
        String stats = getName() +"\t\t"+wins+"\t\t"+loss+"\t\t"+draw+"\t\t"+goalDiff+"\t\t"+points;
        SimpleStringProperty team = new SimpleStringProperty(name);
        return team ;
    }*/

    public int getWins() {
        return wins;
    }

    public int getLoss() {
        return loss;
    }

    public int getDraw() {
        return draw;
    }

    public int getPoints() {
        return points;
    }

    /*public SimpleStringProperty getTeamName() {
        return name;
    }*/

    public String getName() {
        return teamName.get();
    }

    public void calcGoalDiff(){
        goalDiff = goalsFor - goalsAgainst;
    }

    public int calcPoints(){
        calcGoalDiff();
        return points = (wins*WINPOINTS)+(loss*LOSSPOINTS)+(draw*DRAWPOINTS);
    }

    public void increaseWin(){
        wins++;
    }

    public void increaseLoss(){
        loss++;
    }

    public void increaseDraw(){
        draw++;
    }

    public String getStadium(){
        return stadium;
    }

    public int getGoalkeeping() {
        return goalkeeping;
    }

    public int getDefence() {
        return defence;
    }

    public int getMidfield() {
        return midfield;
    }

    public int getAttack() {
        return attack;
    }

    public void increaseGoalsFor(){
        //goalsFor.setAm
    }

    public void increaseGoalsAgainst(){
        goalsAgainst++;
    }

    public int compareTo(Team compare) {
        int comparePoints=((Team)compare).getPoints();
        /* For Ascending order*/
        //return this.points-comparePoints;

        /* For Descending order do like this */
        return comparePoints-this.points;
    }
}

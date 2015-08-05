package model;

/**
 * Created by User on 08/07/2015.
 */
public class Team implements FootInt{

    private String name;
    private int wins ;
    private int loss ;
    private int draw ;
    private int goalsFor;
    private int goalsAgainst ;
    private int points;
    private int goalDiff;
    private int winCount;
    private int drawCount;
    private int lossCount;
    private String stadium;
    private int goalkeeping;
    private int defence;
    private int midfield;
    private int attack;

    //default Constructor
    Team(){
        name = " ";
        wins = 0;
        loss = 0;
        draw = 0;
        points = 0;
        goalsFor = 0;
        goalsAgainst = 0;
        points = 0;
        stadium = "";
    }

    public Team(String name, String stad, int goalkeeping, int defence, int midfield, int attack) {
        this.name = name;
        this.stadium = stad;
        this.goalkeeping = goalkeeping;
        this.defence = defence;
        this.midfield = midfield;
        this.attack = attack;
        goalsFor = 0;
        goalsAgainst = 0;
    }

    public String toString(){
        return name +"\t\t"+wins+"\t\t"+loss+"\t\t"+draw+"\t\t"+goalDiff+"\t\t"+points;
    }

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

    public String getName() {
        return name;
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
        goalsFor++;
    }

    public void increaseGoalsAgainst(){
        goalsAgainst++;
    }
}

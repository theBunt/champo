package model;

import java.util.Random;

/**
 * Created by User on 08/07/2015.
 */
public class Fixture {

    protected Team homeTeam;
    protected Team awayTeam;
    protected String home;
    protected String away;
    private int homeGoal;
    private int awayGoal;
    private String datePlayed;
    private String venue;
    private int attendance;

//default constructor
    Fixture(){
        home = " ";
        away = " ";
        homeGoal = 0;
        awayGoal = 0;
        datePlayed = "-";
        venue = " ";
        attendance = 0;
    }

    public Fixture(Team homeTeamIn, Team awayTeamIn){
        homeTeam = homeTeamIn;
        awayTeam = awayTeamIn;
        home = homeTeamIn.getName();
        away = awayTeamIn.getName();
        homeGoal = 0;
        awayGoal = 0;
        venue = homeTeam.getStadium();
    }

    public String toString(){
        return homeTeam.getName()+" "+homeGoal+" vs "+awayGoal+" "+awayTeam.getName()+"\nVenue: "+homeTeam.getStadium();
    }

    public boolean flipCoin() {
        Random flip = new Random();
        int result = flip.nextInt(2);
        boolean homeTip;
        if (result == 0) {
            homeTip = true;
        } else {
            homeTip = false;
        }
        return homeTip;
    }

    public void increaseHomeGoal() {
        homeGoal++;
    }

    public void increaseAwayGoal() {
        awayGoal++;
    }

    public int getHomeGoal() {
        return homeGoal;
    }

    public int getAwayGoal() {
        return awayGoal;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }
}

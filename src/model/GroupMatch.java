package model;

/**
 * Created by User on 10/08/2015.
 */
public class GroupMatch extends Match implements FootInt {


    GroupMatch (){
        super();
    }

    GroupMatch (Team home, Team away){
        super(home,away);
    }

    public void playMatch() {
        super.playMatch();
        updateStats();
    }

    public void updateStats(){
        int homeGoal = super.getHomeGoal();
        int awayGoal = super.getAwayGoal();
        if(homeGoal>awayGoal)
        {
            homeTeam.increaseWin();
            awayTeam.increaseLoss();
        }
        else if(homeGoal<awayGoal){
            awayTeam.increaseWin();
            homeTeam.increaseLoss();

        }
        else{
            homeTeam.increaseDraw();
            awayTeam.increaseDraw();
        }
        homeTeam.calcPoints();
        awayTeam.calcPoints();
    }

    public String toString(){
        return super.toString();
    }
}

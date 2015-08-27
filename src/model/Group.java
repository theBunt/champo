package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by User on 10/08/2015.
 */
public class Group implements FootInt {
    Scanner in = new Scanner(System.in);

    private ArrayList<Team> teams = new ArrayList<Team>(GROUP_SIZE);
    private ArrayList<GroupMatch> matchList = new ArrayList<GroupMatch>((GROUP_SIZE/2)*(GROUP_SIZE-1)*2);
    private static int count;

    public Group() {
        teams = new ArrayList<Team>();
        count = 0;
    }

    public Group(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public void print() {
        System.out.println("\nTeam\t\t\tWins\tLoss\tDraw\t   GD\t\tPoints");
        System.out.println("______________________________________________________");
        //Collections.sort(teams);
        for(Team temp:teams){
            System.out.println(temp.toString());
        }
    }

    public void viewResults(){
        for(GroupMatch temp: matchList){
            temp.toString();
        }
    }

    public void arrangeFixtures() {
        for (int i = 0; i < (GROUP_SIZE / 2) * (GROUP_SIZE - 1)/2; i++) {
            matchList.add(new GroupMatch(teams.get(0), teams.get(1)));
            matchList.add(new GroupMatch(teams.get(2), teams.get(3)));
            Collections.rotate(teams,1);
            Collections.swap(teams,0,1);
        }

        /*for (int i = 0; i < (GROUP_SIZE / 2) * (GROUP_SIZE - 1)/2; i++) {
            matchList.add(new GroupMatch(teams.get(1), teams.get(0)));
            matchList.add(new GroupMatch(teams.get(3), teams.get(2)));
            Collections.rotate(teams,1);
            Collections.swap(teams,0,1);
        }
        Collections.swap(matchList,2,8);*/
    }

    public void printFixtures(){
        int x = 0;
        for(GroupMatch temp:matchList){
            System.out.println(x+"."+temp.toString());
            x++;
        }
    }

    public void playFixtures(){
        for(GroupMatch temp:matchList){
            temp.playMatch();
            in.nextLine();
        }
    }

}

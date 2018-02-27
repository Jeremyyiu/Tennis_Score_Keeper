package com.example.jeremy.tennisscorekeeperapp;


/**
 * Created by Jeremy on 13/02/2018.
 */

public class Player {
    private String name;
    private boolean isServing;
    private int faultCounter;
    private int games, sets, tiebreakerPoints;
    private Point points;

    public Player() {
        isServing = false;
        faultCounter = 0;
        games = 0;
        sets = 0;
        points = Point.point_0;
        tiebreakerPoints = 0;
    }

    /* Serving */
    public void setIsServing(boolean serving) {
        isServing = serving;
    }

    public boolean isServing() {
        return isServing;
    }

    /* Player name */
    public void setName(String playerName) {
        name = playerName;
    }

    public String getName() {
        return name;
    }

    public void resetName() { name = ""; }

    /* Points */
    public Point getPoints() {
        return points;
    }

    public void setPoints(Point score) {
        points = score;
    }

    public void resetPoints() { points = Point.point_0; }

    /* Tiebreaker Points */
    public int getTieBreakerPoints() {
        return tiebreakerPoints;
    }

    public void tiebreakerPointWin() {
        tiebreakerPoints++;
    }

    public void resetTieBreakerPoints() {
        tiebreakerPoints = 0;
    }

    /* games */
    public int getGames() {
        return games;
    }

    public void winGame() {
        games++;
    }

    public void resetGamesCounter() {
        games = 0;
    }

    /* Faults */
    public int getFaults() {
        return faultCounter;
    }

    public void addToFaultCounter() {
        faultCounter++;
    }

    public void resetFaultCounter() {
        faultCounter = 0;
    }


    /* Sets */
    public int getSets() {
        return sets;
    }

    public void winSet() { sets++; }

    public void resetSetCounter() {
        sets = 0;
    }

    public enum Point {
        point_0("0"),
        point_15("15"),
        point_30("30"),
        point_40("40"),
        point_deuce("deuce"),
        point_adv("adv"),
        point_tiebreaker("tb");

        private final String pointValue;

        Point(String pointValue) {
            this.pointValue = pointValue;
        }

        public String getPointValue() {
            return this.pointValue;
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jplus.bean;

/**
 *
 * @author Hyberbin
 */
public class MoveBean {
    private int score;
    private boolean moved;
    private int count;

    public MoveBean(int score, boolean moved, int count) {
        this.score = score;
        this.moved = moved;
        this.count = count;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}

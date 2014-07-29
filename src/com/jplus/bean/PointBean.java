/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jplus.bean;

import java.awt.Color;

/**
 * 一个点（格子）的模型
 * @author hyberbin
 */
public class PointBean implements Comparable<PointBean>{

    public final static int[] NUMS = new int[]{0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072};
    public final static Color BACK_GROUND = Color.WHITE;
    public final static Color LINE_GROUND = Color.GRAY;
    public final static Color[] COLOR = new Color[]{BACK_GROUND, Color.LIGHT_GRAY, Color.YELLOW, Color.MAGENTA, Color.ORANGE, Color.BLUE, Color.GREEN, Color.CYAN, Color.PINK, Color.WHITE, Color.YELLOW, Color.RED, Color.ORANGE, Color.BLUE, Color.GREEN, Color.CYAN, Color.PINK};
    public final static int size = 80;
    public final static int FONT_SIZE = 30;

    private final int x;
    private final int y;
    private int index;

    public PointBean(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void addIndex() {
        this.index++;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return NUMS[index];
    }

    public String getSValue() {
        return NUMS[index] + "";
    }

    public Color getColor() {
        return COLOR[index];
    }

    @Override
    public int compareTo(PointBean o) {
        return o.getValue()-getValue();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jplus.action;

import com.jplus.bean.MoveBean;
import com.jplus.bean.PointBean;
import com.jplus.forms.Main;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hyberbin
 */
public class PointBeanManager {

    public synchronized PointBean[][] getDefaultBeans() {
        PointBean[][] defaults = new PointBean[Main.SIZE][Main.SIZE];
        for (int x = 0; x < Main.SIZE; x++) {
            for (int y = 0; y < Main.SIZE; y++) {
                defaults[x][y] = new PointBean(x, y, 0);
            }
        }
        return defaults;
    }

    public synchronized PointBean[][] copy(PointBean[][] beans) {
        PointBean[][] defaults = new PointBean[Main.SIZE][Main.SIZE];
        for (int x = 0; x < Main.SIZE; x++) {
            for (int y = 0; y < Main.SIZE; y++) {
                defaults[x][y] = new PointBean(x, y, beans[x][y].getIndex());
            }
        }
        return defaults;
    }
    /**
     * 向左合并所有空的
     * @param beans
     * @return 
     */
    private synchronized boolean leftNull(PointBean[][] beans) {
        boolean moved = false;
        for (int x = 0; x < Main.SIZE; x++) {
            for (int y = 1; y < Main.SIZE; y++) {
                if (beans[x][y - 1].getIndex() == 0 && beans[x][y].getIndex() != 0) {//左边是空往左边移一个
                    beans[x][y - 1].setIndex(beans[x][y].getIndex());
                    beans[x][y].setIndex(0);
                    moved = true;
                }
            }
        }
        if (moved) {
            leftNull(beans);
        }
        return moved;
    }
    /**
     * 向左合并所有相同块
     * @param beans
     * @return 
     */
    public synchronized MoveBean left(PointBean[][] beans) {
        boolean leftNull = leftNull(beans);
        int count = 0;
        int score = 1;
        for (int x = 0; x < Main.SIZE; x++) {
            for (int y = 1; y < Main.SIZE; y++) {
                if (beans[x][y].getIndex() != 0 && beans[x][y].getIndex() == beans[x][y - 1].getIndex()) {//与左边相等
                    beans[x][y - 1].addIndex();
                    beans[x][y].setIndex(0);
                    count++;
                    score += getScore(beans[x][y - 1]);
                }
            }
        }
        leftNull(beans);
        return new MoveBean(score + (leftNull ? 1 : 0), leftNull || count > 0, count);
    }
    /**
     * 向右合并所有空块
     * @param beans
     * @return 
     */
    private synchronized boolean rightNull(PointBean[][] beans) {
        boolean moved = false;
        for (int x = 0; x < Main.SIZE; x++) {
            for (int y = Main.SIZE - 2; y >= 0; y--) {
                if (beans[x][y + 1].getIndex() == 0 && beans[x][y].getIndex() != 0) {//左边是空往左边移一个
                    beans[x][y + 1].setIndex(beans[x][y].getIndex());
                    beans[x][y].setIndex(0);
                    moved = true;
                }
            }
        }
        if (moved) {
            rightNull(beans);
        }
        return moved;
    }
    /**
     * 向右合并所有相同块
     * @param beans
     * @return 
     */
    public synchronized MoveBean right(PointBean[][] beans) {
        boolean rightNull = rightNull(beans);
        int count = 0;
        int score = 1;
        for (int x = 0; x < Main.SIZE; x++) {
            for (int y = Main.SIZE - 2; y >= 0; y--) {
                if (beans[x][y].getIndex() == beans[x][y + 1].getIndex() && beans[x][y].getIndex() != 0) {//与左边相等
                    beans[x][y + 1].addIndex();
                    beans[x][y].setIndex(0);
                    count++;
                    score += getScore(beans[x][y + 1]);
                }
            }
        }
        rightNull(beans);
        return new MoveBean(score + (rightNull ? 1 : 0), rightNull || count > 0, count);
    }
    /**
     * 向上合并所有空块
     * @param beans
     * @return 
     */
    private synchronized boolean upNull(PointBean[][] beans) {
        boolean moved = false;
        for (int x = 1; x < Main.SIZE; x++) {
            for (int y = 0; y < Main.SIZE; y++) {
                if (beans[x - 1][y].getIndex() == 0 && beans[x][y].getIndex() != 0) {//左边是空往左边移一个
                    beans[x - 1][y].setIndex(beans[x][y].getIndex());
                    beans[x][y].setIndex(0);
                    moved = true;
                }
            }
        }
        if (moved) {
            upNull(beans);
        }
        return moved;
    }
    /**
     * 向上合并所有相同块
     * @param beans
     * @return 
     */
    public synchronized MoveBean up(PointBean[][] beans) {
        boolean upNull = upNull(beans);
        int count = 0;
        int score = 1;
        for (int y = 0; y < Main.SIZE; y++) {
            for (int x = 1; x < Main.SIZE; x++) {
                if (beans[x][y].getIndex() == beans[x - 1][y].getIndex() && beans[x][y].getIndex() != 0) {//与左边相等
                    beans[x - 1][y].addIndex();
                    beans[x][y].setIndex(0);
                    count++;
                    score += getScore(beans[x - 1][y]);
                }
            }
        }
        upNull(beans);
        return new MoveBean(score + (upNull ? 1 : 0), upNull || count > 0, count);
    }
    /**
     * 向下合并所有空块
     * @param beans
     * @return 
     */
    private synchronized boolean downNull(PointBean[][] beans) {
        boolean moved = false;
        for (int x = Main.SIZE - 2; x >= 0; x--) {
            for (int y = 0; y < Main.SIZE; y++) {
                if (beans[x + 1][y].getIndex() == 0 && beans[x][y].getIndex() != 0) {//左边是空往左边移一个
                    beans[x + 1][y].setIndex(beans[x][y].getIndex());
                    beans[x][y].setIndex(0);
                    moved = true;
                }
            }
        }
        if (moved) {
            downNull(beans);
        }
        return moved;
    }
    /**
     * 向下合并所有相同块
     * @param beans
     * @return 
     */
    public synchronized MoveBean down(PointBean[][] beans) {
        boolean downNull = downNull(beans);
        int count = 0;
        int score = 1;
        for (int y = 0; y < Main.SIZE; y++) {
            for (int x = Main.SIZE - 2; x >= 0; x--) {
                if (beans[x][y].getIndex() == beans[x + 1][y].getIndex() && beans[x][y].getIndex() != 0) {//与左边相等
                    beans[x + 1][y].addIndex();
                    beans[x][y].setIndex(0);
                    count++;
                    score += getScore(beans[x + 1][y]);
                }
            }
        }
        downNull(beans);
        return new MoveBean(score + (downNull ? 1 : 0), downNull || count > 0, count);
    }

    private int getScore(PointBean bean) {
        return (int) (bean.getIndex()>Main.SIZE/2?bean.getValue()*bean.getValue():bean.getValue());
    }
    /**
     * 随机列出下一个块
     * @param beans
     * @return 
     */
    public synchronized boolean random(PointBean[][] beans) {
        int value = 0;
        while (value == 0) {//随机数大于0.3出2小于等于0.3出4
            value = Math.random() > 0.3 ? 1 : 2;
        }
        List<PointBean> set = new ArrayList<PointBean>();
        for (int x = 0; x < Main.SIZE; x++) {
            for (int y = 0; y < Main.SIZE; y++) {
                if (beans[x][y].getIndex() == 0) {
                    set.add(beans[x][y]);
                }
            }
        }
        if (set.isEmpty()) {
            return false;
        }
        int index = ((Double) Math.floor(Math.random() * set.size())).intValue();
        set.get(index).setIndex(value);
        return true;
    }

}

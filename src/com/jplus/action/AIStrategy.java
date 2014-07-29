/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jplus.action;

import com.jplus.bean.PointBean;
import com.jplus.forms.Main;
import static com.jplus.forms.MainFrame.painter;
import static com.jplus.forms.MainFrame.pointBeans;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AI策略
 * @author hyberbin
 */
public class AIStrategy extends Thread {

    public static int SLEEP = 100;
    public static boolean PAUSE = true;
    private final static int MOVE_DOWN = 1;
    private final static int MOVE_LEFT = 2;
    private final static int MOVE_RIGHT = 3;
    private final static int MOVE_UP = 4;
    private final static PointBeanManager manager = new PointBeanManager();

    @Override
    public synchronized void run() {
        while (true) {
            while (PAUSE);
            int max = Integer.MIN_VALUE;
            int type = 0, type1 = 0, type3 = 0, type4 = 0;
            int score1 = Integer.MIN_VALUE, score2 = Integer.MIN_VALUE;
            for (int i = 1; i <= 4; i++) {//朝四个方向尝试得出最高分
                PointBean[][] copy = manager.copy(pointBeans);
                int score = getScore(i, copy);
                if (score > score1) {
                    score1 = score;
                    type = i;
                }
                for (int j = 1; j <= 4; j++) {//再朝四个方向尝试得出最高分
                    int score3 = getScore(j, copy);
                    if (score3 > score2) {
                        score2 = score3;
                        type1 = j;
                    }
                    if (score2 + score1 > max) {
                        max = score1 + score2;
                        type3 = type;
                        type4 = type1;
                    }
                }

            }
            doNext(type3);//按最高分操作
        }
    }

    /**
     * 获取上，下，左，右四个方向操作得分
     * @param square
     * @return
     */
    private synchronized int getScore(int type, PointBean[][] copy) {
        int score = 0;
        switch (type) {
            case MOVE_DOWN:
                score = manager.down(copy).getScore();
                break;
            case MOVE_LEFT:
                score = manager.left(copy).getScore();
                break;
            case MOVE_RIGHT:
                score = (int) (manager.right(copy).getScore() * 0.9);
                break;
            case MOVE_UP:
                score = (int) (manager.up(copy).getScore() * 0.9);
                break;
        }
        return score + getLs(copy);
    }
    /**
     * 获取周围分，如果一个块被都比它大的块包围则减分
     * @param beans
     * @param max
     * @return 
     */
    private int getRoundScore(PointBean[][] beans, int max) {
        int score = 1;
        for (int y = 0; y < Main.SIZE; y++) {
            for (int x = Main.SIZE - 2; x >= 0; x--) {
                int flag = 0;
                int temp=1;
                PointBean bean = beans[x][y];
                if (bean.getX() == 0 || beans[bean.getX() - 1][bean.getY()].getIndex() != 0) {
                    flag++;
                    temp+=(bean.getX() == 0?max:beans[bean.getX() - 1][bean.getY()].getValue());
                }
                if (bean.getX() == Main.SIZE - 1 || beans[bean.getX() + 1][bean.getY()].getIndex() != Main.SIZE - 1) {
                    flag++;
                    temp+=(bean.getX() == Main.SIZE - 1?max:beans[bean.getX() + 1][bean.getY()].getValue());
                }
                if (bean.getY() == 0 || beans[bean.getX()][bean.getY() - 1].getIndex() != 0) {
                    flag++;
                    temp+=(bean.getY() == 0?max:beans[bean.getX()][bean.getY() - 1].getValue());
                }
                if (bean.getY() == Main.SIZE - 1 || beans[bean.getX()][bean.getY() + 1].getIndex() != Main.SIZE - 1) {
                    flag++;
                    temp+=(bean.getY() == Main.SIZE - 1?max:beans[bean.getX()][bean.getY()+1].getValue());
                }
                score+=flag==4?temp/max:0;
            }
        }
        return score;
    }
    /**
     * 获取各个块的有序离散度
     * @param pointBeans
     * @return 
     */
    private int getLs(PointBean[][] pointBeans) {
        int ls = 0;
        float cx =0, cy = Main.SIZE;
        PointBean[] maxValues = getMaxValues(pointBeans);
        ls += (Math.pow(maxValues[0].getX() * 2 - cx, 2) + Math.pow(maxValues[0].getY() * 2 - cy, 2)) * maxValues[0].getValue();//最大值在顶点
        for (int i = 0; i < maxValues.length - 1 && i < Main.SIZE; i++) {
            ls -= (Math.pow(maxValues[i].getX() * 2 - maxValues[i + 1].getX() * 2, 2) + Math.pow(maxValues[i].getY() * 2 - maxValues[i + 1].getY() * 2, 2)) * Math.abs(maxValues[i + 1].getIndex()- maxValues[i].getIndex())*2;//相邻大小的块在一起
        }
        return ls-getRoundScore(pointBeans, maxValues[maxValues.length/2].getIndex());
    }
    /**
     * 将块按从大到小排序
     * @param pointBeans
     * @return 
     */
    private PointBean[] getMaxValues(PointBean[][] pointBeans) {
        List<PointBean> list = new ArrayList<PointBean>();
        for (int i = 0; i < Main.SIZE; i++) {
            for (int j = 0; j < Main.SIZE; j++) {
                if (pointBeans[i][j].getValue() != 0) {
                    list.add(pointBeans[i][j]);
                }
            }
        }
        Collections.sort(list);
        return list.toArray(new PointBean[]{});
    }    
    /**
     * 真正做下一步操作
     * @param type
     * @return 
     */
    private synchronized boolean doNext(int type) {
        sleepTime();
        DO_NEXT:
        switch (type) {
            case MOVE_DOWN:
                manager.down(pointBeans);
                break;
            case MOVE_LEFT:
                manager.left(pointBeans);
                break;
            case MOVE_RIGHT:
                manager.right(pointBeans);
                break;
            case MOVE_UP:
                manager.up(pointBeans);
                break;
        }
        boolean random = manager.random(pointBeans);
        if (!random) {
            PAUSE = false;
        }
        painter.drawAll(false);
        sleepTime();
        return true;
    }

    private void sleepTime() {
        try {
            sleep(SLEEP);
        } catch (InterruptedException ex) {
        }
    }
}

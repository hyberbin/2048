/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jplus.action;

import com.jplus.bean.PointBean;
import com.jplus.forms.Main;
import com.jplus.forms.MainFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * 画板工具
 * @author hyberbin
 */
public class BaseGraphic extends Canvas {

    /** 图像 */
    private Graphics mainGraph;
    private final static PointBeanManager manager = new PointBeanManager();
    private PointBean[][] lastBeanses=manager.getDefaultBeans();
   
    

    public BaseGraphic() {
    }

    /**
     * 画图函数
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        if (mainGraph == null) {
            mainGraph = this.getGraphics();
        }
         drawAll(true);
    }


    /**
     * 重新绘制画板
     * @param ini
     */
    public synchronized void drawAll(boolean ini) {
        for (int i = 0; i < Main.SIZE; i++) {
            for (int j = 0; j < Main.SIZE; j++) {
                if(MainFrame.pointBeans[i][j].getIndex()!=lastBeanses[i][j].getIndex()||ini){
                    if(lastBeanses[i][j].getIndex()*MainFrame.pointBeans[i][j].getIndex()==0)fill(getWhite(i, j));
                    fill(MainFrame.pointBeans[i][j]);
                }
            }
        }
        lastBeanses=manager.copy(MainFrame.pointBeans);
    }

    /**
     * 数字与几何图形转化
     * @param p 单个块
     * @param center 中心块位置
     */
    private synchronized void fill(PointBean p) {
        int y = (p.getX()) * PointBean.size;
        int x = (p.getY()) * PointBean.size;
        mainGraph.setColor(p.getColor());
        mainGraph.fillRect(x + 1, y + 1, PointBean.size-2, PointBean.size-2);
        if(p.getIndex()>0){
            mainGraph.setColor(new Color(255-p.getColor().getRed(), 255-p.getColor().getGreen(), 255-p.getColor().getBlue()));
            mainGraph.setFont(new Font("宋体",Font.BOLD,PointBean.FONT_SIZE));
            mainGraph.drawString(p.getSValue(), getX(p), getY(p));
        }
    }
    private int getX(PointBean p){
        int x = (p.getY()) * PointBean.size;
        int fm=4-p.getSValue().length();
        if(fm==0){
            return  x;
        }else{
            return x +PointBean.size/2-PointBean.FONT_SIZE/(4-p.getSValue().length());
        }
    }
    private int getY(PointBean p){
        int y = (p.getX()) * PointBean.size;
        return y +PointBean.size/2+PointBean.FONT_SIZE/2;
    }

    /** *
     * 清除画板
     */
    public synchronized void clear() {
        if (mainGraph == null) {
            return;
        }
        mainGraph.setColor(PointBean.LINE_GROUND);
        mainGraph.fillRect(0, 0, Main.SIZE * PointBean.size, Main.SIZE * PointBean.size);
        System.out.println("clear");
    }
    
     private static PointBean getWhite(int x,int y){
         return new PointBean(x, y, 0);
     }

    
}

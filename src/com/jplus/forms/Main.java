/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jplus.forms;

import com.jplus.action.AIStrategy;

/**
 *
 * @author hyberbin
 */
public class Main {

    public static int SIZE = 5;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            if (args.length == 1) {
                SIZE = Integer.parseInt(args[0]);
            }
            if (args.length == 2) {
                SIZE = Integer.parseInt(args[0]);
                AIStrategy.SLEEP = Integer.parseInt(args[1]);
            }
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}

package com.cys.tankwar;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TankClient extends Frame {

    Tank myTank = new Tank(1000, 1000, true, Direction.STOP, this);

    //敌军坦克合集
    List<Tank> tanks = new ArrayList<>();

    //爆炸效果集合
    List<Explode> explodes = new ArrayList<>();

    //子弹集合
    List<Missile> missileList = new ArrayList<>();

    //墙的集合
    Wall w1 = new Wall(1200, 200, 20, 150, this), w2 = new Wall(500, 800, 300, 20, this);

    //血包
    Blood blood=new Blood();

    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;

    Image offScreenImage = null;

    @Override
    public void paint(Graphics g) {

        if(tanks.size()<=0){
            for (int i = 0; i < 10; i++) {
                tanks.add(new Tank(50 + 100 * (i + 10), 50+100*i, false, Direction.D, this));
            }
        }


        myTank.draw(g);

        System.out.println(missileList.size());
        //取出每一枚炮弹
        for (int i = 0; i < missileList.size(); i++) {
            Missile m = missileList.get(i);
            m.hitTanks(tanks); //干掉敌方坦克
            m.hitTank(myTank); //敌方击毙我方坦克

            m.hitWall(w1); //防止子弹穿墙
            m.hitWall(w2);

            m.draw(g);
        }

        //装入爆炸
        for (int i = 0; i < explodes.size(); i++) {
            Explode explode = explodes.get(i);
            explode.draw(g);
        }

        //画出每辆敌军坦克
        for (int i = 0; i < tanks.size(); i++) {
            Tank tank = tanks.get(i);
            tank.collidesWithWall(w1);
            tank.collidesWithWall(w2);
            tank.collidesWithTanks(tanks);
            tank.draw(g);
        }

        //画出墙壁
        w1.draw(g);
        w2.draw(g);
        myTank.eat(blood);
        blood.draw(g);
    }

    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }

        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.black);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);

        g.drawImage(offScreenImage, 0, 0, null);
    }

    /**
     * 画出主窗口
     */
    public void launchFrame() {

        for (int i = 0; i < 10; i++) {
            tanks.add(new Tank(50 + 100 * (i + 10), 50+100*i, false, Direction.D, this));
        }
        this.setLocation(400, 300);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setTitle("TankWar");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setResizable(false);
        this.setBackground(Color.black);
        this.setVisible(true);
        this.addKeyListener(new KeyMonitor());

        new Thread(new PaintThread()).start();
    }


    private class PaintThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }


    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.launchFrame();
    }
}

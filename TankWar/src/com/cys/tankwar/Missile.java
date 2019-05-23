package com.cys.tankwar;

import java.awt.*;
import java.util.List;

public class Missile {

    public static final int XSPEED = 60;
    public static final int YSPEED = 60;
    public static final int WIDTH = 20;  //子弹宽度
    public static final int HEIGHT = 20; //子弹高度
    private TankClient tc;

    private boolean good; //判断子弹是敌方还是我方的
    private boolean live = true;

    public boolean isLive() {
        return live;
    }

    int x, y;

    Direction dir;

    public Missile(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Missile(int x, int y, boolean good, Direction dir, TankClient tc) {
        this(x, y, dir);
        this.good = good;
        this.tc = tc;
    }

    //把子弹画出来
    public void draw(Graphics g) {

        if (!live) {
            tc.missileList.remove(this);
            return;
        }
        Color c = g.getColor();
        if(good) g.setColor(Color.RED);
        else     g.setColor(Color.blue);

        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);

        move();
    }

    private void move() {

        switch (dir) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= XSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case STOP:
                break;
        }

        if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
            tc.missileList.remove(this);
        }
    }

    /**
     * @return 碰撞检测的辅助类Rectangle
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    /**
     * 碰撞检测
     *
     * @param t
     * @return
     */
    public boolean hitTank(Tank t) {

        if (this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {

            //
            if (t.isGood()) {
                t.setLife(t.getLife() - 20);
                if (t.getLife() <= 0) t.setLive(false);
            } else {
                t.setLive(false);
            }

            this.live = false;
            Explode e = new Explode(x, y, tc);
            tc.explodes.add(e);
            return true;
        }
        return false;
    }

    /**
     * 打掉一群坦克
     *
     * @param tanks
     * @return
     */
    public boolean hitTanks(List<Tank> tanks) {

        for (int i = 0; i < tanks.size(); i++) {
            if (hitTank(tanks.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean hitWall(Wall w) {
        if (this.live && this.getRect().intersects(w.getRect())) {
            this.live = false;
            return true;
        }
        return false;
    }
}

package com.cys.tankwar;

import java.awt.*;

/**
 * 可以挡子弹的墙
 */
public class Wall {

    int x, y, w, h; //坐标 长宽

    TankClient tc;

    public Wall(int x, int y, int w, int h, TankClient tc) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.PINK);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }

    //碰撞检测
    public Rectangle getRect() {
        return new Rectangle(x, y, w, h);
    }
}

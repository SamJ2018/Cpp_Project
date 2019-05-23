package com.cys.tankwar;

import java.awt.*;

/**
 * @Author: sam
 * @Description: 添加血包，吃了可以加血
 **/
public class Blood {

    int x, y, w, h;
    TankClient tc;
    int step = 0;
    private boolean live = true;

    private int[][] pos = {
            {350, 300}, {360, 300}, {975, 275}, {400, 200}, {760, 540}, {660, 1080}, {380, 990}
    };

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isLive() {
        return live;
    }

    public void draw(Graphics g) {
        if (!live) return;
        Color c = g.getColor();
        g.setColor(Color.magenta);
        g.fillRect(x, y, w, h);
        g.setColor(c);

        move();
    }

    public Blood() {
        x = pos[0][0];
        y = pos[0][1];
        w = h = 30;
    }


    private void move() {
        step++;

        if (step == pos.length) {
            step = 0;
        }

        x = pos[step][0];
        y = pos[step][1];
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, w, h);
    }
}

package com.cys.tankwar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;


/**
 * 碰撞检测
 * 怎么追踪我方坦克？
 * <p>
 * 添加多辆坦克
 * 1、用容器来装敌人的坦克
 * 2、向容器中装入多辆敌人的坦克
 * 3、画出各个坦克
 * 4、更改hitTanks 能打一些列的tank  TankClient里每一发子弹都可以打tanks
 */

/**
 * 功能3 使敌军坦克更智能
 * 1、让敌军坦克动起来：构造函数指定方向，创建敌军坦克时指定敌军坦克的方向
 * 2、让敌军坦克向随机的方向移动：添加随机数产生器(Random) move完成后，如果是敌军坦克，随机产生一个数，来设定下一个方向(Direction.values)
 * 3、让敌军坦克向随机方向移动随机的步骤：添加变量，记录随机的步骤  当x==0时改变方向， 否则随机步骤递减
 * 4、让敌军坦克发射炮弹：本军坦克不打本军，炮弹根据敌我画出不一样的颜色。-> 修改炮弹的构造方法、修改Tank的fire方法、修改hitTank方法，好不能打好，坏不能打坏
 * 5、敌军火力不能太猛
 */
public class Tank {

    public static final int XSPEED = 30; //x -移动速度
    public static final int YSPEED = 30; //y -移动速度
    public static final int WIDTH = 50;  //坦克宽度
    public static final int HEIGHT = 50; //坦克高度


    private boolean live = true; //判断坦克是否活着
    private boolean bL = false, bU = false, bR = false, bD = false; //判断上下左右按键是否按下
    private boolean good; //判断是否敌机
    private static Random r = new Random(); //坦克移动随机位置
    private int step = r.nextInt(12) + 3;

    private Direction dir = Direction.STOP; //当前位置
    private Direction ptDir = Direction.D;  //炮筒方向
    private TankClient tc;
    private int x, y;
    private int oldX, oldY;
    private int life = 100;
    private BloodBar bloodBar = new BloodBar();


    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.good = good;
    }

    public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
        this(x, y, good);
        this.tc = tc;
        this.dir = dir;
    }

    public boolean isGood() {
        return good;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isLive() {
        return live;
    }

    /**
     * 画子弹操作
     *
     * @param g
     */
    public void draw(Graphics g) {

        //如果被子弹击中就不画了
        if (!live) {
            //敌方坦克被子弹击中，移除
            if (!good) {
                tc.tanks.remove(this);
            }
            return;
        }

        Color c = g.getColor();

        if (good)
            g.setColor(Color.red);
        else
            g.setColor(Color.green);
        //画圆
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);

        if (good) bloodBar.draw(g);

        switch (ptDir) {
            case L:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT / 2);
                break;
            case LU:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y);
                break;
            case U:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y);
                break;
            case RU:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y);
                break;
            case R:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT / 2);
                break;
            case RD:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT);
                break;
            case D:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y + Tank.HEIGHT);
                break;
            case LD:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT);
                break;
        }
        move();
    }

    void move() {

        //记录上一次位置
        this.oldX = x;
        this.oldY = y;

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
        if (this.dir != Direction.STOP) {
            this.ptDir = this.dir;
        }

        //防止出界
        if (x < 0) x = 0;
        if (y < 45) y = 45;
        if (x + Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
        if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEIGHT;

        if (!good) {

            Direction[] dirs = Direction.values();

            if (step == 0) {
                step = r.nextInt(12) + 3;
                int rn = r.nextInt(dirs.length);
                dir = dirs[rn];
            }

            step--;

            if (r.nextInt(40) > 30) this.fire();
        }
    }

    /**
     * 发射炮弹方法
     */
    public void fire() {
        if (!live) return;
        int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile m = new Missile(x, y, good, ptDir, this.tc);
        tc.missileList.add(m);
    }

    public Missile fire(Direction dir) {
        if (!live) return null;
        int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile m = new Missile(x, y, good, dir, this.tc);
        tc.missileList.add(m);

        return m;
    }

    /**
     * 按键e摁下的操作
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_F2:
                if (!this.live) {
                    this.live = true;
                    this.life = 100;
                }
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                bL = true;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                bD = true;
                break;
        }
        locateDirection();
    }

    /**
     * 按键抬起操作
     *
     * @param e
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_SPACE:
                fire();
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                bL = false;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                bD = false;
                break;
            case KeyEvent.VK_Q:
                superFire();
                break;
        }
        locateDirection();
    }

    public void locateDirection() {
        if (bL && !bU && !bR && !bD) dir = Direction.L;
        else if (bL && bU && !bR && !bD) dir = Direction.LU;
        else if (!bL && bU && !bR && !bD) dir = Direction.U;
        else if (!bL && bU && bR && !bD) dir = Direction.RU;
        else if (!bL && !bU && bR && !bD) dir = Direction.R;
        else if (!bL && !bU && bR && bD) dir = Direction.RD;
        else if (!bL && !bU && !bR && bD) dir = Direction.D;
        else if (bL && !bU && !bR && bD) dir = Direction.LD;
        else if (!bL && !bU && !bR && !bD) dir = Direction.STOP;
    }

    /**
     * @return 碰撞检测的辅助类Rectangle
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public boolean collidesWithWall(Wall w) {
        if (this.live && this.getRect().intersects(w.getRect())) {
            this.stay();
            return true;
        }
        return false;
    }

    /**
     * 撞墙后可继续行驶
     */
    private void stay() {
        x = oldX;
        y = oldY;
    }

    public boolean collidesWithTanks(List<Tank> tanks) {

        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);
            if (this != t) {
                if (this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
                    this.stay();
                    t.stay();
                    return true;
                }
            }
        }
        return false;
    }

    private void superFire() {

        Direction[] dirs = Direction.values();

        for (int i = 0; i < 8; i++) {
            tc.missileList.add(fire(dirs[i]));
        }
    }


    private class BloodBar {

        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.RED);
            //空心血条
            g.drawRect(x, y - 30, WIDTH, 20);

            //实心方块宽度
            int w = WIDTH * life / 100;
            g.fillRect(x, y - 30, w, 20);
            g.setColor(c);
        }
    }

    public boolean eat(Blood b) {
        if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
            this.life = 100;
            b.setLive(false);
            return true;
        }
        return false;
    }
}


package com.cys.tankwar;

import java.awt.*;

/**
 * 爆炸功能：
 * 1、用不同直径的圆模拟爆炸
 * 2、加入live  加入位置属性  加入draw方法
 * 3、爆炸应该存入集合类  ->   TankClient加入集合  将集合中的爆炸逐一画出(死去的就去除)
 * 4、击毙一辆坦克后产生爆炸->   hitTank时产生爆炸， 不能直接访问爆炸集合  加入TankClient引用(Missle类)
 * 当new Missle时应该指定在哪里画出来(哪个TankClient)
 * 修改Tank类的fire方法，当new Missle时将保存好的TankClient变量传递给Missle
 */
public class Explode {

    int x, y;
    private boolean live = true;
    private TankClient tc;

    private static Toolkit tk = Toolkit.getDefaultToolkit();

  /*  private static Image[] imgs = {
            tk.getImage(Explode.class.getClassLoader().getResource("images/o.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))

    };*/

    int[] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};
    int step = 0;  //现在画到第几步

    public Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        if (!live) {
            //将爆炸移除
            tc.explodes.remove(this);
            return;
        }

        if (step == diameter.length) {
            live = false;
            step = 0;
            return;
        }

        Color c = g.getColor();
        g.setColor(Color.orange);
        g.fillOval(x, y, diameter[step], diameter[step]);
        g.setColor(c);

        step++;
    }
}

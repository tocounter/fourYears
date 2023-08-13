package cn.jxust.market;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PicPanel extends JPanel{
    Toolkit tool;
    Image logo;
    public PicPanel() {
        tool=getToolkit();
        logo=tool.getImage("image/a.jpg");
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logo,0,0, getWidth(), getHeight(), this);
        g.setFont(new Font("Courier",Font.BOLD,20));
        g.setColor(Color.black);
        g.drawString("理工超市注册登录", 600, 40);
    }
}

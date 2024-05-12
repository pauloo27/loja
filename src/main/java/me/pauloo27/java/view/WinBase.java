package me.pauloo27.java.view;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Font;

public class WinBase extends JFrame {

    private static Font titleFont = new Font("Serif", Font.BOLD, 24);
    private JLabel title;

    public WinBase(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setupComponents();
    }

    public void setTitle(String title) {
        super.setTitle(title);
        this.title.setText(title);
    }

    public void addAtOn(Container parent, JComponent comp, int x, int y) {
        Dimension size = comp.getPreferredSize();
        parent.add(comp);
        comp.setBounds(x, y, size.width, size.height);
    }

    public void addAt(JComponent comp, int x, int y) {
        this.addAtOn(this, comp, x, y);
    }

    public void addAtOn(Container parent, JComponent comp, int x, int y, int width, int height) {
        parent.add(comp);
        comp.setBounds(x, y, width, height);
    }

    public void addAt(JComponent comp, int x, int y, int width, int height) {
        this.addAtOn(this, comp, x, y, width, height);
    }

    public void setupComponents() {
        this.title = new JLabel(this.getTitle());
        title.setFont(titleFont);
        this.addAt(title, 10, 10, 300, 40);
    }
}

package com.skanderj.gingerbread;

import java.awt.Color;
import java.awt.Graphics;

import com.skanderj.gingerbread.core.Game;

public final class Gingerbread extends Game {
    public static final String IDENTIFIER = "gingerbread", TITLE = "Gingerbread";
    public static final int OGX = 0, OGY = 0, WIDTH = 600, HEIGHT = Gingerbread.WIDTH, BUFFERS = 2;
    public static final double RATE = 30.0;

    public Gingerbread() {
        super(Gingerbread.IDENTIFIER, Gingerbread.RATE, Gingerbread.TITLE, Gingerbread.WIDTH, Gingerbread.HEIGHT, Gingerbread.BUFFERS);
    }

    @Override
    public void update(double delta) {
        // TODO
        boolean closeRequested = this.window.isCloseRequested();
        if (closeRequested) {
            this.stop();
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(Gingerbread.OGX, Gingerbread.OGY, Gingerbread.WIDTH, Gingerbread.HEIGHT);
    }

    public static void main(String[] args) {
        Gingerbread gingerbread = new Gingerbread();
        gingerbread.start();
    }
}
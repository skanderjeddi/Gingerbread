package com.skanderj.gingerbread.core;

import java.awt.image.BufferStrategy;
import java.awt.Graphics;

import com.skanderj.gingerbread.SimpleThread;
import com.skanderj.gingerbread.display.Window;
import com.skanderj.gingerbread.input.Keyboard;
import com.skanderj.gingerbread.input.Mouse;

public abstract class Process extends SimpleThread {
	public static final int DEFAULT_SIZE = 400, DEFAULT_BUFFERS = 2;

	protected double rate;
	protected Window window;
	protected Keyboard keyboard;
	protected Mouse mouse;

	public Process(String identifier, double rate) {
		this(identifier, rate, identifier, Process.DEFAULT_SIZE, Process.DEFAULT_SIZE, Process.DEFAULT_BUFFERS);
	}

	public Process(String identifier, double rate, String title, int width, int height, int buffers) {
		super(identifier);
		this.rate = rate;
		this.window = new Window(this, title, width, height, buffers);
		this.keyboard = new Keyboard();
		this.mouse = new Mouse();
	}

	@Override
	protected void create() {
		this.window.registerKeyboard(this.keyboard);
		this.window.registerMouse(this.mouse);
		this.window.show();
	}

	@Override
	protected void destroy() {
		this.window.hide();
	}

	@Override
	protected void loop() {
		long startTime = System.nanoTime();
		double nanosecondsPerTick = 1000000000D / this.rate;
		int frames = 0;
		int updates = 0;
		long resetTime = System.currentTimeMillis();
		double delta = 0.0D;
		while (this.isRunning) {
			long endTime = System.nanoTime();
			delta += (endTime - startTime) / nanosecondsPerTick;
			startTime = endTime;
			boolean shouldRender = false;
			while (delta >= 1) {
				updates++;
				this.update(delta);
				delta -= 1;
				shouldRender = true;
			}
			if (shouldRender) {
				frames++;
				BufferStrategy bufferStrategy = this.window.getBufferStrategy();
				Graphics graphics = bufferStrategy.getDrawGraphics();
				this.render(graphics);
				graphics.dispose();
				bufferStrategy.show();
			}
			if ((System.currentTimeMillis() - resetTime) >= 1000) {
				resetTime += 1000;
				System.out.printf("LSU %d - LSF %d\n", updates, frames);
				frames = 0;
				updates = 0;
			}
		}
	}

	/**
	 * Updates game logic
	 *
	 * @param delta the delaya between the current update and last update
	 */
	public abstract void update(double delta);

	/**
	 * Renders the game
	 *
	 * @param graphics used to draw the screen
	 */
	public abstract void render(Graphics graphics);

	public final void updateKeyboard() {
		this.keyboard.update();
	}

	public final void updateMouse() {
		this.mouse.update();
	}

	public static final float mapf(float value, float valueMin, float valueMax, float targetMin, float targetMax) {
		return ((value - valueMin) / (valueMax - valueMin)) * (targetMax - targetMin) + targetMin;
	}
}

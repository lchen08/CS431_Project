package GUIPackage.DrawingPackage;

import java.awt.Graphics;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This is a class for a rectangle object.
 *
 * @author Lisa Chen
 * @since July 17, 2018
 */
public class Rectangle
{
	private int x;
	private int y;
	private int width;
	private int height;

	/**
	 * Constructs a rectangle with position location, height, and width.
	 * @param x The rectangle's top left corner  x-axis position
	 * @param y The rectangle's top left corner  y-axis position
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 */
	public Rectangle( int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Draws the rectangle with a x-y position, width, and height.
	 * @param g For drawing the ring.
	 */
	public void drawRectangle(Graphics g)
	{
		g.drawRect(x, y, width, height);
	}
}
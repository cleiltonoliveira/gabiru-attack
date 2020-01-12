/**
 * @author cleilton
 * File: TileMap.java - Date: Dec 10, 2019
 */
package tileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import init.GamePanel;

public class TileMap {

	// position
	private double x; // x position of the map
	private double y; // y position of the map

	// bounds
	private int xmin; // minimum x value for the map
	private int ymin; // minimum y value for the map
	private int xmax; // maximum x value for the map
	private int ymax; // maximum y value for the map

	private double tween; // // variable to smoothen the tile map movement

	// map
	private int[][] map; // map array to store value of each tile
	private int tileSize; // size of the tile
	private int numRows; // number of rows on the map
	private int numCols; // number of colums on the map
	private int width; // width of the map
	private int height; // height of the map

	// tileset
	private BufferedImage tileset;
	private int numTilesAcross; // number of tiles on each row of the tileset
	private Tile[][] tiles; // tiles extracted from tileset to build the map

	// drawing
	private int rowOffset; // off set value rows
	private int colOffset; // off set value for columns
	private int numRowsToDraw;
	private int numColsToDraw;

	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2; // = 240/30+2 = 10
		numColsToDraw = GamePanel.WIDTH / tileSize + 2; // = 320/30+2 = 12
		tween = 0.07;
	}

	public void loadTiles(String s) {

		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));

			numTilesAcross = tileset.getWidth() / tileSize;

			tiles = new Tile[2][numTilesAcross];

			BufferedImage subImage;

			for (int col = 0; col < numTilesAcross; col++) {

				subImage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);

				tiles[0][col] = new Tile(subImage, Tile.NORMAL);
				subImage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);

				tiles[1][col] = new Tile(subImage, Tile.BLOCKED);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadMap(String s) {
		try {

			InputStream in = getClass().getResourceAsStream(s);

			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			numCols = Integer.parseInt(br.readLine()); // number of columns is 107
			numRows = Integer.parseInt(br.readLine()); // number of rows is 8

			map = new int[numRows][numCols];

			width = numCols * tileSize; // width = 107 * 30 = 3210
			height = numRows * tileSize; // height = 8 * 30 = 240

			xmin = GamePanel.WIDTH - width; // -2890
			xmax = 0;
			ymin = GamePanel.HEIGHT - height; // 0
			ymax = 0;

			String delims = "\\s+";

			for (int row = 0; row < numRows; row++) {

				String line = br.readLine();
				String[] tokens = line.split(delims);

				for (int col = 0; col < numCols; col++) {

					map[row][col] = Integer.parseInt(tokens[col]);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getTileSize() {
		return tileSize;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}

	public void setTween(double d) {
		tween = d;
	}

	public void setPosition(double x, double y) {

		this.x += (x - this.x) * tween;

		this.y += (y - this.y) * tween;

		fixBounds();

		colOffset = (int) -this.x / tileSize;
		rowOffset = (int) -this.y / tileSize;

	}

	private void fixBounds() {

		if (x < xmin)
			x = xmin; // xmin = -2890
		if (y < ymin)
			y = ymin; // 0
		if (x > xmax)
			x = xmax; // 0
		if (y > ymax)
			y = ymax; // 0
	}

	public void draw(Graphics2D g) {

		for (int row = 0; row < rowOffset + numRowsToDraw; row++) {

			if (row >= numRows) {
				break;
			}

			for (int col = colOffset; col < colOffset + numColsToDraw; col++) {

				if (col >= numCols) {
					break;
				}
				if (map[row][col] == 0) {
					continue;
				}

				int rc = map[row][col]; // get value of the tile in map to draw
				int r = rc / numTilesAcross; // row of the tile in tileset / the result value will be 0 or 1
				int c = rc % numTilesAcross; // column of the tile in tileset

				g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);

			}

		}

	}

}
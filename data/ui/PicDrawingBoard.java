package be.alexandreliebh.picacademy.data.ui;

import java.awt.Point;

import be.alexandreliebh.picacademy.data.PicConstants;

public final class PicDrawingBoard {

	private final int sizeX = PicConstants.SURFACE_SIZE_X_PIX/PicConstants.UNIT_SIZE;
	private final int sizeY = PicConstants.SURFACE_SIZE_Y_PIX/PicConstants.UNIT_SIZE;

	private final byte[][] board = new byte[sizeX][sizeY];

	public PicDrawingBoard() {
		this.resetBoard();
	}

	public void setPixel(Point p, PicColor color) {
		this.board[p.x][p.y] = color.getId();
	}

	public void resetBoard() {
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				setPixel(new Point(i, j), PicColor.WHITE);
			}
		}
	}

	public String toString() {
		PicColor[] colors = PicColor.values();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				short color = board[i][j];
				if (color == PicColor.WHITE.getId()) {
					continue;
				}
				builder.append("Coords " + i + ", " + j + " --> " + colors[board[i][j]] + "\n");
			}
		}
		return builder.toString();
	}
}

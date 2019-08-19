package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    /**
     * Adds a hexagon of side length s to a given position in the world.
     * @param world the world to draw on
     * @param pos the bottom left coordinate of the hexagon
     * @param s the side length of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position pos, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        addLowerHalf(world, pos, s, t);
        addUpperHalf(world, pos, s, t);
    }

    /**
     * Adds n random hexagons vertically from bottom to up, starting at given position.
     * @param world the world to draw hexagons
     * @param pos the starting position
     * @param s the side length of the hexagon
     * @param n the number of hexagons to draw vertically
     */
    public static void addRandomVerticalHexagons(TETile[][] world, Position pos, int s, int n) {
        for (int k = 0; k < n; k++) {
            TETile[] tiles = new TETile[]{Tileset.MOUNTAIN, Tileset.GRASS, Tileset.TREE, Tileset.FLOWER, Tileset.SAND};
            Random random = new Random();
            TETile randomTile = tiles[random.nextInt(tiles.length)];
            addHexagon(world, new Position(pos.x, pos.y + 2 * k * s), s, randomTile);
        }
    }

    /**
     * Calculates the right bottom corner's starting position of the given hexagon.
     * Starting position for a hexagon means its left bottom position, i.e.
     *                                           +++
     *                                          +++++
     *                                         +++++++
     *                                         +++++++
     *                                          +++++
     *                                           +++
     *                                           ^
     * @param pos the starting position of the given hexagon
     * @param s the side length of the given hexagon
     * @return the right bottom corner's starting position
     */
    private static Position rightBottomCorner(Position pos, int s) {
        return new Position(pos.x + 2 * s - 1, pos.y - s);
    }

    /**
     * Calculates the right upper corner's starting position of the given hexagon.
     * @param pos the starting position of the given hexagon
     * @param s the side length of the given hexagon
     * @return the right upper corner's starting position
     */
    private static Position rightUpperCorner(Position pos, int s) {
        return new Position(pos.x + 2 * s - 1, pos.y + s);
    }

    /**
     * Adds the lower half of the hexagon.
     * @param world
     * @param pos
     * @param s
     * @param t
     */
    private static void addLowerHalf(TETile[][] world, Position pos, int s, TETile t) {
        for (int k = 0; k < s; k++) {
            addRow(world, pos.x - k, pos.y + k, s + 2 * k, t);
        }
    }

    /**
     * Adds the upper half of the hexagon.
     * @param world
     * @param pos
     * @param s
     * @param t
     */
    private static void addUpperHalf(TETile[][] world, Position pos, int s, TETile t) {
        for (int k = 0; k < s; k++) {
            addRow(world, pos.x - (s - 1) + k, pos.y + s + k, 3 * s - 2 - 2 * k, t);
        }
    }

    /**
     * Adds row of a hexagon.
     * @param world the world to draw on
     * @param x the x coordinate of the row to start
     * @param y the y coordinate of the row to start
     * @param length the length of the row
     * @param t the tile to draw on
     */
    private static void addRow(TETile[][] world, int x, int y, int length, TETile t) {
        for (int k = 0; k < length; k++) {
            world[x+k][y] = TETile.colorVariant(t, 32, 32, 32, new Random());
        }
    }

    private static class Position {
        int x, y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        int width = 60;
        int height = 60;
        ter.initialize(width, height);

        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

//        addHexagon(world, new Position(10, 5), 3, Tileset.MOUNTAIN);
//        addHexagon(world, new Position(20, 5), 4, Tileset.SAND);
//        addHexagon(world, new Position(35, 5), 5, Tileset.FLOWER);

        // Draw hexagons vertically bottom to up, starting from left to right
        // Draw 3, 4, 5, 4, 3 hexagons respectively.
        Position columnBottomPos = new Position(15, 25);  // columnBottomPos is the starting position every time to draw vertically
        addRandomVerticalHexagons(world, columnBottomPos,3,3);    // draw left part
        columnBottomPos = rightBottomCorner(columnBottomPos, 3);
        addRandomVerticalHexagons(world, columnBottomPos, 3, 4);  // draw left part
        columnBottomPos = rightBottomCorner(columnBottomPos, 3);
        addRandomVerticalHexagons(world, columnBottomPos, 3, 5);  // draw middle
        columnBottomPos = rightUpperCorner(columnBottomPos, 3);
        addRandomVerticalHexagons(world, columnBottomPos, 3, 4);  // draw right part
        columnBottomPos = rightUpperCorner(columnBottomPos, 3);
        addRandomVerticalHexagons(world, columnBottomPos, 3, 3);  // draw right part

        ter.renderFrame(world);
    }




}

package io.bloeckchengrafik.heldenprojekt.world;

import java.util.ArrayList;

public class World {
    private final int width;
    private final int height;
    private final int[][] tiles;
    private final ArrayList<EvilCastle> evilCastles = new ArrayList<>();
    private final Healer healer;

    public World(String file) {
        // Read file

        // The File is a matrix of one-width tokens
        // Width and height can be implied by counting columns and rows

        String[] lines = file.replace("\r", "").split("\n");
        String[][] tokens = new String[lines.length][];

        for (int i = 0; i < lines.length; i++) {
            tokens[i] = lines[i].split("");
        }

        this.width = tokens[0].length;
        this.height = tokens.length;

        this.tiles = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = height-1; y >= 0; y--) {
                tiles[x][y] = parseToken(tokens[y][x]);
            }
        }

        evilCastles.add(new EvilCastle(3, 35, 37));
        evilCastles.add(new EvilCastle(4, 39, 42));
        evilCastles.add(new EvilCastle(4, 31, 44));
        evilCastles.add(new EvilCastle(5, 38, 48));

        healer = new Healer(38,22);
    }

    private int parseToken(String token) {
//                "BLUE": "o",
//                "BLACK": "x",
//                "YELLOW": ".",
//                "BROWN": "=",
//                "LIGHTGREEN": "#",
//                "GREEN": "+",

        switch (token) {
            case "x":
                return 1;
            case ".":
                return 2;
            case "=":
                return 3;
            case "#":
                return 4;
            case "+":
                return 5;
            default:
                return 0;
        }
    }

    public int[][] getTiles() {
        return this.tiles;
    }

    public ArrayList<EvilCastle> getEvilCastles() {
        return this.evilCastles;
    }

    public Healer getHealer() {
        return this.healer;
    }
}

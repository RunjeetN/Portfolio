package byow.Core;


import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;


import static java.lang.System.out;


public class Engine {

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    public static final int CANVASHEIGHT = HEIGHT * 20;
    public static final int CANVASWIDTH = WIDTH * 20;
    Random rand;
    public TETile[][] finalWorldFrame;

    TETile[][] occludedWorldFrame;
    Long seed;
    int startingRoom;
    int totalRooms;
    int limit;
    int absBoundHeight = 2;
    int absBoundWidth = 2;
    int randBoundHeight = 2;
    int randBoundWidth = 2;
    int[] avatar;
    int[] mousePos;

    private final int fourty = 40;
    private final int thirty = 30;
    private final int twenty = 20;
    private final int fifteen = 15;
    private final int ten = 10;
    private final double pointEight = 0.8;
    private List<Integer> wallTiles;
    private Map<Integer, List<Integer>> rooms;

    private List<int[]> maskCoords;

    private KeyboardInputSource keeb;
    String fileSeperator = File.separator;
    String fileName = "byow" + fileSeperator + "Core" + fileSeperator + "save-file.txt";
    TETile t = new TETile('@', Color.GREEN, Color.BLACK,
            "main character vibes", "byow" + fileSeperator + "Core" + fileSeperator
            + "Assets" + fileSeperator + "icons8-purple-man-16.PNG");
    TETile w = new TETile('#', Color.GREEN, Color.BLACK,
            "Wall", "byow" + fileSeperator + "Core" + fileSeperator
            + "Assets" + fileSeperator + "icons8-java-16.PNG");
    TETile f = new TETile('F', Color.GREEN, Color.BLACK,
            "Floor", "byow" + fileSeperator + "Core" + fileSeperator
            + "Assets" + fileSeperator + "icons8-floor-plan-16.PNG");
    TETile n = new TETile('N', Color.GREEN, Color.BLACK,
            "Nothing", "byow" + fileSeperator + "Core" + fileSeperator
            + "Assets" + fileSeperator + "icons8-musicbee-16.PNG");


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        // Set up StdDraw Canvas and other variables
        boolean occlusionOn = true;
        mousePos = ter.getMouse();
        setUpDrawFrame();
        keeb = new KeyboardInputSource();
        String runningString = "";
        drawMenu("(N) New Game   (L) Load Game    (Q) Quit");
        char letter = keeb.getNextKey();
        if (letter == 'N') {
            drawMenu("Input Seed:");
            runningString += letter;
            while (letter != 'S') {
                letter = keeb.getNextKey();
                runningString += letter;
                drawMenu(runningString.substring(1, runningString.length()));
            }
        } else if (letter == 'Q') {
            System.exit(0);
            return;
        } else if (letter == 'L') {
            try {
                Scanner file = new Scanner(new File(fileName));
                if (!file.hasNextLine()) {
                    System.exit(0);
                    return;
                }
                runningString = file.nextLine();
                file.close();
            } catch (FileNotFoundException e) {
                e.toString();
            }

        } else {
            interactWithKeyboard();
        }
        interactWithInputString(runningString);


        while (true) {
            if (occlusionOn) {
                StdDraw.clear();
                occludeWorld(avatar);
            } else {
                ter.renderFrame(finalWorldFrame);
            }
            if(StdDraw.hasNextKeyTyped()){
                letter = keeb.getNextKey();
                runningString += letter;
            }
            //displayText(mouseX.intValue(), mouseY.intValue());
            if (letter == 'W') {
                move('W');
            } else if (letter == 'S') {
                move('S');
            } else if (letter == 'A') {
                move('A');
            } else if (letter == 'D') {
                move('D');
            } else if (letter == 'B') {
                occlusionOn = !occlusionOn;
            } else if (letter == 'Q') {
                if (runningString.charAt(runningString.length() - 2) == ':') {
                    saveAndQuit(runningString.substring(0, runningString.length() - 2));
                    System.exit(0);
                    return;
                }
            }
            letter = '@';
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, running both of these:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        wallTiles = new ArrayList<>();
        startingRoom = xyToId(Math.floorDiv(WIDTH, 2) - 1, Math.floorDiv(HEIGHT, 2) - 1);
        rooms = new HashMap<>();
        int startIndex;
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        makeBlankWorld(finalWorldFrame);
        if (input.equals("") || input == null) {
            return finalWorldFrame;
        }
        // load world
        if (input.charAt(0) == 'L' || input.charAt(0) == 'l') {
            finalWorldFrame = loadGame();
            startIndex = 1;
        } else {
            // make new world
            seed = getSeed(input);
            rand = new Random(seed);
            generateRoom(startingRoom);
            rooms.put(startingRoom, new ArrayList<>());
            limit = rand.nextInt(1) + ten;
            makeWorld(limit);
            connectRooms();
            generateWalls();
            startIndex = seed.toString().length() + 2;
            avatar = idToXY(startingRoom);
        }


        for (int i = startIndex; i < input.length(); i++) {
            if ((input.charAt(i) == 'Q' || input.charAt(i) == 'q') && input.charAt(i - 1) == ':') {
                if (input.charAt(0) == 'l' || input.charAt(0) == 'L') {
                    saveAndQuit(input.substring(1, i - 1));
                } else {
                    saveAndQuit(input.substring(0, i - 1));
                }
            }

            if (input.charAt(i) == 'W' || input.charAt(i) == 'w') {
                move('W');
            } else if (input.charAt(i) == 'S' || input.charAt(i) == 's') {
                move('S');
            } else if (input.charAt(i) == 'A' || input.charAt(i) == 'a') {
                move('A');
            } else if (input.charAt(i) == 'D' || input.charAt(i) == 'd') {
                move('D');
            }
        }

        finalWorldFrame[avatar[0]][avatar[1]] = t;
        ter.initialize(WIDTH, HEIGHT, this);
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    // WORLD SETUP
    private long getSeed(String input) {
        if (input.length() == 0) {
            return 0;
        }
        String typedSeed = "";
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == 'S' || input.charAt(i) == 's') {
                return Long.parseLong(typedSeed);
            }
            typedSeed += input.charAt(i);
        }
        return Long.parseLong(typedSeed);
    }

    private void makeBlankWorld(TETile[][] world) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private int xyToId(int x, int y) {
        return x + y * WIDTH;
    }

    private int[] idToXY(int id) {
        int x = id % WIDTH;
        int y = Math.floorDiv(id - x, WIDTH);
        return new int[]{x, y};
    }

    // STRUCTURE METHODS
    private void generateRoom(int centerPoint) {
        int hH = rand.nextInt(randBoundHeight) + absBoundHeight; // half height
        int hW = rand.nextInt(randBoundWidth) + absBoundWidth; // half width

        int[] cC = idToXY(centerPoint); // center coordinates
        // build floors
        for (int y = cC[1] + hH; y > cC[1] - hH; y--) {
            for (int x = cC[0] - hW; x < cC[0] + hW; x++) {
                finalWorldFrame[x][y] = f;
            }
        }
        // store walls
        int leftWallX = cC[0] - hW - 1;
        int rightWallX = cC[0] + hW;
        int topWallY = cC[1] + hH + 1;
        int btmWallY = cC[1] - hH;

        for (int y = topWallY; y >= btmWallY; y--) {
            wallTiles.add(xyToId(leftWallX, y));
            wallTiles.add(xyToId(rightWallX, y));
        }
        for (int x = leftWallX; x < rightWallX; x++) {
            wallTiles.add(xyToId(x, topWallY));
            wallTiles.add(xyToId(x, btmWallY));
        }
    }

    private void generateHallway(int startID, int endID) {
        int[] startPoint = idToXY(startID);
        int[] endPoint = idToXY(endID);
        int horDist = endPoint[0] - startPoint[0];
        int vertDist = endPoint[1] - startPoint[1];
        int currentX = startPoint[0];
        int currentY = startPoint[1];
        int xStepDirection = 0;
        int yStepDirection = 0;
        //Determine direction to step in horizontally
        if (horDist > 0) {
            xStepDirection = 1;
        }
        if (horDist < 0) {
            xStepDirection = -1;
        }
        //Determine direction to step in vertically
        if (vertDist > 0) {
            yStepDirection = 1;
        }
        if (vertDist < 0) {
            yStepDirection = -1;
        }
        //Step in the correct direction for the desired horizontal distance
        wallTiles.add(xyToId(currentX, currentY - 1));
        wallTiles.add(xyToId(currentX, currentY - 2));
        for (int i = 0; i < Math.abs(horDist); i++) {
            currentX = currentX + xStepDirection;
            finalWorldFrame[currentX][currentY] = f;
            wallTiles.add(xyToId(currentX, currentY + 1));
            wallTiles.add(xyToId(currentX, currentY - 1));
        }
        wallTiles.add(xyToId(currentX, currentY + 1));
        wallTiles.add(xyToId(currentX, currentY + 2));
        wallTiles.add(xyToId(currentX, currentY + 3));
        wallTiles.add(xyToId(currentX - 1, currentY + 1));
        wallTiles.add(xyToId(currentX - 1, currentY + 2));
        wallTiles.add(xyToId(currentX - 1, currentY));
        wallTiles.add(xyToId(currentX + 1, currentY));
        wallTiles.add(xyToId(currentX + 1, currentY + 2));
        wallTiles.add(xyToId(currentX + 1, currentY + 1));


        //Step in the correct direction for the desired vertical distance
        for (int j = 0; j < Math.abs(vertDist); j++) {
            currentY = currentY + yStepDirection;
            finalWorldFrame[currentX][currentY] = f;
            wallTiles.add(xyToId(currentX + 1, currentY));
            wallTiles.add(xyToId(currentX - 1, currentY));
        }
    }

    private int generateCenterPoint() {
        //generate x and y that are removed from the border by the maximum room size
        int x = rand.nextInt(randBoundWidth + absBoundWidth + 1,
                WIDTH - randBoundWidth - absBoundWidth - 1);
        int y = rand.nextInt(randBoundHeight + absBoundHeight + 1,
                HEIGHT - randBoundHeight - absBoundHeight - 1);
        return xyToId(x, y);

    }

    private void connectRooms() {
        for (int key : rooms.keySet()) {
            for (int elem : rooms.get(key)) {
                generateHallway(key, elem);
            }
        }
    }

    private int createNeighbors(int sourceRoom) {
        int numOfNeighbors = rand.nextInt(2) + 1;
        for (int i = 0; i < numOfNeighbors; i++) {
            int cP = generateCenterPoint();
            rooms.get(sourceRoom).add(cP);
            rooms.put(cP, new ArrayList<>());
            generateRoom(cP);
        }
        return numOfNeighbors;
    }

    private void makeWorld(int l) {
        if (l <= 0) {
            return;
        }
        Set<Integer> keySet = new TreeSet<>();
        for (int key : rooms.keySet()) {
            keySet.add(key);
        }
        for (int key : keySet) {
            if (rooms.get(key).isEmpty()) {
                makeWorld(l - createNeighbors(key));
            }
        }
    }

    private void generateWalls() {
        for (int tile : wallTiles) {
            int[] tileCoords = idToXY(tile);
            int x = tileCoords[0];
            int y = tileCoords[1];
            if (finalWorldFrame[x][y].equals(Tileset.NOTHING)) {
                finalWorldFrame[x][y] = w;
            }
        }
    }
    private void move(char direction) {
        if (direction == 'W') {
            updatePosition(avatar, new int[]{avatar[0], avatar[1] + 1});
        }
        if (direction == 'S') {
            updatePosition(avatar, new int[]{avatar[0], avatar[1] - 1});
        }
        if (direction == 'A') {
            updatePosition(avatar, new int[]{avatar[0] - 1, avatar[1]});
        }
        if (direction == 'D') {
            updatePosition(avatar, new int[]{avatar[0] + 1, avatar[1]});
        }
    }

    private void updatePosition(int[] currentPos, int[] newPos) {
        if (finalWorldFrame[newPos[0]][newPos[1]] != w) {
            finalWorldFrame[currentPos[0]][currentPos[1]] = f;
            finalWorldFrame[newPos[0]][newPos[1]] = t;
            avatar = newPos;


        }
    }
    public void setUpDrawFrame() {
        StdDraw.setCanvasSize(CANVASWIDTH, CANVASHEIGHT);
        Font font = new Font("Monaco", Font.BOLD, thirty);
        Font font2 = new Font("Monaco", Font.BOLD, ten);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, CANVASWIDTH);
        StdDraw.setYscale(0, CANVASHEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void drawMenu(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, thirty);
        StdDraw.setFont(fontBig);
        StdDraw.text(CANVASWIDTH / 2, CANVASHEIGHT / 2, s);
        StdDraw.show();
    }



    private TETile getTile(int x, int y) {
        return finalWorldFrame[x][y];
    }

    //WRITING TO A FILE

    //Doesn't quit yet, only saves
    private void saveAndQuit(String runningString) {
        try {
            FileWriter out = new FileWriter(fileName);
            out.write(runningString);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TETile[][] loadGame() {
        try {
            Scanner file = new Scanner(new File(fileName));
            String savedLine = file.nextLine();
            file.close();
            if (savedLine == null) {
                return null;
            }
            if (!savedLine.toString().equals("")) {
                String fileContents = savedLine;
                seed = getSeed(fileContents);
                return interactWithInputString(savedLine);
            }
            return null;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private void newGame(String runningString) {
        interactWithInputString(runningString);
    }

    private void occludeWorld(int[] avatarPos) {
        occludedWorldFrame = new TETile[WIDTH][HEIGHT];
        makeBlankWorld(occludedWorldFrame);
        // set avatar position

        List<int[]> mCoords = new ArrayList<>();
        //Arbitrary mask shape
        mCoords.add(new int[]{avatarPos[0] + 1, avatarPos[1]});
        mCoords.add(new int[]{avatarPos[0] + 1, avatarPos[1] + 1});
        mCoords.add(new int[]{avatarPos[0] - 1, avatarPos[1]});
        mCoords.add(new int[]{avatarPos[0] - 1, avatarPos[1] + 1});
        mCoords.add(new int[]{avatarPos[0], avatarPos[1]});
        mCoords.add(new int[]{avatarPos[0] + 1, avatarPos[1] - 1});
        mCoords.add(new int[]{avatarPos[0], avatarPos[1] - 1});
        mCoords.add(new int[]{avatarPos[0] - 1, avatarPos[1] - 1});
        mCoords.add(new int[]{avatarPos[0], avatarPos[1] - 2});
        mCoords.add(new int[]{avatarPos[0], avatarPos[1] + 2});
        mCoords.add(new int[]{avatarPos[0] + 2, avatarPos[1]});
        mCoords.add(new int[]{avatarPos[0] - 2, avatarPos[1]});
        mCoords.add(new int[]{avatarPos[0], avatarPos[1] + 1});


        for (int[] coord : mCoords) {
            maskCoordConverter(coord);
        }
        ter.renderFrame(occludedWorldFrame);
    }

    private void maskCoordConverter(int[] inputCoord) {
        occludedWorldFrame[inputCoord[0]][inputCoord[1]] = finalWorldFrame[inputCoord[0]][inputCoord[1]];
    }
}
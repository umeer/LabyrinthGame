package javagame;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameController extends Observable {

    private Map gameMap, gameMapSaved;
    private int userKey, userKeySaved;
    private Random rand;

    public GameController() {
        rand = new Random();
    }

    public void gameInitialization() {
        userKey = 0;

        //map creation happens here, and this is the fist basic map
        int[][] blockPosition, blockValues;
        blockPosition = new int[][]{
            {BlockType.USER.ordinal(), 0, 0, 0, 0, 0, 0, 0, 0, 4},
            {0, 0, 0, 4, 0, 0, 0, 0, 0, 4},
            {0, 0, 0, 0, 0, 0, 0, 0, 4, 4},
            {0, 0, 2, 0, 4, 0, 0, 4, 4, 4},
            {0, 4, 0, 4, 4, 0, 0, 0, 0, 4},
            {0, 0, 0, 0, 0, 0, 0, 4, 0, 4},
            {0, 0, 0, 0, 0, 0, 4, 4, 0, 4},
            {3, 4, 0, 0, 0, 0, 4, 3, 0, 4},
            {3, 4, 0, 0, 0, 0, 4, 0, 4, 4},
            {2, 4, 0, 0, 0, 6, 4, 0, 0, BlockType.END.ordinal()}
        };

        blockValues = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 5, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 20, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {20, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        gameMap = new Map(blockPosition, blockValues);
        saveGame();
    }

    public void saveGame() {
        gameMapSaved = new Map();
        for (int i = 0; i < 10; i++) {
            gameMapSaved.blockPosition[i] = Arrays.copyOf(gameMap.blockPosition[i], gameMap.blockPosition[i].length);
            gameMapSaved.blockValues[i] = Arrays.copyOf(gameMap.blockValues[i], gameMap.blockValues[i].length);
        }
        userKeySaved = userKey;
    }

    public void reloadGame() {
         gameMap = new Map();
        for (int i = 0; i < 10; i++) {
            gameMap.blockPosition[i] = Arrays.copyOf(gameMapSaved.blockPosition[i], gameMapSaved.blockPosition[i].length);
            gameMap.blockValues[i] = Arrays.copyOf(gameMapSaved.blockValues[i], gameMapSaved.blockValues[i].length);
        }
        userKey = userKeySaved;
    }

    public void mapMutationAlgorithm() {
        int oriVariation = rand.nextInt(10);
        int variation = oriVariation;
        ArrayList<Integer> valuesLogs = new ArrayList<Integer>();
      
        for (int i = 0; i < variation; i++) {

            int xPos = rand.nextInt(8) + 1;
            int yPos = rand.nextInt(8) + 1;
            int size = rand.nextInt(5) + 5;

            if (gameMap.blockPosition[xPos][yPos] == BlockType.KEY.ordinal()) {
                variation++;
            } else {
                gameMap.blockPosition[xPos][yPos] = BlockType.KEY.ordinal();
                gameMap.blockValues[xPos][yPos] = size;
                valuesLogs.add(size);
            }
        }

        variation = oriVariation;

        for (int i = 0; i < variation; i++) {

            int xPos = rand.nextInt(8) + 1;
            int yPos = rand.nextInt(8) + 1;

            if (gameMap.blockPosition[xPos][yPos] == BlockType.KEY.ordinal()) {
                variation++;
            } else {
                gameMap.blockPosition[xPos][yPos] = BlockType.DOOR.ordinal();
                gameMap.blockValues[xPos][yPos] = valuesLogs.get(0);
                valuesLogs.remove(0);
            }
        }
        saveGame();
    }

    public Map getGameMap() {
        return gameMap;
    }

    public int getUserKeys() {
        return userKey;
    }

    public boolean moveCharachter(int direction) {

        int i = 0, j = 0;
        int newxPos = 0, newyPos = 0;

        if (direction == KeyEvent.VK_UP) {
            outerloop:
            for (i = 0; i < 10; i++) {
                for (j = 0; j < 10; j++) {
                    if (gameMap.blockPosition[i][j] == BlockType.USER.ordinal()) {
                        newxPos = i - 1;
                        newyPos = j;
                        break outerloop;
                    }
                }
            }
        } else if (direction == KeyEvent.VK_LEFT) {
            outerloop:
            for (i = 0; i < 10; i++) {
                for (j = 0; j < 10; j++) {
                    if (gameMap.blockPosition[i][j] == BlockType.USER.ordinal()) {
                        newxPos = i;
                        newyPos = j - 1;
                        break outerloop;
                    }
                }
            }
        } else if (direction == KeyEvent.VK_DOWN) {
            outerloop:
            for (i = 0; i < 10; i++) {
                for (j = 0; j < 10; j++) {
                    if (gameMap.blockPosition[i][j] == BlockType.USER.ordinal()) {
                        newxPos = i + 1;
                        newyPos = j;
                        break outerloop;
                    }
                }
            }
        } else if (direction == KeyEvent.VK_RIGHT) {
            outerloop:
            for (i = 0; i < 10; i++) {
                for (j = 0; j < 10; j++) {
                    if (gameMap.blockPosition[i][j] == BlockType.USER.ordinal()) {
                        newxPos = i;
                        newyPos = j + 1;
                        break outerloop;
                    }
                }
            }
        } else {
            return false;
        }

        if (checkPosition(newxPos, newyPos)) {
            gameMap.blockPosition[i][j] = BlockType.EMPTY.ordinal();
            gameMap.blockPosition[newxPos][newyPos] = BlockType.USER.ordinal();
            return true;
        }
        return false;
    }

    private boolean checkPosition(int xPos, int yPos) {

        //end game checker
        if (xPos == 9 && yPos == 9) {
            playSound();
            if (isExtraToBeCollected()) {
                createSystemMessage(SystemMessage.GAME_COMPLETED_NO_EXTRA);
            } else {
                createSystemMessage(SystemMessage.GAME_COMPLETED);
            }
            gameInitialization();
            mapMutationAlgorithm();
            return false;
        }

        //boundary limiter
        if ((xPos >= 0 && xPos <= 9) && (yPos >= 0 && yPos <= 9)) {

            if (gameMap.blockPosition[xPos][yPos] == BlockType.WALL.ordinal()) {
                return false;
            } else if (gameMap.blockPosition[xPos][yPos] == BlockType.KEY.ordinal()) {
                playSound();
                userKey = gameMap.blockValues[xPos][yPos];
            } else if (gameMap.blockPosition[xPos][yPos] == BlockType.DOOR.ordinal()) {
                if (gameMap.blockValues[xPos][yPos] == userKey) {
                    playSound();
                    //userKeys = userKeys - gameMap.blockValues[xPos][yPos];
                } else {
                    createSystemMessage(SystemMessage.WRONG_KEY);
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }

    private boolean isExtraToBeCollected() {
        //If the extra is on the map, you need to collect it before finishing
        Boolean isItPresentOnTheMap = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameMap.blockPosition[i][j] == BlockType.EXTRA.ordinal()) {
                    isItPresentOnTheMap = true;
                }
            }
        }

        return isItPresentOnTheMap;
    }

    private void playSound() {
        java.awt.Toolkit.getDefaultToolkit().beep();
    }

    private void createSystemMessage(SystemMessage message) {
        setChanged();
        notifyObservers(message);
    }

}

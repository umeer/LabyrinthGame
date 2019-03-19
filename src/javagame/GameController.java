
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JOptionPane;

public class GameController {
    
    private Component messageFrame;
    private Map gameMap;
    private int userKeys;
    private Random rand;
    
    public GameController() {
        rand = new Random();
    }
    
    public void gameInitialization() {
        userKeys = 0;

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
            {0, 4, 0, 0, 0, 0, 4, 0, 4, 4},
            {2, 4, 0, 0, 0, 6, 4, 0, 0, BlockType.END.ordinal()}
        };
        
        blockValues = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 10, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 20, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {15, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        
        gameMap = new Map(blockPosition, blockValues);
    }
    
    public void mapMutationAlgorithm() {
        int oriVariation = rand.nextInt(5);
        int variation = oriVariation;
        
        for (int i = 0; i < variation; i++) {
            
            int xPos = rand.nextInt(8) + 1;
            int yPos = rand.nextInt(8) + 1;
            int size = rand.nextInt(16) + 5;
                        
            if (gameMap.blockPosition[xPos][yPos] == BlockType.KEY.ordinal()) {
                variation++;
            } else {
                gameMap.blockPosition[xPos][yPos] = BlockType.KEY.ordinal();
                gameMap.blockValues[xPos][yPos] = size;
            }
        }

        variation = oriVariation;
        
        for (int i = 0; i < variation; i++) {

            int xPos = rand.nextInt(8) + 1;
            int yPos = rand.nextInt(8) + 1;
            int size = rand.nextInt(16) + 5;

            if (gameMap.blockPosition[xPos][yPos] == BlockType.KEY.ordinal()) {
                variation++;
            } else {
                gameMap.blockPosition[xPos][yPos] = BlockType.DOOR.ordinal();
                gameMap.blockValues[xPos][yPos] = size;
            }
        }
    }
    
    public Map getGameMap() {
        return gameMap;
    }
    
    public int getUserKeys() {
        return userKeys;
    }
    
    private boolean checkPosition(int xPos, int yPos) {

        //end game checker
        if (xPos == 9 && yPos == 9) {
            playSound();
            if (isExtraToBeCollected()) {
                JOptionPane.showMessageDialog(messageFrame, "Congratulations you found the exit but you left the extra inside.", "Game Completed!!!", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(messageFrame, "Congratulations you found the exit.", "Game Completed!!!", JOptionPane.WARNING_MESSAGE);
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
                userKeys = userKeys + gameMap.blockValues[xPos][yPos];
            } else if (gameMap.blockPosition[xPos][yPos] == BlockType.DOOR.ordinal()) {
                if (gameMap.blockValues[xPos][yPos] <= userKeys) {
                    playSound();
                    userKeys = userKeys - gameMap.blockValues[xPos][yPos];
                } else {
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
    
    public void moveCharachter(int direction) {
        
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
        }
        
        if (checkPosition(newxPos, newyPos)) {
            gameMap.blockPosition[i][j] = BlockType.EMPTY.ordinal();
            gameMap.blockPosition[newxPos][newyPos] = BlockType.USER.ordinal();
        }
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

//This is the Default map used in some of the tests below
//   blockPosition = new int[][]{
//            {BlockType.USER.ordinal(), 0, 0, 0, 0, 0, 0, 0, 0, 4},
//            {0, 0, 0, 4, 0, 0, 0, 0, 0, 4},
//            {0, 0, 0, 0, 0, 0, 0, 0, 4, 4},
//            {0, 0, 2, 0, 4, 0, 0, 4, 4, 4},
//            {0, 4, 0, 4, 4, 0, 0, 0, 0, 4},
//            {0, 0, 0, 0, 0, 0, 0, 4, 0, 4},
//            {0, 0, 0, 0, 0, 0, 4, 4, 0, 4},
//            {3, 4, 0, 0, 0, 0, 4, 3, 0, 4},
//            {3, 4, 0, 0, 0, 0, 4, 0, 4, 4},
//            {2, 4, 0, 0, 0, 6, 4, 0, 0, BlockType.END.ordinal()}
//        };
//
//        blockValues = new int[][]{
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 5, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {5, 0, 0, 0, 0, 0, 0, 20, 0, 0},
//            {5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {20, 0, 0, 0, 0, 0, 0, 0, 0, 0}
//        };

public class GameTests {

    public GameTests() {
    }

    @Test
    public void testAssets() {
        StandardBlock block;
        try {
            block = new StandardBlock(BlockType.DOOR);
            block = new StandardBlock(BlockType.EMPTY);
            block = new StandardBlock(BlockType.END);
            block = new StandardBlock(BlockType.EXTRA);
            block = new StandardBlock(BlockType.KEY);
            block = new StandardBlock(BlockType.USER);
            block = new StandardBlock(BlockType.WALL);
        } catch (Exception ex) {
            Assert.fail("Exception " + ex);
        }
    }

    @Test
    public void testINITConsole() {
        GameConsole console = new GameConsole();
        assertEquals(true, true);
    }

    @Test
    public void testINITController() {
        GameController controller = new GameController();
        assertEquals(true, true);
    }

    @Test
    public void movementLogicTestRightAndLeft() {
        GameController controller = new GameController();
        controller.gameInitialization();

        Map gameMap = controller.getGameMap();
        assertEquals(gameMap.blockPosition[0][0], BlockType.USER.ordinal());

        controller.moveCharachter(KeyEvent.VK_RIGHT);
        assertEquals(gameMap.blockPosition[0][1], BlockType.USER.ordinal());

        controller.moveCharachter(KeyEvent.VK_LEFT);
        assertEquals(gameMap.blockPosition[0][0], BlockType.USER.ordinal());
    }

    @Test
    public void movementLogicTestUpAndDown() {
        GameController controller = new GameController();
        controller.gameInitialization();

        Map gameMap = controller.getGameMap();
        assertEquals(gameMap.blockPosition[0][0], BlockType.USER.ordinal());

        controller.moveCharachter(KeyEvent.VK_DOWN);
        assertEquals(gameMap.blockPosition[1][0], BlockType.USER.ordinal());

        controller.moveCharachter(KeyEvent.VK_UP);
        assertEquals(gameMap.blockPosition[0][0], BlockType.USER.ordinal());
    }

    @Test
    public void antiWallCrossTest() { //in the default map the user starts in the top left corner hence can't go  up or left
        GameController controller = new GameController();
        controller.gameInitialization();

        Map gameMap = controller.getGameMap();
        assertEquals(gameMap.blockPosition[0][0], BlockType.USER.ordinal());

        controller.moveCharachter(KeyEvent.VK_UP);
        assertEquals(gameMap.blockPosition[0][0], BlockType.USER.ordinal());

        controller.moveCharachter(KeyEvent.VK_LEFT);
        assertEquals(gameMap.blockPosition[0][0], BlockType.USER.ordinal());
    }
    @Test
    public void gameSaveTest() {
        GameController controller = new GameController();
        controller.gameInitialization();
        controller.saveGame();
        controller.moveCharachter(KeyEvent.VK_DOWN);
        controller.moveCharachter(KeyEvent.VK_RIGHT);
        controller.moveCharachter(KeyEvent.VK_RIGHT);
        controller.moveCharachter(KeyEvent.VK_DOWN);
        controller.moveCharachter(KeyEvent.VK_RIGHT);
        controller.reloadGame();

        Map gameMap = controller.getGameMap();
        assertEquals(gameMap.blockPosition[0][0], BlockType.USER.ordinal());
    }

    @Test
    public void wallCollisionTest() { //test based on the default map
        GameController controller = new GameController();
        controller.gameInitialization();
        controller.moveCharachter(KeyEvent.VK_RIGHT);
        controller.moveCharachter(KeyEvent.VK_RIGHT);
        controller.moveCharachter(KeyEvent.VK_RIGHT);
        controller.moveCharachter(KeyEvent.VK_DOWN);//here it should not cross the wall

        Map gameMap = controller.getGameMap();
        assertEquals(gameMap.blockPosition[0][3], BlockType.USER.ordinal());
    }

    @Test
    public void keyPickUPTest() { //test based on the default map
        GameController controller = new GameController();
        controller.gameInitialization();
        assertEquals(controller.getUserKeys(), 0);

        controller.moveCharachter(KeyEvent.VK_RIGHT);
        controller.moveCharachter(KeyEvent.VK_RIGHT);
        controller.moveCharachter(KeyEvent.VK_DOWN);
        controller.moveCharachter(KeyEvent.VK_DOWN);
        controller.moveCharachter(KeyEvent.VK_DOWN);

        assertEquals(controller.getUserKeys(), 5);

        Map gameMap = controller.getGameMap();
        assertEquals(gameMap.blockPosition[3][2], BlockType.USER.ordinal());
    }

}


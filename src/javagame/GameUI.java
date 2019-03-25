package javagame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

public class GameUI extends JPanel implements KeyListener, ActionListener, Observer {

    private Component messageFrame;
    private GameController controller;
    private JPanel MapPanel;

    public GameUI() {
        this.controller = new GameController();
        controller.addObserver(this);

        loadGameStartMenu();
    }

    private void loadGameStartMenu() {
        this.removeAll();

        this.setLayout(new BorderLayout());
        JPanel mainScreen = new JPanel();
        mainScreen.setLayout(new GridBagLayout());
        mainScreen.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));
        this.add(mainScreen, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;

        JLabel label = new JLabel("<html><p style=\"font-size:60px\">Barricade Game</font></html>", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 25));
        label.setForeground(Color.BLACK);
        c.fill = GridBagConstraints.VERTICAL;
        c.ipady = 40;      //make this component tall
        c.weightx = 3;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        mainScreen.add(label, c);

        ImageIcon immagine = new ImageIcon("images/block/user.png");
        JLabel image = new JLabel(new ImageIcon(immagine.getImage().getScaledInstance(350, 350, java.awt.Image.SCALE_SMOOTH)));
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        mainScreen.add(image, c);

        JButton startButton = new JButton();
        startButton.setPreferredSize(new Dimension(300, 50));
        startButton.setText("Start");
        startButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        mainScreen.add(startButton, c);
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadGameInterface();
            }
        });

        this.revalidate();
        this.repaint();
    }

    private void loadGameInterface() {
        this.removeAll();

        this.setLayout(new BorderLayout());
        MapPanel = new JPanel();
        this.add(MapPanel, BorderLayout.CENTER);

        JPanel controllButtonsPanel = new JPanel(new BorderLayout());
        controllButtonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 75, 100));
        JButton restartButton = new JButton();
        restartButton.setPreferredSize(new Dimension(300, 50));
        restartButton.setText("Restart Game");
        controllButtonsPanel.add(restartButton, BorderLayout.WEST);
        restartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controller.reloadGame();
                paintGameMap(controller.getGameMap());
            }
        });
        JButton newMapButton = new JButton();
        newMapButton.setPreferredSize(new Dimension(300, 50));
        newMapButton.setText("Generate New Random Map");
        controllButtonsPanel.add(newMapButton, BorderLayout.EAST);
        newMapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controller.gameInitialization();
                controller.mapMutationAlgorithm();
                paintGameMap(controller.getGameMap());
            }
        });
        this.add(controllButtonsPanel, BorderLayout.SOUTH);

        controller.gameInitialization();
        paintGameMap(controller.getGameMap());

        this.setFocusable(true);
        this.addKeyListener(this);
        this.revalidate();
        this.repaint();
    }

    public void paintGameMap(Map gameMap) { //aggiorna la gui quando necessario(modifica filtri orari o giorni)
        JPanel pannello = new JPanel(new GridLayout(0, 10, -25, 0));
        try {
            pannello.setBackground(Color.BLACK);

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    StandardBlock block;

                    if (gameMap.blockPosition[i][j] == BlockType.EMPTY.ordinal()) {
                        block = new StandardBlock(BlockType.EMPTY);
                    } else if (gameMap.blockPosition[i][j] == BlockType.USER.ordinal()) {
                        block = new StandardBlock(BlockType.USER);
                    } else if (gameMap.blockPosition[i][j] == BlockType.KEY.ordinal()) {
                        block = new NumericBlock(BlockType.KEY, gameMap.blockValues[i][j] + "");
                    } else if (gameMap.blockPosition[i][j] == BlockType.DOOR.ordinal()) {
                        block = new NumericBlock(BlockType.DOOR, gameMap.blockValues[i][j] + "");
                    } else if (gameMap.blockPosition[i][j] == BlockType.WALL.ordinal()) {
                        block = new StandardBlock(BlockType.WALL);
                    } else if (gameMap.blockPosition[i][j] == BlockType.END.ordinal()) {
                        block = new StandardBlock(BlockType.END);
                    } else if (gameMap.blockPosition[i][j] == BlockType.EXTRA.ordinal()) {
                        block = new StandardBlock(BlockType.EXTRA);
                    } else {
                        block = new StandardBlock(BlockType.EMPTY);
                    }

                    pannello.add(block);
                }
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(messageFrame, "Errore while loading images", "Error!!!", JOptionPane.WARNING_MESSAGE);
        }
        MapPanel.removeAll();
        MapPanel.add(pannello);

        JLabel label;
        if (controller.getUserKeys() == 0) {
            label = new JLabel("No Key Collected", JLabel.CENTER);

        } else {
            label = new JLabel("Key Pin: " + controller.getUserKeys(), JLabel.CENTER);
        }
        label.setFont(new Font("Serif", Font.BOLD, 25));
        label.setPreferredSize(new Dimension(250, 300));
        label.setForeground(Color.BLACK);
        MapPanel.add(label);

        MapPanel.revalidate();
        MapPanel.repaint();
        this.requestFocusInWindow();
        this.setFocusable(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (controller.moveCharachter(e.getKeyCode())) {
            paintGameMap(controller.getGameMap());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void update(Observable o, Object arg) {
        SystemMessage systemMessage = (SystemMessage) arg;
        switch (systemMessage) {

            case NULL: {
                break;
            }
            case GAME_COMPLETED: {
                gameCompletedNotificationPopup("Congratulations you found the exit!");
                break;
            }
            case GAME_COMPLETED_NO_EXTRA: {
                gameCompletedNotificationPopup("Congratulations you found the exit but you left the extra inside");
                break;
            }
            case WRONG_KEY: {
                if (controller.getUserKeys() == 0) {
                    JOptionPane.showMessageDialog(messageFrame, "Pick up a key to open the door!", "Game Message:", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(messageFrame, "Wrong key!", "Game Message:", JOptionPane.WARNING_MESSAGE);
                }
                break;
            }
            default: {
            }
        }
    }

    private void gameCompletedNotificationPopup(String message) {
        Object[] options1 = {"Return To Start Page", "Exit"};
        JPanel panel = new JPanel();
        panel.add(new JLabel(message));
        int result = JOptionPane.showOptionDialog(messageFrame, panel, "Game Message:",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        if (result == JOptionPane.YES_OPTION) {
            loadGameStartMenu();
            this.removeKeyListener(this);
        } else {
            System.exit(0);
        }
    }

}

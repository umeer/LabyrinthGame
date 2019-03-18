
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameUI extends JPanel implements KeyListener, ActionListener {

    private Component messageFrame;
    private GameController controller;
    private JPanel mainPanel;

    public GameUI() {
        this.controller = new GameController();

        this.setLayout(new GridLayout(1, 2, 10, 10));
        mainPanel = new JPanel();
        this.add(mainPanel);

        JButton restartButton = new JButton();
        restartButton.setText("Restart");
        this.add(restartButton);
        restartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controller.gameInitialization();
                controller.mapMutationAlgorithm();
                paintGameMap(controller.getGameMap());
            }
        });

        setFocusable(true);
        this.addKeyListener(this);

        controller.gameInitialization();
        paintGameMap(controller.getGameMap());
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

        JLabel label = new JLabel("Keys collected: " + controller.getUserKeys(), JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 25));
        label.setPreferredSize(new Dimension(250, 300));
        label.setForeground(Color.BLACK);

        mainPanel.removeAll();
        mainPanel.add(pannello);
        mainPanel.add(label);
        mainPanel.revalidate();
        mainPanel.repaint();
        this.requestFocusInWindow();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        controller.moveCharachter(e.getKeyCode());

        paintGameMap(controller.getGameMap());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

}

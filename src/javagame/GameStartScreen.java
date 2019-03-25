package javagame;

import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Yoga
 */
public class GameStartScreen extends JPanel {

    public GameStartScreen() {

        //this.setLayout(new GridLayout(1, 2, 10, 10));
        this.setLayout(new BorderLayout());

        JPanel mainScreen = new JPanel();
        mainScreen.setLayout(new GridBagLayout());
        mainScreen.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));
        this.add(mainScreen, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;

        JLabel label = new JLabel("<html><p style=\"font-size:60px\">Barricade Game</font></html>", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 25));
//        label.setPreferredSize(new Dimension(250, 300));
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
        startButton.setSize(20, 40);
        startButton.setText("Start Game");
        startButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        mainScreen.add(startButton, c);
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadGameUI();
            }
        });

    }

    public void loadGameUI() {
        this.removeAll();
        this.add(new GameUI());
        this.revalidate();
        this.repaint();
    }

}

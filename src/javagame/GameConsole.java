package javagame;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameConsole extends JFrame{

    private JPanel display = new JPanel(new BorderLayout());
    private Component frameErrore;

    public GameConsole() {
        draw();
    }

    public void draw() {
        this.setTitle("Game");
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 1200, 850);
        ImageIcon icon = new ImageIcon("images/icon.PNG");
        setIconImage(icon.getImage());

        this.add(display);
        GameUI page = new GameUI();
        display.add(page);
        
    }
        
}
   enum BlockType {

        EMPTY,
        USER,
        KEY,
        DOOR,
        WALL,
        END,
        EXTRA // if the extra is present on the map, before finishing you need to collect it
  }

 enum SystemMessage {

    NULL,
    GAME_COMPLETED,
    GAME_COMPLETED_NO_EXTRA,
    WRONG_KEY
}
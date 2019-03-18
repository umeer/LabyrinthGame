
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

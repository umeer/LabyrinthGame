package javagame;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class StandardBlock extends JButton {

    private String[] imagesLocation = new String[]{"images/block/empty.png", "images/block/user.png", "images/block/key.png", "images/block/door.png", "images/block/wall.png", "images/block/end.png", "images/block/extra.png"};

    public StandardBlock(BlockType typeOfBlock) throws IOException {
 
        setCover(typeOfBlock);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setLayout(new BorderLayout());
    }

    private void setCover(BlockType typeOfBlock) throws IOException {
        ImageIcon seat_free = new ImageIcon(imagesLocation[typeOfBlock.ordinal()]);
        this.setIcon(scaleImage(seat_free, 50, 50));
    }

    private ImageIcon scaleImage(ImageIcon image, int l, int h) {
        return new ImageIcon(image.getImage().getScaledInstance(l, h, java.awt.Image.SCALE_SMOOTH));
    }
}

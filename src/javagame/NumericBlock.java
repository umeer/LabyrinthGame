package javagame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import javax.swing.JLabel;

public class NumericBlock extends StandardBlock{

    public NumericBlock(BlockType typeOfBlock, String text) throws IOException {
        super(typeOfBlock);
        
        if (text.length() > 0) {
           
            JLabel label = new JLabel(text, JLabel.CENTER);
            label.setFont(new Font("Serif", Font.BOLD, 12));
            label.setPreferredSize(new Dimension(22, 22));
            label.setForeground(Color.BLACK);

            this.add(label,BorderLayout.PAGE_START);
        }       
    }
}

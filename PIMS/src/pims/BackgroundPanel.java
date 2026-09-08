package pims;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image bg;

    public BackgroundPanel() {
        setLayout(null);
        try {
            bg = new ImageIcon(getClass().getResource("/pims/images/pharmacyBG.png")).getImage();
        } catch (Exception e) {
            bg = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(240, 244, 242));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
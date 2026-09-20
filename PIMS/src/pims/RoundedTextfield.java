package pims;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextfield extends JTextField {
    public RoundedTextfield() {
        setOpaque(false);
        setBorder(new EmptyBorder(6, 12, 6, 40));
        setFont(new Font("SansSerif", Font.PLAIN, 13));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 12, 12));
        g2.setColor(new Color(210, 210, 210));
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 12, 12));
        g2.dispose();
        super.paintComponent(g);
    }
}
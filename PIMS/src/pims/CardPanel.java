package pims;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CardPanel extends JPanel {
    private final int radius;

    public CardPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // soft drop shadow
        g2.setColor(new Color(0, 0, 0, 25));
        g2.fill(new RoundRectangle2D.Double(4, 6, getWidth() - 8, getHeight() - 8, radius, radius));

        // card itself
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 8, getHeight() - 8, radius, radius));

        g2.dispose();
        super.paintComponent(g);
    }
}
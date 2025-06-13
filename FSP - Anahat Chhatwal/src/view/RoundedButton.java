package view;

// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// RoundedButton is a custom JButton component designed with a modern, rounded appearance.
public class RoundedButton extends JButton {
	
	// Flag to track whether the button is being hovered over
    private boolean hovered = false;

    // Constructor
    public RoundedButton(String text) {
        super("<html><center>" + text + "</center></html>");
        setFont(new Font("Arial", Font.ITALIC, 26));
        setContentAreaFilled(false);
        setOpaque(true);
        setFocusPainted(false);
        setBackground(Color.decode("#3a5600"));
        setForeground(Color.decode("#e6f2cf"));
        // Hover effect - pop-up
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                setBounds(getX() - 2, getY() - 2, getWidth() + 4, getHeight() + 4);
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                setBounds(getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4);
                repaint();
            }
        });
    }
    // Custom component painting to draw a rounded background behind the button text.
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
        super.paintComponent(g);
        g2.dispose();
    }
    // Custom border rendering to match the rounded style of the button.
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 40, 40);
        g2.dispose();
    }
    // Override isOpaque to allow custom transparency control.
    @Override
    public boolean isOpaque() {
        return false;
    }
}
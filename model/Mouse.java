package model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
	public int x, y;
	public boolean clicked;

    @Override
    public void mouseClicked(MouseEvent e) {
        clicked = true;
        x = e.getX();
        y = e.getY();
    }

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
}

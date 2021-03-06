package layout.view;

import layout.controller.Controller;
import layout.model.Model;

import javax.swing.event.MouseInputListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 * User: Alan P. Sexton
 * Date: 21/06/13
 * Time: 00:52
 */
class CanvasMouseListener implements MouseInputListener

{
    Model model;
    View view;
    Controller controller;

    int x1;
    int y1;
    int x2;
    int y2;
    boolean mouseDown = false;

    public CanvasMouseListener(Model model, View view, Controller controller)
    {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    public void paint(Graphics g)
    {
        if (mouseDown)
        {
            Color col = g.getColor();
            g.setColor(Color.RED);
            if (x1 <= x2)
            {
                if (y1 <= y2)
                    g.drawRect(x1,y1, x2-x1, y2-y1);
                else
                    g.drawRect(x1, y2, x2 - x1, y1 - y2);
            }
            else
            {
                if (y1 <= y2)
                    g.drawRect(x2,y1, x1-x2, y2-y1);
                else
                    g.drawRect(x2,y2, x1-x2, y1-y2);
            }
            g.setColor(col);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (!model.isActive())
            return ;
        x1 = (int) (e.getX() / view.getCanvas().getScale());
        y1 = (int) (e.getY() / view.getCanvas().getScale());
        mouseDown = true;
        view.getCanvas().addMouseMotionListener(this);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (!model.isActive())
            return ;
        view.getCanvas().removeMouseMotionListener(this);
        mouseDown = false;
        x2 = (int) (e.getX() / view.getCanvas().getScale());
        y2 = (int) (e.getY() / view.getCanvas().getScale());
        if (x1 <= x2)
        {
            if (y1 <= y2)
                controller.addRect(new Rectangle(x1, y1, x2 - x1, y2 - y1));
            else
                controller.addRect(new Rectangle(x1, y2, x2 - x1, y1 - y2));
        }
        else
        {
            if (y1 <= y2)
                controller.addRect(new Rectangle(x2, y1, x1 - x2, y2 - y1));
            else
                controller.addRect(new Rectangle(x2, y2, x1 - x2, y1 - y2));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        x2 = (int) (e.getX() / view.getCanvas().getScale());
        y2 = (int) (e.getY() / view.getCanvas().getScale());
        view.getCanvas().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
    }
}

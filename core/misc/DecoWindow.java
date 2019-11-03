/*
The Design Patterns Java Companion

Copyright (C) 1998, by James W. Cooper

IBM Thomas J. Watson Research Center

*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DecoWindow extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;
  JButton Quit;

  public DecoWindow() {
    super("Deco Button");
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    JPanel jp = new JPanel();

    getContentPane().add(jp);
    jp.add(new CoolDecorator(new JButton("Cbutton")));
    jp.add(new SlashDecorator(new CoolDecorator(new JButton("Dbutton"))));
    //jp.add( new CoolDecorator(new JButton("Dbutton")));
    jp.add(Quit = new JButton("Quit"));
    Quit.addActionListener(this);
    setSize(new Dimension(200, 100));

    setVisible(true);
    Quit.requestFocus();
  }

  public void actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  static public void main(String argv[]) {
    new DecoWindow();
  }
}

class Decorator extends JComponent {
  private static final long serialVersionUID = 1L;

  public Decorator(JComponent c) {
    setLayout(new BorderLayout());
    add("Center", c);
  }
}

class SlashDecorator extends Decorator {
  private static final long serialVersionUID = 1L;
  int x1, y1, w1, h1;

  public SlashDecorator(JComponent c) {
    super(c);
  }

  public void setBounds(int x, int y, int w, int h) {
    x1 = x;
    y1 = y;
    w1 = w;
    h1 = h;
    super.setBounds(x, y, w, h);
  }

  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(Color.red);
    g.drawLine(0, 0, w1, h1);
  }

}

class CoolDecorator extends Decorator {
  private static final long serialVersionUID = 1L;

  boolean mouse_over; // true when mose over button

  JComponent thisComp;

  public CoolDecorator(JComponent c) {
    super(c);
    mouse_over = false;
    thisComp = this; //save this component
    //catch mouse movements in inner class
    c.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        mouse_over = true; //set flag when mouse over
        thisComp.repaint();
      }

      public void mouseExited(MouseEvent e) {
        mouse_over = false; //clear flag when mouse not over
        thisComp.repaint();
      }
    });

  }

  //paint the button
  public void paint(Graphics g) {
    super.paint(g); //first draw the parent button
    if (!mouse_over)
    //if the mouse is not over the button
    //erase the borders
    {
      Dimension size = super.getSize();
      g.setColor(Color.lightGray);
      g.drawRect(0, 0, size.width - 1, size.height - 1);
      g.drawLine(size.width - 2, 0, size.width - 2, size.height - 1);
      g.drawLine(0, size.height - 2, size.width - 2, size.height - 2);
    }

  }
}
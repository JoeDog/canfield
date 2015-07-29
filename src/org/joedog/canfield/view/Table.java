package org.joedog.canfield.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.joedog.canfield.game.Card;
import org.joedog.canfield.game.Canfield;
import org.joedog.canfield.control.Game;
import org.joedog.canfield.model.Location;

public class Table extends JPanel {
  private Game   control;
  private Color  green   = new Color(48,200,126);

  public Table(Game control) {
    this.control = control;
    this.setBackground(green);
  }

  @Override
  public void paintComponent(Graphics g) {
    ArrayList<Card>     cards     = (ArrayList<Card>)control.getModelProperty("Cards");
    ArrayList<Location> locations = (ArrayList<Location>)control.getModelProperty("Locations");
    super.paintComponent(g);
    g.setColor(new Color(33,33,33));
    /*for (int i = 0; i < control.getModelIntProperty("LocationCount"); i++) {
      Location tmp = control.getLocation(i);
      g.drawRoundRect(tmp.getX(),tmp.getY(),62,96,10,10);
    }*/
    if (locations != null) {
      for (Location tmp : locations) {
        g.drawRoundRect(tmp.getX(),tmp.getY(),62,96,10,10);
      }
    }
    if (cards != null) {
      for (Card card : cards) {
        g.drawImage(card.getImage(), card.getX(), card.getY(), null);
      }
    }
  }
}


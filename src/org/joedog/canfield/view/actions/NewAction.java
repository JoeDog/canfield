package org.joedog.canfield.view.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

import org.joedog.canfield.control.Game;

public class NewAction implements ActionListener {
  private Game controller;

  public NewAction (Game controller) {
    this.controller = controller;
  }

  public void actionPerformed (ActionEvent e) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        controller.reset();
      }
    });
  }
}


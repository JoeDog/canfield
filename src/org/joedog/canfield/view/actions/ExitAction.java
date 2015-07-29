package org.joedog.canfield.view.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.joedog.canfield.control.Game;

public class ExitAction implements ActionListener {
  private Game controller;

  public ExitAction (Game controller) {
    this.controller = controller;
  }

  public void actionPerformed (ActionEvent e) {
    this.controller.exit();
  }
}


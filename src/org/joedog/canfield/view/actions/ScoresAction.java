package org.joedog.canfield.view.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.joedog.canfield.control.Game;
//import org.joedog.canfield.view.HighScorePanel;

public class ScoresAction implements ActionListener {
  private Game controller;

  public ScoresAction (Game controller) {
    this.controller = controller;
  }

  public void actionPerformed (ActionEvent e) {
    //new HighScorePanel(this.controller);
  }
}


package org.joedog.canfield.view;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;

import org.joedog.util.TextUtils;
import org.joedog.canfield.game.Canfield;
import org.joedog.canfield.control.Game;
import org.joedog.canfield.control.Constants;

public class View extends JDesktopPane implements Viewable {
  private Game      control;
  private int       width;
  private int       height;
  private Table     table;
  private StatusBar sbar;

  public View (Game control) {
    this.control = control;
    this.table   = new Table(control);
    this.sbar    = new StatusBar();
    this.build();
  }

  public void action() {
    table.repaint();
  }

  private void build() {
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx =  0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.weightx = gbc.weighty = 95;
    gbc.insets = new Insets(2, 2, 2, 2);
    this.add(table, gbc);
 
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.weightx = gbc.weighty = 2;
    this.add(sbar, gbc); 
  }   

  public void modelPropertyChange(PropertyChangeEvent e) {
    if (e.getNewValue() == null) return;
    if (e.getPropertyName().equals(Constants.MESSAGE)) {
      this.sbar.setMessage((String)e.getNewValue());
    }
  }

  private class ResetAction extends AbstractAction {
    private Game control;

    public ResetAction(Game control) {
      putValue(NAME, "Reset");
      this.control = control;
    }

    public void actionPerformed(ActionEvent ae) {
      control.reset();
    } 
  }
}

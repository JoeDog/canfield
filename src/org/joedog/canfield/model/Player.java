package org.joedog.canfield.model;

import org.joedog.util.*;
import java.net.URL;

import org.joedog.canfield.game.*;
import org.joedog.canfield.control.Constants;
import org.joedog.canfield.model.AbstractModel;

public class Player extends AbstractModel {
  private String name;

  public Player (String name) { 
    this.name = name;
  }

  public void save() {}
}

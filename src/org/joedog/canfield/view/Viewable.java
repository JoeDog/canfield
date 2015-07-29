package org.joedog.canfield.view;

import java.beans.PropertyChangeEvent;

public interface Viewable {
  public void modelPropertyChange(PropertyChangeEvent evt);
}

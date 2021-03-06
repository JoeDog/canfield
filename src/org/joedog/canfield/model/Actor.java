package org.joedog.canfield.model;

public class Actor {
  protected int      x;
  protected int      y;
  protected int      width; 
  protected int      height;
  private   int      dw;
  private   int      dh;
  private   int      ypad;
  private   Arena    arena    = null; 
  private   Location location = new Location(0, 0);

  public Actor() {
    this(20, 120);
  }

  public Actor(int width, int height) {
    this.width  = width;
    this.dw     = width;
    this.height = height;
    this.dh     = height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void resetWidth() {
    this.width = this.dw;
  }

  public int getWidth() {
    return this.width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void resetHeight() {
    this.height = dh;
  }

  public int getHeight() {
    return this.height;
  }

  public Location getLocation() {
    return this.location;
  }

  public int getX() {
    return this.location.getX();
  }

  public int getY() {
    return this.location.getY();
  }

  public synchronized void setX(int x) {
    this.location.setX(x);
  }

  public synchronized void setY(int y) {
    this.location.setY(y);
  }

  public synchronized void setLocation(int x, int y) {
    this.location.setX(x);
    this.location.setY(y);
  }

  public synchronized void setLocation(Location location) {
    if (location == null) {
      // WTF?
      return;
    }
    this.location.setX(location.getX());
    this.location.setY(location.getY());
  }
}

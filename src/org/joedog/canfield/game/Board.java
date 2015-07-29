package org.joedog.canfield.game;

import java.util.ArrayList;
import javax.swing.event.ListDataListener;

import org.joedog.canfield.model.Location;

public class Board {
  private int    size = 0;
  private ArrayList<Hand> hands = new ArrayList<Hand>();

  public Board (int size) {
    this.size = size;
    for (int i = 0; i < this.size; i++) {
      this.hands.add(i, new Hand());
    }
  }

  public int size() {
    return this.size;
  }

  public void setLayout(int num, Location location,int type) {
    int col = num - 1;
    if (col > size-1 || col < 0) {
      System.err.println("ERROR: column count ("+num+") is out of bounds");
      for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
        System.out.println(ste);
      }
      return;
    }
    this.hands.get(col).setLayout(location, type);
  }

  public void setLayout(int num, Location location) {
    this.setLayout(num, location, Canfield.STACKED);
  }

  public Location getLocation(int num) {
    int col = num - 1;
    if (col > size-1 || col < 0) {
      System.err.println("ERROR: column count ("+num+") is out of bounds");
      for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
        System.out.println(ste);
      }
      return new Location(0,0);
    }
    return this.hands.get(col).getLocation();
  }

  /** 
   * Add a Card to the hand in column 
   * <p>
   * @param  int   The pile number in which we add the card
   * @param  Card  The card to add
   * @return void
   */
  public void add(int num, Card card) {
    int col = num - 1;
    if (col > size-1 || col < 0) {
      System.err.println("ERROR: column count ("+num+" "+col+") is out of bounds "+(this.size-1));
      for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
        System.out.println(ste);
      }
      return;
    }
    this.hands.get(col).addCard(card); 
  }

  public ArrayList getAll() {
    return this.hands;
  } 

  public Hand getHand(int num) {
    int col = num - 1;
    if (col > size-1 || col < 0) {
      System.err.println("ERROR: column count ("+num+") is out of bounds");
      for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
        System.out.println(ste);
      }
      return null; // stupid programmer 
    }
    return this.hands.get(col);
  }

  public ArrayList<Card> getCards() {
    ArrayList<Card> cards = new ArrayList<Card>();
    for (int i = 0; i < this.size; i++) {
      cards.addAll(this.hands.get(i).getCards());
    }
    return cards;
  }
 
  public void addListDataListener(int num, javax.swing.event.ListDataListener l) {
    int col = num - 1;
    if (col > size-1 || col < 0) {
      System.err.println("ERROR: column count ("+num+") is out of bounds");
      for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
        System.out.println(ste);
      }
      return; // stupid programmer 
    }
    this.hands.get(col).addListDataListener(l);
  } 
}

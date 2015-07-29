package org.joedog.canfield.model;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.event.ListDataEvent;

import org.joedog.canfield.control.Constants;
import org.joedog.util.NumberUtils;
import org.joedog.canfield.game.Board;
import org.joedog.canfield.game.Card;
import org.joedog.canfield.game.Canfield;
import org.joedog.canfield.game.Hand;

public class Arena extends AbstractModel {
  private String message;
  private int    status = Canfield.DEAL;
  private Hand   deck;
  private Hand   waste;
  private Hand   stock; 
  private Board  foundation;
  private Board  tableau;
  private static Arena  _instance =  null;
  private static Object mutex     =  new Object();

  private Arena() { 
    this.deck       = new Hand(new Location(570, 60));
    this.waste      = new Hand(new Location(670, 170));
    this.stock      = new Hand(new Location(50, 60));
    this.foundation = new Board(4);
    this.tableau    = new Board(4);

    this.deck.addListDataListener(new HandChangeListener());
    this.waste.addListDataListener(new HandChangeListener());
    this.stock.addListDataListener(new HandChangeListener());
    for (int i = 1; i <= this.foundation.size(); i++) {
      this.foundation.addListDataListener(i, new HandChangeListener());
    }
    for (int i = 1; i <= this.tableau.size(); i++) {
      this.foundation.addListDataListener(i, new HandChangeListener());
    }

    this.foundation.setLayout(1, new Location(150, 15),  Canfield.STACKED);
    this.foundation.setLayout(2, new Location(250, 15),  Canfield.STACKED);
    this.foundation.setLayout(3, new Location(350, 15),  Canfield.STACKED);
    this.foundation.setLayout(4, new Location(450, 15),  Canfield.STACKED);

    this.tableau.setLayout(1, new Location(150, 115), Canfield.VERTICAL);
    this.tableau.setLayout(2, new Location(250, 115), Canfield.VERTICAL);
    this.tableau.setLayout(3, new Location(350, 115), Canfield.VERTICAL);
    this.tableau.setLayout(4, new Location(450, 115), Canfield.VERTICAL);
  }

  public synchronized static Arena getInstance() {
    if (_instance == null) {
      synchronized(mutex) {
        if (_instance == null) {
          _instance = new Arena();
        }
      }
    }
    return _instance;
  }

  public void save() {}

  /**
   * Arena subscribes to status for internal purposes. 
   * It's getter is private
   * <p>
   * @param  String
   * @return
   */
  public void setStatus(String status) {
    int s;
    if (! NumberUtils.isNumeric(status)) {
      this.status = Canfield.DEAL;
      return;
    }

    s = Integer.parseInt(status);
    if (s < Canfield.DEAL || s > Canfield.OVER) {
      this.status = Canfield.DEAL;
      return;
    }
    this.status = Integer.parseInt(status);
  }

  /**
   * Returns the game status as a String
   * <p>
   * @param  none
   * @return String
   */
  public String getStatus() {
    return ""+this.status;
  }

  /**
   * Status the status message and fires a property change
   * <p>
   * @param  String  the status message
   * @return void
   */
  public void setMessage(String message) {
    this.message = message;
    firePropertyChange(Constants.MESSAGE, "message", message);
  }

  /**
   * Returns a status message
   * <p>
   * @param  void
   * @return String  The status message
   */
  public String getMessage() {
    return this.message;
  }

  public void addToDeck(Card card) {
    this.deck.addCard(card);
  }

  public void addToStock(Card card) {
    this.stock.addCard(card);
  }

  public void addToWaste(Card card) {
    this.waste.addCard(card);
  }

  public void addToFoundation(int col, Card card) {
    this.foundation.add(col, card);
  }

  public void addToTableau(int col, Card card) {
    this.tableau.add(col, card);
  }

  
  public synchronized void select(int x, int y) {
    /*for (int key : hands.keySet()) {
      Hand hand = hands.get(key);
      for (Card card : hand.getCards()) {
        if (card.isSelected(x, y)) {
          System.out.println("selected: "+card.toString());
        }
      }
    }*/
    //ArrayList<Card> south = (ArrayList<Card>)((this.getHand(Pinochle.SOUTH)).getHand());
    /*if (this.getStatus() == Canfield.PLAY) {
      for (Card card : south) {
        if (! card.isSelected(x, y)) {
          // we can only select one card at a time in play. 
          // So if this is not the clicked card it goes false
          card.select(false);
        }
      }
    } 
    for (Card card : south) {
      if (card.isSelected(x, y)) {
        card.select(! card.isSelected());
        break;
      }
    }*/
    return;
  }

  /*
   * Returns all the cards that are displayed in the Arena
   * <p>
   * @param  none
   * @return ArrayList
   * @see    java.util.ArrayList
   */
  public ArrayList<Card> getCards() {
    ArrayList<Card> cards = new ArrayList<Card>();
    cards.addAll(this.deck.getCards());
    cards.addAll(this.waste.getCards());
    cards.addAll(this.stock.getCards());
    cards.addAll(this.foundation.getCards());
    cards.addAll(this.tableau.getCards());
    return (cards == null) ? (ArrayList)Collections.EMPTY_LIST : cards;
  }

  public ArrayList<Location> getLocations() {
    ArrayList<Location> locations = new ArrayList<Location>();
    locations.add(this.deck.getLocation());
    locations.add(this.waste.getLocation());
    locations.add(this.stock.getLocation());
    for (int i = 1; i <= this.foundation.size(); i++) {
      locations.add(this.foundation.getLocation(i));
    }
    for (int i = 1; i <= this.tableau.size(); i++) {
      locations.add(this.tableau.getLocation(i));
    }
    return (locations == null) ? (ArrayList)Collections.EMPTY_LIST : locations;
  }

  private class HandChangeListener implements javax.swing.event.ListDataListener {
    public void intervalAdded(ListDataEvent e)   {}

    public void intervalRemoved(ListDataEvent e) {}

    public void contentsChanged(ListDataEvent e) {
      deck.relocate();
      waste.relocate();
      stock.relocate(); 
      for (int i = 1; i <= foundation.size(); i++) {
        foundation.getHand(i).relocate();
      }
      for (int i = 1; i <= tableau.size(); i++) {
        tableau.getHand(i).relocate();
      } 
    }
  }
}

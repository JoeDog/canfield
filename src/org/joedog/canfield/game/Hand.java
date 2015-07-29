package org.joedog.canfield.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.joedog.canfield.model.Location;

public class Hand implements ListModel {
  private ArrayList hand;  
  private ArrayList listeners = new ArrayList();
  private int       fan       = Canfield.STACKED;
  private int       space     = 20; 
  private Location  location  = null;

  /**
   * Create a new empty hand
   * <p>
   * @return Hand
   */
  public Hand() {
    hand = new ArrayList();
  }

  public Hand(Location location) {
    this();
    this.setLayout(location, Canfield.STACKED);
  }

  public Hand(Location location, int type) {
    this();
    this.setLayout(location, type);
  }

  /**
   * Sets the starting location for the Hand and
   * stack the cards with no fan
   * <p>
   * @param  Location  The starting location
   * @return void
   */
  public void setLayout(Location start){
    this.setLayout(start, Canfield.STACKED);
  }

  /**
   * Sets the starting location for the Hand along
   * with its fan
   * <p>
   * @param  Location  The starting location
   * @param  int       How to fan the cards
   * @return void
   */
  public void setLayout(Location start, int fan){
    this.location = start;
    this.fan = fan;
  }

  public Location getLocation() {
    return this.location;
  }

  public void relocate() {
    int i = 0;
    int x = 0; 
    int y = 0; 

    if (this.location == null) return; 

    x = this.location.getX();
    y = this.location.getY();
    Card tmp = null;
    for (Card c: this.getCards()){
      if (this.fan == Canfield.VERTICAL && i > 0)  
        x += 20;
      else if (this.fan == Canfield.HORIZONTAL && i > 0) 
        y += 20;
      c.setLocation(x, y);
      c.resetWidth();
      tmp = c;
      i++;
    }
    if (tmp != null) {
      tmp.setWidth(tmp.getImageWidth());
    }
  }

  /**
   * Set the space in pixels between fanned cards
   * <p>
   * @param  int  The space in pixels between cards
   * @return void
   */
  public void setSpace(int space) {
    this.space = space;
  }

  /**
   * Returns a Card object whose rank and suit match 
   * the parameters
   * <p>
   * @param  int   A card's rank
   * @return int   A card's suit
   */
  public Card getCard(int rank, int suit) {
    for (Card c: this.getCards()){
      if (c.isa(rank, suit)) {
        return c; 
      }
    }
    return null;
  }

  /**
   * Return a List<Card> of all the cards in the Hand
   * <p>
   * @param  none
   * @return List<Card>
   */
  public List<Card> getCards() {
    return hand;
  }

  /**
   * Clear the hand of all cards
   * <p>
   * @param  none
   * @return void
   */
  public void reset() {
    hand.clear();
  }

  /** 
   * Return an ArrayList interpretation of the Hand
   * <p>
   * @param  none
   * @return ArrayList
   * @see    ArrayList
   */
  public ArrayList getHand() {
    return (this.hand == null) ? (ArrayList)Collections.EMPTY_LIST : this.hand;
  }

  /**
   * Add a card to the hand
   * <p>
   * @param  Card   The card to add
   * @return void
   */ 
  public void addCard(Card c) {
    if (c == null)
      throw new NullPointerException("Can't add a null card to a hand.");
    hand.add(c);
    this.relocate();
  }

  /**
   * Return an int which represents Card's postion
   * in the hand; this method returns the position 
   * of the first instance of RANK and SUIT.
   * <p>
   * @param  Card
   * @return int   position in the hand or -1 not found
   */
  public int position (Card card) {
    for (int i = 0; i < hand.size(); i++) {
      Card c = (Card)hand.get(i);
      if (c.matches(card)) {
        return i;  
      } 
    }
    return -1;
  }

  /** 
   * Returns an int which represents Card's position
   * in the hand AT or AFTER mark; a match is based on
   * the first occurance of RANK and SUIT
   * <p>
   * @param Card    the Card we're looking for
   * @param int     position in the deck at which we start looking
   */
  public int position (Card card, int mark) {
    for (int i = mark; i < hand.size(); i++) {
      Card c = (Card)hand.get(i);
      if (c.matches(card)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Return an int which represents how many cards were
   * selected in the hand.
   * <p>
   * @param  none
   * @return int    The total number of selected cards
   */
  public int numSelected() {
    int num = 0;
 
    for (int i = 0; i < hand.size(); i++) {
      Card tmp = (Card)hand.get(i);
      if (tmp.isSelected()) {
            num += 1;
      }
    }
    return num;
  }

  public Card getSelected() {
    for (Card c: this.getCards()){ 
      if (c.isSelected()) {
        return c;
      } 
    }
    return null;
  }

  /**
   * Returns an int which represents how many instances
   * of Card the hand contains
   * <p>
   * @param  Card  The Card to match
   * @return int   The number of instances we've found 
   */
  public int contains (Card card) { 
    int found = 0;
    for (Card c: this.getCards()){ 
      if (c.matches(card)) {
        found ++;
      }
    } 
    return found;
  }

  /**
   * Returns an int which represents the number of 
   * instances of suit discovered in the hand
   * <p>
   * @param  int   The suit we're looking for
   * @return int   The number of instances we've found
   */
  public int contains (int suit) {
    int num = 0;
    for (Card c: this.getCards()) { 
      if (c.getSuit() == suit) {
        num ++;
      }
    } 
    return num;
  }

  public void deselectAll() {
    for (Card card : this.getCards()) {
      card.select(false);
    }
  }

  public Deck getSuit (int suit) {
    Deck deck = new Deck();
    for (Card c: this.getCards()) {
      if (c.isa(suit)) {
        deck.add(c);
      }  
    }  
    return deck;
  }

  public void sort() {
    Comparator cc = Card.getComparator(
      Card.SortParameter.SUIT_ASCENDING,
      Card.SortParameter.RANK_DESCENDING
    );
    Collections.sort(hand, cc);
  }

  public void sortByPosition() {
    Comparator cc = Card.getComparator(
      Card.SortParameter.SUIT_ASCENDING,
      Card.SortParameter.RANK_DESCENDING
    );
    Collections.sort(hand, cc);

  }

  public void display() {
    System.out.println(this.toString());
    System.out.println(" ");
  }
  
  @Override
  public String toString() {
    String str = "";
    Comparator cc = Card.getComparator(
      Card.SortParameter.SUIT_ASCENDING,
      Card.SortParameter.RANK_DESCENDING
    );
    Collections.sort(hand, cc); 
    for (int i = 0; i < hand.size(); i++) { 
      Card c = (Card)hand.get(i);
      str = str + c.toString();
    }
    return str;
  }

  /**
   * Removes a card like Card c
   * <p>
   * @param  Card  Remove a card like this one
   * @return void
   */ 
  public void remove (Card c) { 
    for (Iterator<Card> iterator = this.getCards().iterator(); iterator.hasNext(); ) {
      Card card = iterator.next();
      if (card.matches(c)) {
        iterator.remove();
        this.notifyListeners();
        return; 
      }
    }
    return;
  } 

  /**
   * Removes a card by its numeric ID.
   * <p>
   * @param  int  the id of the card to remove; hand.remove(card.getId());
   * @return void
   */
  public void remove (int id) {
    for (Iterator<Card> iterator = this.getCards().iterator(); iterator.hasNext(); ) {
      Card card = iterator.next();
      if (id == card.getId()) {
        iterator.remove();
        this.notifyListeners();
        return;
      }
    }
    return;
  }

  public void removeAll() {
    for (Iterator<Card> iterator = this.getCards().iterator(); iterator.hasNext(); ) {
      Card card = iterator.next();
      iterator.remove();
    }
    this.notifyListeners();
    return;
  }

  public Card get(int position) {
    if (position < 0 || position >= hand.size())
      throw new IllegalArgumentException("Position does not exist in hand: "+ position);
    return (Card)hand.get(position);
  }

  public int size() {
    return hand.size();
  }

  /**
   * The following methods are in support of the ListModel interface...
   */
  public int getSize() {
    return this.size();
  }

  public Card getElementAt(int index) {
    return this.get(index);
  }

  public void removeListDataListener(javax.swing.event.ListDataListener l) {
    listeners.remove(l);
  }

  public void addListDataListener(javax.swing.event.ListDataListener l) {
    listeners.add(l);
  }

  private void notifyListeners() {
    ListDataEvent le = new ListDataEvent(
      this, ListDataEvent.CONTENTS_CHANGED, 0, this.getSize()
    );
    for (int i = 0; i < listeners.size(); i++) {
      ((ListDataListener)listeners.get(i)).contentsChanged(le);
    }
  }
}



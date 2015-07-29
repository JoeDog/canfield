package org.joedog.canfield.game;

import java.util.Random;

public class Deck extends Pack {
  public final static int EMPTY = 0; 
  public final static int FULL  = 0; 

  static final int ranks[] = {
    Canfield.KING,
    Canfield.QUEEN,
    Canfield.JACK,
    Canfield.TEN,
    Canfield.NINE,
    Canfield.EIGHT,
    Canfield.SEVEN,
    Canfield.SIX,
    Canfield.FIVE,
    Canfield.FOUR,
    Canfield.THREE,
    Canfield.TWO,
    Canfield.ACE
  };
  static final int suits[]  = {
    Canfield.CLUBS,
    Canfield.SPADES,
    Canfield.HEARTS,
    Canfield.DIAMONDS
  };

  /**
   * Creates a new deck of 52 cards.
   * <p>
   * @return Deck
   */
  public Deck() { 
    this.createDeck(FULL);
  }

  /** 
   * Creates a Canfield deck of type FULL or EMPTY
   * <p>
   * @param  int    FULL or EMPTY deck
   * @return Deck
   */
  public Deck (int type) {
    this.createDeck(type);
  }

  private void createDeck(int type) {
    if (type == FULL) {
     int id = 0;
      for (int i = 0; i < ranks.length; i++) {
        for (int j = 0; j < suits.length; j++) {
          add(new Card(ranks[i], suits[j], id++));
        }
      }
    }
  }

  public int contains(Card card) {
    int  count = 0;
    for (Card c: this.getCards()) {
      if (c.matches(card)) count++; 
    }
    return count;
  }

  public int contains (int suit) {
    int num = 0;
    for (Card c: this.getCards()) {
      if (c.getSuit() == suit) {
        num ++;
      }
    }
    return num;
  }

  public void printIt() {
    System.out.println(this.toString());
  }

  public Card dealCard(int num) {
    return (Card) this.get(num);    
  }

  /**
   * Returns a String interpretation of the Deck
   * <p>
   * @param  none
   * @return String
   */
  public String toString() {
    String cards = null;
    for (Card c: this.getCards()) {
      if (cards==null) {
        cards = Canfield.rank(c.getRank())+Canfield.suit(c.getSuit())+"("+c.getId()+")";
      } else {
        cards = cards+" "+Canfield.rank(c.getRank())+Canfield.suit(c.getSuit())+"("+c.getId()+")";
      }
    }
    return cards;
  }

  /**
   * Places the cards in the deck in random order; while
   * completely unnecessary, this method 'shuffles' the 
   * deck five times just for fun....
   * <p>
   * @param  none
   * @return void
   */
  public void shuffle() {
    int x = 0;
    while (x < 5) { 
      Pack shuffled = new Pack();

      while (this.count() > 0) {
        shuffled.add(remove((int)(count() * java.lang.Math.random())));
      }

      while (shuffled.count() > 0) {
        add(shuffled.remove(0));
      }
      x++;
    } // this outer loop isn't necessary but it's fun....
  }

  public static final int[] randomSet(int n) {
    int na[] = new int[n];
    Random rand = new Random();

    for (int i = 0; i < n; ++i) {
      na[i] = i;
    }
    for (int i = 0; i < n; ++i) {
      swap(na, i, Math.abs(rand.nextInt()) % n);
    }
    return na;
  }

  private static final void swap(int[] na, int a, int b) {
    int tmp;
    tmp   = na[a];
    na[a] = na[b];
    na[b] = tmp;
  }
}


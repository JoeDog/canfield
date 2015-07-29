package org.joedog.canfield.game;

public final class Canfield {
  public final static int HEARTS     = 0;
  public final static int CLUBS      = 1;
  public final static int DIAMONDS   = 2;
  public final static int SPADES     = 3;

  public final static int KING       = 13;
  public final static int QUEEN      = 12;
  public final static int JACK       = 11;
  public final static int TEN        = 10;
  public final static int NINE       = 9;
  public final static int EIGHT      = 8;
  public final static int SEVEN      = 7;
  public final static int SIX        = 6;
  public final static int FIVE       = 5;
  public final static int FOUR       = 4;
  public final static int THREE      = 3;
  public final static int TWO        = 2;
  public final static int ACE        = 1;

  public final static int DEAL       = 0;  // Deal the cards
  public final static int PLAY       = 1;  // Play the hand
  public final static int SCORE      = 2;  // Score the hand 
  public final static int OVER       = 3;  // GAME OVER

  public final static int STACKED    = 0;  // Do not fan the cards
  public final static int HORIZONTAL = 1;  // Fan the cards horizontally
  public final static int VERTICAL   = 2;  // Fan the card vertically

  public final static String suit(int suit) {
    switch(suit) {
      case CLUBS:
        return "C";
      case SPADES:
        return "S";
      case HEARTS:
        return "H";
      case DIAMONDS:
        return "D";
    }
    return null;
  }

  public final static String suitname(int suit) {
    switch(suit) {
      case CLUBS:
        return "Clubs";
      case SPADES:
        return "Spades";
      case HEARTS:
        return "Hearts";
      case DIAMONDS:
        return "Diamonds";
    }
    return null;
  }

  public final static String rank(int rank) {
    switch(rank) {
      case KING:
        return "K";
      case QUEEN: 
        return "Q";
      case JACK:
        return "J";
      case TEN:
        return "10";
      case NINE:
        return "9";
      case EIGHT:
        return "8";
      case SEVEN:
        return "7";
      case SIX:
        return "6";
      case FIVE:
        return "5";
      case FOUR:
        return "4";
      case THREE:
        return "3";
      case TWO:
        return "2";
      case ACE:
        return "A";
    }
    return null;
  }
}


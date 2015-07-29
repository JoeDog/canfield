package org.joedog.canfield.control;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.joedog.util.*;
import org.joedog.canfield.game.Deck;
import org.joedog.canfield.game.Card;
import org.joedog.canfield.game.Canfield;
import org.joedog.canfield.model.Arena;
import org.joedog.canfield.model.Config;
import org.joedog.canfield.model.Player;
import org.joedog.canfield.model.Location;

public class Game extends AbstractController {
  private int    round;
  private Engine engine;
  private Arena  arena;
  private Config config;
  private Player player;
  private Thread thread;
  private AtomicBoolean  hiatus   = new AtomicBoolean(false);

  public Game() {
    this.config    = Config.getInstance();
    this.arena     = Arena.getInstance(); 
    this.player    = new Player((String)this.getModelProperty("PlayerName"));
    this.round     = 1; // newHand increments to 1
    this.addModel(config);
    this.addModel(arena);
    this.addModel(player);
    this.setModelProperty("Round", ""+this.round);
  }

  public void setEngine(Engine engine) {
    this.engine = engine;
  }

  public void setThread(Thread thread) {
    this.thread  = thread;
  }

  public void setMessage(String message) {
    this.setModelProperty("Message", message);
  }

  public void exit() {
    setMessage("Shutting down...");
    this.runModelMethod("save");
    Sleep.milliseconds(500);
    System.exit(0);
  }

  public void reset() {
    setMessage("New game!");
    /*********************************
    this.meldable = false;
    this.passable = false;
    runModelMethod("resetGame");
    runViewMethod("resetScore");
    runViewMethod("clearTrick");
    runViewMethod("clearLast");
    setModelProperty("GameStatus", ""+DEAL);
    setMessage("Dealing a new hand....");
    if (this.isPaused()) {
      this.pause(false);
    } else {
      this.thread.stop();
    }
    *********************************/
  }

  public void pause(boolean pause) {
    hiatus.set(pause);
  }

  public boolean isPaused() {
    return hiatus.get();
  }

  public void select(int x, int y) {
    this.arena.select(x, y);
  }

  public boolean cheatMode() {
    return this.getModelBooleanProperty("Cheat");
  }

  /**
   * Invoked by the GameThread this method starts and 
   * conducts the flow of a game.
   * <p>
   * @param  none
   * @return void
   */
  public synchronized void start() {
    int turn     = 0;
    int status   = Canfield.DEAL;
    engine.start();

    while (engine.paused()) {
      Sleep.milliseconds(500);
    }

    while (status != Canfield.OVER) {
      status = this.getModelIntProperty("Status");
      switch (status) {
        case Canfield.DEAL:
          this.deal();
          break;
        case Canfield.PLAY:
          this.play();
          break;
        case Canfield.SCORE:
          break;
        case Canfield.OVER:
          break;
      } 
      Sleep.milliseconds(20);
    } 
  }

  /**
   * Deals the cards to all game participants
   * <p>
   * @param  none
   * @return void
   */
  private void deal() {
    Deck deck = new Deck();

    deck.shuffle();
    for (int i = 0; i < deck.count(); i++) {
      Card card = deck.get(i);
      if (i < 13) {
        // deal thirteen cards into the reserve
        card.setFaceUp();
        this.arena.addToStock(card);
      } else if (i < 14) {
        // deal one card to first column of tableau
        card.setFaceUp();
        this.arena.addToTableau(1, card);
      } else if (i < 15) {
        // deal one card to second column of tableau
        card.setFaceUp();
        this.arena.addToTableau(2, card);
      } else if (i < 16) {
        // deal one card to third column of tableau
        card.setFaceUp();
        this.arena.addToTableau(3, card);
      } else if (i < 17) {
        // deal one card to fourth column of tableau
        card.setFaceUp();
        this.arena.addToTableau(4, card);
      } else {
        // the rest go to deck 
        this.arena.addToDeck(card);
      }
    } 
    this.setModelProperty("Status", ""+Canfield.PLAY);
    return;
  }

  /** 
   * Referees the trick taking phase of a canfield hand.
   * <p>
   * @param  none
   * @return void
   */
  public void play() {
    while(true);
  }
}

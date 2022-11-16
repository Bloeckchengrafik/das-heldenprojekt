package io.bloeckchengrafik.heldenprojekt.game;

import java.util.logging.Logger;
import java.util.Random;
/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 30.09.2022
 * @author Christian
 */

public class Zauberer extends Held {
  
  // Anfang Attribute
  private int zauberkraft;
  
  public static final String[] names = {
    "Merlin",
    "Gandalf"
  };
  // Ende Attribute
  
  public Zauberer(String name, int staerke, int lebenspunkte, Waffe waffe, int zauberkraft) {
    super(name, staerke, lebenspunkte, waffe);
    this.zauberkraft = zauberkraft;                    
  }

  // Anfang Methoden
  private void heilen() {
    this.setLebenspunkte(this.getLebenspunkte() + zauberkraft);
  }

  public int getZauberkraft() {
    return zauberkraft;
  }

  public void setZauberkraft(int zauberkraftNeu) {
    zauberkraft = zauberkraftNeu;
  }

  // Ende Methoden
} // end of Zauberer

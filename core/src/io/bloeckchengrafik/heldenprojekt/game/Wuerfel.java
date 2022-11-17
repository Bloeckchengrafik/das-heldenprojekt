package io.bloeckchengrafik.heldenprojekt.game;

import java.io.Serializable;
import java.util.Random;

public class Wuerfel implements Serializable {
  
  // Anfang Attribute
  private final int anzahlSeiten;
  private final Random random;
  // Ende Attribute
  
  // Anfang Methoden
  
  public Wuerfel(int anzahlSeiten){
    this.anzahlSeiten = anzahlSeiten;
    this.random = new Random();
  }
  
  public int wuerfeln() {
    return this.random.nextInt(anzahlSeiten)+1;
  }

  // Ende Methoden
} // end of Wuerfel

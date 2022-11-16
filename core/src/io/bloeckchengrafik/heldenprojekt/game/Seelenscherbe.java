package io.bloeckchengrafik.heldenprojekt.game;

/**
 *
 * Eine Seelenscherbe ist ein Upgrade für einen Helden
 *
 * @version 1.0 vom 16.09.2022
 * @author Christian
 */

public class Seelenscherbe {
  
  // Anfang Attribute
  private int ksch = 0;
  private int kper = 0;
  // Ende Attribute
  
  // Anfang Methoden
  public int getKritischerSchaden(){
    return ksch;
  }
  
  public float getKritischerSchadenProzent(){
    return kper;
  }
  
  public float getSeelenwert() {
    return (getKritischerSchaden() * getKritischerSchadenProzent())/5;
  }

  // Ende Methoden
} // end of Seelenscherbe

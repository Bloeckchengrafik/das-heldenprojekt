package io.bloeckchengrafik.heldenprojekt.game;

/**
 *
 * Eine Seelenscherbe ist ein Upgrade für einen Helden
 *
 * @version 1.0 vom 16.09.2022
 * @author Christian
 */

public class Seelenscherbe {

  // Ende Attribute
  
  // Anfang Methoden
  public int getKritischerSchaden(){
    // Anfang Attribute
    return 0;
  }
  
  public float getKritischerSchadenProzent(){
    return 0;
  }
  
  public float getSeelenwert() {
    return (getKritischerSchaden() * getKritischerSchadenProzent())/5;
  }

  // Ende Methoden
} // end of Seelenscherbe

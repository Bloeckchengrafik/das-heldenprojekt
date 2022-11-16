package io.bloeckchengrafik.heldenprojekt.game;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 10.10.2022
 * @author Christian
 */

public class Troll extends Monster {
  
  // Anfang Attribute
  private int heilungspunkte;
  private Wuerfel wuerfel = new Wuerfel(5);
  // Ende Attribute
  
  // Anfang Methoden
  public Troll(int staerke, int lebenspunkte, int heilungspunkte) {
    super(staerke, lebenspunkte);
    
    this.heilungspunkte = heilungspunkte;
  }
  
  public int getAngriffswert() {
    int generated = super.getAngriffswert();
    
    if (wuerfel.wuerfeln() == 1) {
       setLebenspunkte(getLebenspunkte()+1);
    }
    
    return generated;
  }

  // Ende Methoden
} // end of Troll

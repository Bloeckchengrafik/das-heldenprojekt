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
    return super.getAngriffswert();
  }

  public void postAngriff() {
    if (wuerfel.wuerfeln() == 1) {
      setLebenspunkte(getLebenspunkte()+1);
    }
  }

  // Ende Methoden
} // end of Troll

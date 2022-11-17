package io.bloeckchengrafik.heldenprojekt.game;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 09.09.2022
 * @author 
 */

public class Monster extends Entity{
  
  // Anfang Attribute
  private Wuerfel wuerfel4 = new Wuerfel(4);
  private int bonus;
  // Ende Attribute
  
  public Monster(int staerke, int lebenspunkte) {
    super(staerke, lebenspunkte);
  }
  
  public Monster() {
    super(new Wuerfel(4).wuerfeln(), new Wuerfel(4).wuerfeln()+10);
    bonus = wuerfel4.wuerfeln();
  }

  // Anfang Methoden
  public int getAngriffswert() {
    angriffswertBerechnen();
    return angriffswert + bonus;
  }
  
  public static Monster newRandom() {
    Wuerfel wuerfel15 = new Wuerfel(15);
    int staerke = wuerfel15.wuerfeln();
    int lebenspunkte = wuerfel15.wuerfeln();
    
    switch (new Wuerfel(3).wuerfeln()) {
      case 1: 
        return new Troll(staerke, lebenspunkte, new Wuerfel(5).wuerfeln());
      case 2:
        return new Ork(staerke, lebenspunkte);
      case 3:
        return new Goblin(staerke, lebenspunkte);
      default:
        throw new RuntimeException("Dice rolled into error");
    } // end of switch
  }

  public String getName() {
    return getClass().getSimpleName();
  }

    // Ende Methoden
} // end of Monster

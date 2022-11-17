package io.bloeckchengrafik.heldenprojekt.game;

import java.io.Serializable;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 14.09.2022
 * @author Christian
 */

public class Material implements Serializable {
  // Anfang Attribute
  public static final Material WAFFEL = new Material("Waffel", 1);
  public static final Material PFANNE = new Material("Pfanne", 4);
  public static final Material HEISSE_PFANNE = new Material("Heiße Pfanne", 5);
  public static final Material NARRATIVIUM = new Material("Narrativium", -5);
  public static final Material PLUTONIUM = new Material("Plutonium", 20); // Held bekommt -1 HP alle 3 Kämpfe
  public static final Material GOLD = new Material("Gold", 2);
  public static final Material SIRUP = new Material("Sirup", 0);
  
  public static final Material[] values = {
    WAFFEL, PFANNE, HEISSE_PFANNE, NARRATIVIUM, PLUTONIUM, GOLD, SIRUP
  };

  
  private final int bonus;
  private final String name;
  // Ende Attribute
  
  Material(String name, int bonus) {
    this.bonus = bonus;
    this.name = name;
  }
  // Anfang Methoden
  
  public int getBonus() {
    return bonus;
  }
  
  public String getName() {
    return name;
  }

  // Ende Methoden
}

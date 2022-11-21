/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 30.09.2022
 * @author Christian
 */

public class Zauberer extends Held {
  
  // Anfang Attribute
  private final int zauberkraft;
  
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

  // Ende Methoden
} // end of Zauberer

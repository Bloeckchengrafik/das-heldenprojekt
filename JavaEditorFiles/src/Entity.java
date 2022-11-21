import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;
/**
 *
 *
 *
 * @version 1.0 vom 09.09.2022
 * @author 
 */

public class Entity implements Serializable {
  
  // Anfang Attribute
  protected int staerke;
  protected int angriffswert;
  private int lebenspunkte;
  private float level = 1;
  private final Wuerfel wuerfel100 = new Wuerfel(100);
  private ArrayList<Seelenscherbe> seelenscherben = new ArrayList<>();
  
  
  private static final Logger logger = LogFormat.getLogger(Entity.class);
  // Ende Attribute
  
  public Entity(int staerke, int lebenspunkte) {
    this.staerke = staerke;
    this.lebenspunkte = lebenspunkte;
  }

  // Anfang Methoden
  public void angreifen(Entity entity, Kampfregel kampfregel) {
    kampfregel.kampf(this, entity);
    levelAufwerten(entity);
  }
  
  private void levelAufwerten(Entity other) {
    float otherLevelWert = other.getAngriffswert() / 25f;
    int seelenscherbenWert = 0;
    
    for (Seelenscherbe seelenscherbe : seelenscherben) {
      seelenscherbenWert += seelenscherbe.getSeelenwert() / 5;
    } // end of for
    
    level += seelenscherbenWert + otherLevelWert;
  }
  
  void angriffswertBerechnen() {
    float kritischerSchadenProzent = 0;
    float kritischerSchaden = 0;
    float seelenwert = 0;
    
    for (Seelenscherbe seelenscherbe : seelenscherben) {
      kritischerSchadenProzent += seelenscherbe.getKritischerSchadenProzent();
      kritischerSchaden += seelenscherbe.getKritischerSchaden();
      seelenwert += seelenscherbe.getSeelenwert();
    } // end of for
    
    kritischerSchadenProzent = kritischerSchadenProzent / seelenscherben.size();
    kritischerSchaden = kritischerSchaden / seelenscherben.size();
    seelenwert = seelenwert / seelenscherben.size();
    
    int bonus = 0;
    
    if (seelenwert > level) {
      logger.warning("Die Seelenscherbe ist ueber dem Level: " + seelenwert + "!");
      bonus = -5;
    } else {
      if(wuerfel100.wuerfeln() < (kritischerSchadenProzent * 100)) {
        bonus = (int) kritischerSchaden;
      }

    } // end of if-else
    
    angriffswert = staerke + bonus;
  }

  public int getStaerke() {
    return staerke;
  }
  
  public int getAngriffswert() {
    angriffswertBerechnen();
    return angriffswert;
  }
  
  public int getLebenspunkte() {
    return lebenspunkte;  
  }
  
  public void setStaerke(int staerke) {
    this.staerke = staerke;
  }
  
  public void setLebenspunkte(int lebenspunkte) {
    this.lebenspunkte = lebenspunkte;
  }

  public ArrayList<Seelenscherbe> getSeelenscherben() {
    return seelenscherben;
  }

  public void setSeelenscherben(ArrayList<Seelenscherbe> seelenscherbenNeu) {
    seelenscherben = seelenscherbenNeu;
  }
  
  public void addSeelenscherbe(Seelenscherbe scherbe) {
    seelenscherben.add(scherbe);
  }

  // Ende Methoden
} // end of Held

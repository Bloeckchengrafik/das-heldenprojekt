import java.util.Random;
import java.util.logging.Logger;
/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 09.09.2022
 * @author 
 */

public class Held extends Entity {
  
  // Anfang Attribute
  private String name;
  private Waffe waffe;
  
  private static final Logger logger = LogFormat.getLogger(Held.class);
  private final Waffenstapel waffenstapel = new Waffenstapel();
  private float waffenstapelGewicht = 0;
  // Ende Attribute
  
  public Held(String name, int staerke, int lebenspunkte, Waffe waffe) {
    super(staerke, lebenspunkte);
    
    this.name = name;
    this.waffe = waffe;
  }
  
  public Held(String name) {
    super(5, 5);
    this.name = name;
    this.waffe = new Waffe(Material.WAFFEL);  
  }
  public String getName() {
    return name;
  }
  
  private void addWaffe(Waffe waffe) {
    float waffeGewicht = waffe.getBonus() / 10F;
    if (waffenstapelGewicht + waffeGewicht > staerke) throw new RuntimeException("Ouch, that's too much");
    
    waffenstapelGewicht += waffeGewicht;
    waffenstapel.push(waffe);
  }
  
  private Waffe popWaffe() {
    Waffe popped = waffenstapel.pop();
    if ( popped == null ) return null;
    
    waffenstapelGewicht -= popped.getBonus() / 10F;
    return popped;
  }
  
  public void setName(String name) {
    this.name = name;
  }

  public void setWaffe(Waffe waffeNeu) {
    waffe = waffeNeu;
  }

  public Waffe getWaffe() {
    return waffe;
  }

  public static Held newRandom() {
    int staerke = new Wuerfel(15).wuerfeln() + 5;
    int lebenspunkte = new Wuerfel(15).wuerfeln() + 10;
    int other = new Wuerfel(5).wuerfeln();
    
    Waffe waffe = new Waffe();
    
    if (new Wuerfel(2).wuerfeln() == 1) {
      return new Krieger(Krieger.names[new Random().nextInt(Krieger.names.length)], staerke, lebenspunkte, waffe, other); 
    } else {
      return new Zauberer(Zauberer.names[(new Random().nextInt(Zauberer.names.length))], staerke, lebenspunkte, waffe, other); 
    } // end of if-else
  }

  public void postAngriff() {}
  // Ende Methoden
} // end of Held

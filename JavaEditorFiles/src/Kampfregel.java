import java.util.logging.Logger;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 09.09.2022
 * @author 
 */

public class Kampfregel {
  
  // Anfang Attribute
  private static final Logger logger = LogFormat.getLogger(Kampfregel.class);
  private final Wuerfel wuerfel6 = new Wuerfel(6);
  private final Wuerfel wuerfel10 = new Wuerfel(10);
  // Ende Attribute
  
  // Anfang Methoden
  public void kampf(Entity entity1, Entity entity2) {
    int e1Schaden = entity1.getAngriffswert();
    int e2Schaden = entity2.getAngriffswert();

    System.out.println("Cmp " + entity1.getAngriffswert() + " vs " + entity2.getAngriffswert());
    
    if (e1Schaden >= e2Schaden) {
      entity2.setLebenspunkte( Math.max(entity2.getLebenspunkte() - 1, 0) );
    } else {
      entity1.setLebenspunkte( Math.max(entity1.getLebenspunkte() - 1, 0) );
    }
  }

  public void gruppenKampfSchritt(Gruppe gruppe1, Gruppe gruppe2) {
    Entity[] gr1 = gruppe1.getEntities();
    Entity[] gr2 = gruppe2.getEntities();

    kampf(gr1[new Wuerfel(gr1.length).wuerfeln()-1], gr2[new Wuerfel(gr2.length).wuerfeln()-1]);
  }
  
  // Ende Methoden
} // end of Kampfregel

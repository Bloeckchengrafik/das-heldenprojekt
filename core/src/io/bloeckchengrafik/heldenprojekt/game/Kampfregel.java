package io.bloeckchengrafik.heldenprojekt.game;

import java.util.logging.Logger;
import java.util.Random;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 09.09.2022
 * @author 
 */

public class Kampfregel {
  
  // Anfang Attribute
  private static Logger logger = LogFormat.getLogger(Kampfregel.class);
  private Wuerfel wuerfel6 = new Wuerfel(6);
  private Wuerfel wuerfel10 = new Wuerfel(10);
  // Ende Attribute
  
  // Anfang Methoden
  public void kampf(Entity entity1, Entity entity2) {
    int e1Schaden = entity1.getAngriffswert();
    int e2Schaden = entity2.getAngriffswert();
    
    if (e1Schaden >= e2Schaden) {
      entity1.setLebenspunkte( Math.max(entity1.getLebenspunkte() - 1, 0) );    
    } else {
      entity1.setLebenspunkte( Math.max(entity1.getLebenspunkte() - 1, 0) );
    }
  }
  
  private Entity[] randomize(Entity[] rndm) {
    Random random = new Random();
    
    for (int i = 0; i < rndm.length; ++i) {
      int index = random.nextInt(rndm.length - i);
      Entity tmp = rndm[rndm.length - 1 - i];
      rndm[rndm.length - 1 - i] = rndm[index];
      rndm[index] = tmp;
    }
    
    return rndm;
  }
  
  
  public void gruppenKampf(Gruppe gruppe1, Gruppe gruppe2) {
    Entity[] gr1 = randomize(gruppe1.getEntities());
    Entity[] gr2 = randomize(gruppe2.getEntities());
    
    for (Entity e1 : gr1) {
      for (Entity e2 : gr2) {
        kampf(e1, e2);  
      } // end of for  
    } // end of for
  }
  
  // Ende Methoden
} // end of Kampfregel

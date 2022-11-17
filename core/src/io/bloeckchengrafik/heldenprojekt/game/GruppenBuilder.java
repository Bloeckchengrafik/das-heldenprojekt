package io.bloeckchengrafik.heldenprojekt.game;

import java.util.function.Function;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 14.10.2022
 * @author Christian
 */

public class GruppenBuilder {
    private final int len;
    private Function<Integer, Entity> factory;
    
    public GruppenBuilder(int len) {
      this.len = len;
    }
    
    public GruppenBuilder fuelleMitHelden() {
      factory = (i) -> Held.newRandom();
      return this;
    }
  
    public GruppenBuilder fuelleMitMonstern() {
      factory = (i) -> Monster.newRandom();
      return this;
    }

    
    public Gruppe build() {
      return new Gruppe(len, factory);
    }
  }
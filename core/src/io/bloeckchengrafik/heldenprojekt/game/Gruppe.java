package io.bloeckchengrafik.heldenprojekt.game;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 14.10.2022
 * @author Christian
 */

public class Gruppe implements Serializable {
  
  // Anfang Attribute
  private Entity[] entities;
  // Ende Attribute
  
  public Gruppe(int maxLen, Function<Integer, Entity> factory) {
    entities = new Entity[maxLen];
    for (int i = 0; i < maxLen; i++) {
      entities[i] = factory.apply(i);
    }
  }

  public Gruppe(int maxLen) {
    this(maxLen, i -> null);
  }

  // Anfang Methoden
  public void addEntity(Entity entity) {
    for (int i = 0; i < entities.length; i++) {
      if (entities[i] == null) {
        entities[i] = entity;
        break;
      }
    }

    throw new RuntimeException("No more space in group");
  }

  public void removeEntity(Entity entity) {
    for (int i = 0; i < entities.length; i++) {
      if (entities[i] == entity) {
        entities[i] = null;
        break;
      }
    }
  }
  
  public void apply(Consumer<Entity> consumer) {
    for (int i = 0; i < entities.length ; i++) {
      if (entities[i] == null) continue;
      
      consumer.accept(entities[i]);
    } // end of for
  }
  
  public Entity[] getEntities() {
    return entities;
  }


  // Ende Methoden
} // end of Gruppe

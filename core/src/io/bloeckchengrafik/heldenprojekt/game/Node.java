package io.bloeckchengrafik.heldenprojekt.game;

import java.io.Serializable;
import java.util.logging.Logger;
/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 17.10.2022
 * @author Christian
 */

public class Node implements Serializable {
  
  // Anfang Attribute
  private Node next;
  private Object object;
  
  // Ende Attribute
  
  public Node(Object object) {
    this.object = object;
    this.next = null;
  }

  // Anfang Methoden
  
  public Node getNext() {
    return next;
  }

  public Object getObject() {
    return object;
  }

  public void setNext(Node nextNeu) {
    next = nextNeu;
  }

  // Ende Methoden
} // end of Node

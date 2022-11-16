package io.bloeckchengrafik.heldenprojekt.game;

import java.io.Serializable;
import java.util.Stack;
/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 17.10.2022
 * @author Christian
 */

public class Waffenstapel implements Serializable {
  
  // Anfang Attribute
  private Node head;
  // Ende Attribute
  
  // Anfang Methoden
  public void push(Waffe waffe){
    Node node = new Node(waffe);
    node.setNext(head);
    
    head = node;
  }
  
  public Waffe pop() {
    if ( isEmpty() ) throw new RuntimeException("Stack is empty");
    Waffe waffe = (Waffe) head.getObject();
    
    head = head.getNext();
    return waffe;
  }
  
  public boolean isEmpty() {
    return head == null;
  }
  
  
  // Ende Methoden
} // end of Waffenstapel

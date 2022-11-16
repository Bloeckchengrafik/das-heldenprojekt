package io.bloeckchengrafik.heldenprojekt.game;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 17.10.2022
 * @author Christian
 */

public class Queue {
  
  // Anfang Attribute
  private Node head;
  private Node tail;
  // Ende Attribute
  
  // Anfang Methoden
  public boolean isEmpty() {
    return head == null;
  }
  
  public void enqueue(Entity entity) {
    Node node = new Node(entity);
    if ( isEmpty() ) {
      head = node;
      tail = node;
      return;
    }
    
    Node prevTail = tail;
    tail = node;
  
    prevTail.setNext(node);
  }
  
  public Entity dequeue() {
    if ( isEmpty() ) throw new RuntimeException("Die Queue ist leer");
    Node prevHead = head;
    if ( head == null ) {
      tail = null;
      return null;
    }
    
    head = prevHead.getNext();

    return (Entity) prevHead.getObject();
  }
  
  public Entity front() {
    if ( isEmpty() ) throw new RuntimeException("Die Queue ist leer");
    
    return (Entity) head.getObject();    
  }

  // Ende Methoden
} // end of Queue

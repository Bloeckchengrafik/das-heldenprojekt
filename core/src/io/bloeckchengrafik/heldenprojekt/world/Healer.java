package io.bloeckchengrafik.heldenprojekt.world;

import io.bloeckchengrafik.heldenprojekt.game.Held;
import io.bloeckchengrafik.heldenprojekt.game.Queue;
import io.bloeckchengrafik.heldenprojekt.game.Wuerfel;

public class Healer {
    private final Queue queue = new Queue();
    private final int x,y;
    private int num = 0;

    public Healer(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void enqueue(Held held){
        queue.enqueue(held);
        num++;
    }

    public void heal() {
        Held held = (Held) queue.dequeue();
        held.setLebenspunkte(held.getLebenspunkte()+new Wuerfel(3).wuerfeln());
        num--;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getNum() {
        return this.num;
    }
}

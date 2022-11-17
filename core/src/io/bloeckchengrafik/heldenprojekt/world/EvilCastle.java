package io.bloeckchengrafik.heldenprojekt.world;

import io.bloeckchengrafik.heldenprojekt.game.Gruppe;
import io.bloeckchengrafik.heldenprojekt.game.GruppenBuilder;

public class EvilCastle {
    private final int x, y;
    private final Gruppe monsterGruppe;

    public EvilCastle(int num, int x, int y) {
        monsterGruppe = new GruppenBuilder(num)
                .fuelleMitMonstern()
                .build();

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Gruppe getMonsterGruppe() {
        return this.monsterGruppe;
    }
}

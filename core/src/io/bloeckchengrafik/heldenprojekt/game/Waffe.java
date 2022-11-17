package io.bloeckchengrafik.heldenprojekt.game;

import java.io.Serializable;
import java.util.logging.Logger;
import java.util.Random;

/**
 * Beschreibung
 *
 * @author Christian
 * @version 1.0 vom 14.09.2022
 */

public class Waffe implements Serializable {

    // Anfang Attribute
    private static final String[] waffenNamen = {
            "Fridolin die Flöte",
            "Olaf der Schneemann",
            "Mithrilbasti",
            "Hr. Basti",
            "DER Bannhammer",
            "DER ONKEL"
    };


    private String name;
    private int bonus;
    private Material material;
    private int magie;
    private String lore;
    // Ende Attribute

    public Waffe(Material material) {
        this.material = material;

        magie = new Wuerfel(3).wuerfeln();
        name = waffenNamen[new Wuerfel(waffenNamen.length).wuerfeln() - 1];

    }

    public Waffe() {
        this(Material.values[new Wuerfel(Material.values.length).wuerfeln() - 1]);
    }

    // Anfang Methoden
    void bonusBerechnen() {
        int waffenbonus = material.getBonus();

        bonus = magie + waffenbonus;
    }

    public int getBonus() {
        bonusBerechnen();
        return bonus;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    // Ende Methoden

} // end of Waffe

import java.io.Serializable;

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


    private final String name;
    private int bonus;
    private final Material material;
    private final int magie;
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

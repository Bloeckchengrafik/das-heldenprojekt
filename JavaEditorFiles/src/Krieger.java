/**
 * Beschreibung
 *
 * @author Christian
 * @version 1.0 vom 30.09.2022
 */

public class Krieger extends Held {

    // Anfang Attribute
    private final int ausdauer;

    public static final String[] names = {
            "Gerald",
            "Hans",
            "Karl",
            "Klaus",
            "Peter",
            "Rainer Zufall"
    };

    // Ende Attribute

    // Anfang Methoden
    public Krieger(String name, int staerke, int lebenspunkte, Waffe waffe, int ausdauer) {
        super(name, staerke, lebenspunkte, waffe);
        this.ausdauer = ausdauer;
    }

    public void angriffswertBerechnen() {
        super.angriffswertBerechnen();
        this.angriffswert *= 1F + (ausdauer / 100F);
    }

    // Ende Methoden
} // end of Krieger

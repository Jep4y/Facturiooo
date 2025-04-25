import java.time.LocalDate;

public class Factuur {

    private final int factuurNummer;
    private final LocalDate datum;
    private Klant klant;
    private double totaalBedrag;
    private boolean isVerzonden;

    // Constructor accepteert nu een string voor het factuurnummer
    public Factuur(Klant klant, double totaalBedrag, int factuurNummer) {
        this.klant = klant;
        this.totaalBedrag = totaalBedrag;
        this.factuurNummer = factuurNummer;
        this.datum = LocalDate.now();
        this.isVerzonden = false;
    }

    public void markeerAlsVerzonden() {
        this.isVerzonden = true;
    }

    public int getFactuurNummer() {
        return factuurNummer;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public Klant getKlant() {
        return klant;
    }

    public double getTotaalBedrag() {
        return totaalBedrag;
    }

    public boolean isVerzonden() {
        return isVerzonden;
    }
}

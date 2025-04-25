public class FactuurDatumNummerTest {
    public static void main(String[] args) {
        FactuurAplicatie app = new FactuurAplicatie();
        app.voegKlantToe(new Klant("Lars", "Laan 5", "lars@example.com"));
        Factuur factuur = app.maakNieuweFactuur("lars@example.com", 50.0);
        //test datum
        assert factuur.getDatum() != null : "Datum moet automatisch worden ingevuld";
        //test facatuurnummer
        assert factuur.getFactuurNummer() > 0 : "Factuurnummer moet automatisch worden gegenereerd";
        System.out.println("Test voor automatisch invullen van datum en nummer is geslaagd.");
    }
}

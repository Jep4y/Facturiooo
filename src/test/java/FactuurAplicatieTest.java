public class FactuurAplicatieTest {
    public static void main(String[] args) {
        FactuurAplicatie app = new FactuurAplicatie();

        // test klant toevoegen aan de KlantDatabase
        Klant klant = new Klant("Jeppe Jorg", "Straat 123", "jepjorg13@gmail.com");
        app.voegKlantToe(klant);  // Zorg ervoor dat de klant wordt toegevoegd aan de database
        System.out.println("Klant toegevoegd: " + klant.getNaam());

        // test  Maaken nieuwe factuur
        Factuur factuur = app.maakNieuweFactuur("jepjorg13@gmail.com", 100.0);
        if (factuur != null) {
            System.out.println("Factuur gemaakt: " + factuur.getFactuurNummer());

            // Verwerk de factuur
            try {
                app.verwerkFactuur(factuur);
                System.out.println("Factuur succesvol verwerkt: " + factuur.getFactuurNummer());
            } catch (Exception e) {
                System.err.println("Fout bij het verwerken van de factuur: " + e.getMessage());
            }
        } else {
            System.err.println("Factuur kan niet worden aangemaakt. Klant niet gevonden.");
        }
    }
}
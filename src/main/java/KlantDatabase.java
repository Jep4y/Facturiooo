import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class KlantDatabase {
    private Map<String, Klant> klanten;
    private final String bestandPad = "C:/Users/jepjo/Documents/facaturen/klanten.txt";  // Pad naar het bestand voor klantgegevens

    public KlantDatabase() {
        klanten = new HashMap<>();
        laadKlantenUitBestand();
    }

    public void voegKlantToe(Klant klant) {
        klanten.put(klant.getEmail(), klant);
        slaKlantenOpInBestand();  // Sla klanten op telkens wanneer er een nieuwe klant wordt toegevoegd
    }

    public Klant zoekKlant(String email) {
        return klanten.get(email);
    }

    public Map<String, Klant> getKlanten() {
        return klanten;  // Geeft de lijst van klanten terug
    }
    public int aantalKlanten() {
        return klanten.size();
    }


    private void slaKlantenOpInBestand() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bestandPad))) {
            for (Klant klant : klanten.values()) {
                writer.write(klant.getNaam() + "," + klant.getAdres() + "," + klant.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void laadKlantenUitBestand() {
        File file = new File(bestandPad);
        if (!file.exists()) {
            return;  // Geen bestand, geen klanten om in te laden
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] klantGegevens = line.split(",");
                if (klantGegevens.length == 3) {
                    Klant klant = new Klant(klantGegevens[0], klantGegevens[1], klantGegevens[2]);
                    klanten.put(klant.getEmail(), klant);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

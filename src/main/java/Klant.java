public class Klant {
    private String naam;
    private String adres;
    private String email;

    public Klant(String naam, String adres, String email) {
        this.naam = naam;
        this.adres = adres;
        this.email = email;
    }

    public String getNaam() {
        return naam;
    }

    public String getAdres() {
        return adres;
    }

    public String getEmail() {
        return email;
    }

    public boolean isGeldig() {
        return naam != null && !naam.isEmpty() && email != null && !email.isEmpty();
    }
    // de toString() methode voor het weergave van de klant
    @Override
    public String toString() {
        return naam;
    }
}

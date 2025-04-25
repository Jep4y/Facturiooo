import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class FactuurAplicatie {
    private int laatstGebruikNummer;  // Begin nummer als int, bijvoorbeeld F2025-000 wordt 202500
    private KlantDatabase klantDatabase;
    private PDFGenerator pdfGenerator;

    public FactuurAplicatie() {
        klantDatabase = new KlantDatabase();
        pdfGenerator = new PDFGenerator();
        laatstGebruikNummer = 202500000 + klantDatabase.aantalKlanten();
    }

    public Factuur maakNieuweFactuur(String klantEmail, double bedrag) {
        Klant klant = klantDatabase.zoekKlant(klantEmail);
        if (klant == null) {
            System.out.println("Klant niet gevonden.");
            return null;
        }

        // Gebruik het laatst gebruikte nummer als int zonder conversie naar String
        int factuurNummer = laatstGebruikNummer;

        // Maak de factuur aan met het nummer als int
        Factuur factuur = new Factuur(klant, bedrag, factuurNummer);

        // Verhoog het laatst gebruikte nummer voor de volgende factuur
        laatstGebruikNummer++;

        return factuur;
    }

    public boolean verstuurFactuur(Factuur factuur, String bestandsnaam) {
        String ontvanger = factuur.getKlant().getEmail();
        if (ontvanger == null || ontvanger.isBlank()) return false;

        String afzender = "jepjorg10@gmail.com";
        String wachtwoord = "rtmv qzaz doaz rjfp";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(afzender, wachtwoord);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(afzender));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ontvanger));
            msg.setSubject("Uw factuur " + factuur.getFactuurNummer());
            msg.setText("Beste " + factuur.getKlant().getNaam() + ",\n\nHierbij uw factuur.");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            File file = new File(bestandsnaam);
            FileDataSource source = new FileDataSource(file);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(file.getName());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentPart);

            msg.setContent(multipart);

            Transport.send(msg);

            factuur.markeerAlsVerzonden();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void verwerkFactuur(Factuur factuur) {
        // Genereren en versturen van de factuur
        String bestandsnaam = pdfGenerator.genereer(factuur);
        if (bestandsnaam != null) {
            verstuurFactuur(factuur, bestandsnaam);
        }
    }

    public void voegKlantToe(Klant klant) {
        klantDatabase.voegKlantToe(klant);
    }
}
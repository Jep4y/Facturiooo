import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PDFGenerator extends DocumentGenerator {

    @Override
    public String genereer(Factuur factuur) {
        try {
            Document document = new Document();

            String basisPad = "C:/Users/jepjo/Documents/facaturen/";
            LocalDate datum = factuur.getDatum();
            String jaar = String.valueOf(datum.getYear());
            String maand = String.format("%02d", datum.getMonthValue());
            String dag = String.format("%02d", datum.getDayOfMonth());
            String volledigeMapPad = basisPad + jaar + "/" + maand + "/" + dag + "/";

            File map = new File(volledigeMapPad);
            if (!map.exists()) {
                map.mkdirs();
            }

            String bestandsnaam = volledigeMapPad + "Factuur_" + factuur.getFactuurNummer() + ".pdf";

            PdfWriter.getInstance(document, new FileOutputStream(bestandsnaam));
            document.open();
            document.add(new Paragraph("Factuur: " + factuur.getFactuurNummer()));
            document.add(new Paragraph("Datum: " + datum.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            document.add(new Paragraph("Klant: " + factuur.getKlant().getNaam()));
            document.add(new Paragraph("E-mail: " + factuur.getKlant().getEmail()));
            document.add(new Paragraph("Totaal bedrag: €" + factuur.getTotaalBedrag()));
            document.close();

            return bestandsnaam;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

abstract class DocumentGenerator {
    public abstract String genereer(Factuur factuur);
}

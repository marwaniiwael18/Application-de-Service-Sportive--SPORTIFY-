package services;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entities.Match;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFGenerator {

    public static void generatePDF(Match match) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.setLeading(18.0f);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Match Information:");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                contentStream.showText("Nom: " + match.getNom());
                contentStream.newLine();
                contentStream.showText("Type: " + match.getType());
                contentStream.newLine();
                contentStream.showText("Date: " + match.getDate());
                contentStream.newLine();
                contentStream.showText("Heure: " + match.getHeure());
                contentStream.newLine();
                contentStream.showText("Description: " + match.getDescription());
                contentStream.newLine();
                contentStream.showText("Equipe 1: " + match.getEquipe1().toString1());
                contentStream.newLine();
                contentStream.showText("Equipe 2: " + match.getEquipe2().toString1());
                contentStream.newLine();
                contentStream.showText("Arbitre: " + match.getArbitre().toString1());
                contentStream.newLine();
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.showText("Signé par Sportify");
                contentStream.endText();


                // Générer le QR code
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                String matchInfo = "Nom: " + match.getNom() + "\n"
                        + "Type: " + match.getType() + "\n"
                        + "Date: " + match.getDate() + "\n"
                        + "Heure: " + match.getHeure() + "\n"
                        + "Description: " + match.getDescription() + "\n"
                        + "Equipe 1: " + match.getEquipe1().toString1() + "\n"
                        + "Equipe 2: " + match.getEquipe2().toString1() + "\n"
                        + "Arbitre: " + match.getArbitre().toString1();
                BitMatrix bitMatrix = qrCodeWriter.encode(matchInfo, com.google.zxing.BarcodeFormat.QR_CODE, 200, 200);

                // Créer une image à partir du BitMatrix du QR code
                BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

                // Enregistrer l'image du QR code dans un fichier PNG
                File qrCodeFile = new File("match_qr_code.png");
               //
                ImageIO.write(qrCodeImage, "PNG", qrCodeFile);

                // Ajouter l'image du QR code au PDF
                PDImageXObject qrCodeXObject = PDImageXObject.createFromFileByContent(qrCodeFile, document);
                contentStream.drawImage(qrCodeXObject, 300, 500);

            } catch (WriterException e) {
                throw new RuntimeException(e);
            }

            document.save(new File("match_info.pdf"));
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

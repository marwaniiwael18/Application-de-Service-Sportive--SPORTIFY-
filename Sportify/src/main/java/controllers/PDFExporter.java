package controllers;

import entities.Team;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class PDFExporter {

    public static void exportTableViewToPDF(TableView<Team> tableView, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float tableWidth = (float) tableView.getWidth();
            float rowHeight = 20; // Adjust as needed
            float tableHeight = (float) (tableView.getItems().size() * rowHeight);

            float startX = 50; // Adjust as needed
            float startY = (float) (page.getMediaBox().getHeight() - 50); // Adjust as needed

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText("Table Content");
            contentStream.endText();

            startX = 50; // Reset X position
            startY -= 20; // Move up for table content

            for (TableColumn<Team, ?> column : tableView.getColumns()) {
                float columnWidth = (float) column.getWidth();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, startY);
                contentStream.showText(column.getText());
                contentStream.endText();
                startX += columnWidth;
            }

            startY -= 20; // Move up for table content

            ObservableList<Team> items = tableView.getItems();
            for (Team team : items) {
                startX = 50; // Reset X position
                for (TableColumn<Team, ?> column : tableView.getColumns()) {
                    String cellData = ""; // Assurez-vous d'adapter cela à votre modèle User
                    if (column.getCellData(team) != null) {
                        cellData = column.getCellData(team).toString();
                    }
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX, startY);
                    contentStream.showText(cellData);
                    contentStream.endText();
                    startX += (float) column.getWidth();
                }
                startY -= rowHeight;
            }

            contentStream.close();

            document.save(new File(filePath));
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
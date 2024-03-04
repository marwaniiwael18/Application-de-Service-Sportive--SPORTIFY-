package services;

import entities.Match;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {

    public static void generateMatchExcel(List<Match> matches) {
        Workbook workbook = new XSSFWorkbook(); // Créer un nouveau workbook

        try  {
            Sheet sheet = workbook.createSheet("Matches");

            // Création des en-têtes
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Nom", "Type", "Date", "Heure", "Description", "Equipe 1", "Equipe 2", "Arbitre"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Ajout des données des matchs
            int rowNum = 1;
            for (Match match : matches) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(match.getNom());
                row.createCell(1).setCellValue(match.getType());
                row.createCell(2).setCellValue(match.getDate().toString());
                row.createCell(3).setCellValue(match.getHeure().toString());
                row.createCell(4).setCellValue(match.getDescription());
                row.createCell(5).setCellValue(match.getEquipe1().toString());
                row.createCell(6).setCellValue(match.getEquipe2().toString());
                row.createCell(7).setCellValue(match.getArbitre().toString1());
            }

            // Sauvegarde du fichier Excel
            try (FileOutputStream outputStream = new FileOutputStream("matches.xlsx")) {
                workbook.write(outputStream);
            }
            System.out.println("Le fichier Excel des matchs a été créé avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Supposons que vous ayez une liste de matchs
        //List<Match> matches = /* Récupérez vos matchs depuis la source de données */;
       // generateMatchExcel(matches);
    }
}


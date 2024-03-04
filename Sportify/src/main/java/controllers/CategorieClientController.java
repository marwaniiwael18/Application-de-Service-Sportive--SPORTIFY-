package controllers;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import entities.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.ServiceCategorie;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


public class CategorieClientController implements Initializable {

    @FXML
    private ListView<Categorie> categoriesListView;
    private Connection con;
    @FXML
    private Button statButton;
    private ServiceCategorie serviceCategorie;
    @FXML
    private MenuButton menuButton;

    @FXML
    private Button CreateEquipe;
    @FXML
    private Button sportsData;
    @FXML
    private TextField search;
    @FXML
    private ImageView qrCodeImageView;
    ObservableList<Categorie> dataList = FXCollections.observableArrayList();


    // Convert JavaFX Image to BufferedImage
    public BufferedImage convertToBufferedImage(Pane node) {
        int width = (int) node.getWidth();
        int height = (int) node.getHeight();
        WritableImage writableImage = node.snapshot(new SnapshotParameters(), null);

        PixelReader pixelReader = writableImage.getPixelReader();
        int[] pixels = new int[width * height];
        pixelReader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.setRGB(0, 0, width, height, pixels, 0, width);
        return bufferedImage;
    }

    @FXML
    void navigateToStatPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Stat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getImagePaths() throws SQLException {
        List<String> imagePaths = new ArrayList<>();
        String query = "SELECT Image FROM categorie";
        try (PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String imagePath = resultSet.getString("Image");
                imagePaths.add(imagePath);
            }
        }
        return imagePaths;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceCategorie = new ServiceCategorie();

        try {
            List<Categorie> categories = serviceCategorie.afficher();
            categoriesListView.setItems(FXCollections.observableList(categories));
            categoriesListView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Categorie> call(ListView<Categorie> param) {
                    return new ListCell<>() {
                        protected void updateItem(Categorie item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                setText(item.getNom() + " - " + item.getDescription());
                                String imagePath = item.getImage();
                                try {
                                    Image image;
                                    if (imagePath != null && !imagePath.isEmpty()) {
                                        InputStream inputStream = getClass().getResourceAsStream(imagePath);
                                        if (inputStream != null) {
                                            image = new Image(inputStream);
                                        } else {
                                            System.err.println("Image file not found: " + imagePath);
                                            // Load a default image if the specified image is not found
                                            image = new Image("/img/defaultImage.jpg"); // Replace "defaultImage.jpg" with the path to your default image
                                        }
                                    } else {
                                        System.err.println("Empty or null image path");
                                        // Load a default image if the image path is empty or null
                                        image = new Image("/img/defaultImage.jpg"); // Replace "defaultImage.jpg" with the path to your default image
                                    }
                                    ImageView imageView = new ImageView(image);
                                    imageView.setFitWidth(50);
                                    imageView.setFitHeight(50);
                                    setGraphic(imageView);
                                } catch (Exception e) {
                                    System.err.println("Error loading image: " + e.getMessage());
                                }
                            }
                        }
                    };
                }
            });
           showQRCode();
        } catch (Exception e) {
            System.err.println("Error initializing categories: " + e.getMessage());
        }
    }


    private void handleMenuItemAction(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        menuButton.setText(menuItem.getText());
    }

    @FXML
    void Create(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/CreationEquipe.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSportsButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SportsData.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) sportsData.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchForCategorie(ActionEvent event) {
        try {
            categoriesListView.getItems().clear();
            String searchText = search.getText().toLowerCase();
            ObservableList<Categorie> observableList = FXCollections.observableList(serviceCategorie.afficher());

            List<Categorie> filteredList = observableList.stream()
                    .filter(e -> e.getNom().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());

            ObservableList<Categorie> newList = FXCollections.observableList(filteredList);

            categoriesListView.setItems(newList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Image convertToJavaFXImage(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", out);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        return new Image(in);
    }


      public Image generateQRCode(String text, int width, int height) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
       MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        // Return the QR Code as a JavaFX image
        return new Image(new ByteArrayInputStream(pngData));
    }
    public void showQRCode() {
        try {
            String categoriesDetails = "Catégories de sports  ";
            Image qrCodeImage = generateQRCode(categoriesDetails, 200, 200);
            qrCodeImageView.setImage(qrCodeImage);
        } catch (WriterException | IOException e) {
            e.printStackTrace();

        }
    }


}
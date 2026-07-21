package ds.comsats.idms.idms.filegui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.Objects;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import ds.comsats.idms.idms.navigation.SceneHistory;
import ds.comsats.idms.idms.compression.HuffmanCompressor;

public class SelectFileGUI {

    File selectedFile;
    File processedFile;

    public Scene getScene(Stage stage, SceneHistory history) {

        Button back = new Button("← Back");
        back.setOnAction(e -> history.back());

        String navStyle =
                "-fx-background-color:#1E293B;" +
                        "-fx-text-fill:#E2E8F0;" +
                        "-fx-font-size:15px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:25;" +
                        "-fx-border-radius:25;" +
                        "-fx-border-color:#38BDF8;" +
                        "-fx-border-width:2;" +
                        "-fx-padding:10 22;" +
                        "-fx-cursor:hand;";

        String navHover =
                "-fx-background-color:#38BDF8;" +
                        "-fx-text-fill:#020617;" +
                        "-fx-font-size:15px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:25;" +
                        "-fx-border-radius:25;" +
                        "-fx-border-color:#7DD3FC;" +
                        "-fx-border-width:2;" +
                        "-fx-padding:10 22;" +
                        "-fx-cursor:hand;";

        back.setStyle(navStyle);

        back.setOnMouseEntered(e -> back.setStyle(navHover));
        back.setOnMouseExited(e -> back.setStyle(navStyle));

        HBox navigation = new HBox(15, back);
        navigation.setAlignment(Pos.TOP_LEFT);
        navigation.setPadding(new Insets(25));


        Image saveImage = new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream("/Button.png"),
                        "Button.png not found in resources"
                )
        );

        Image downloadImage = new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream("/Save.png"),
                        "Save.png not found in resources"
                )
        );

        Text title = new Text("Drop File Here\n\n\t   or\n\nClick to Browse");
        Label info = new Label("No File Selected Yet");

        ImageView compressIcon = new ImageView(saveImage);
        compressIcon.setFitWidth(20);
        compressIcon.setFitHeight(20);

        ImageView decompressIcon = new ImageView(saveImage);
        decompressIcon.setFitWidth(20);
        decompressIcon.setFitHeight(20);

        ImageView saveIcon = new ImageView(downloadImage);
        saveIcon.setFitWidth(20);
        saveIcon.setFitHeight(20);

        Button compress = new Button("Compress File", compressIcon);
        Button decompress = new Button("Decompress File", decompressIcon);
        Button save = new Button("Save File", saveIcon);
        Button reset = new Button("Reset");

        Button[] buttons = {compress, decompress, save, reset};

        String normalStyle =
                "-fx-background-color:linear-gradient(to right,#1E293B,#0F172A);" +
                        "-fx-text-fill:#E2E8F0;" +
                        "-fx-background-radius:30;" +
                        "-fx-border-radius:30;" +
                        "-fx-border-color:#38BDF8;" +
                        "-fx-border-width:2;" +
                        "-fx-padding:12 25;" +
                        "-fx-cursor:hand;";

        String hoverStyle =
                "-fx-background-color:linear-gradient(to right,#38BDF8,#7DD3FC);" +
                        "-fx-text-fill:#020617;" +
                        "-fx-background-radius:30;" +
                        "-fx-border-radius:30;" +
                        "-fx-border-color:#BAE6FD;" +
                        "-fx-border-width:2;" +
                        "-fx-padding:12 25;" +
                        "-fx-cursor:hand;";

        for (Button button : buttons) {
            button.setPrefWidth(230);
            button.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 19));
            button.setStyle(normalStyle);
            button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
            button.setOnMouseExited(e -> button.setStyle(normalStyle));
        }

        save.setDisable(true);

        VBox buttonBox = new VBox(20, compress, decompress, save, reset);
        buttonBox.setAlignment(Pos.CENTER);

        StackPane fileArea = new StackPane();

        Rectangle box = new Rectangle(460, 285);
        box.setArcWidth(30);
        box.setArcHeight(30);
        box.setStroke(Color.web("#38BDF8"));
        box.setStrokeWidth(5);
        box.getStrokeDashArray().addAll(12.0, 12.0);
        box.setFill(Color.rgb(56, 189, 248, 0.12));

        DropShadow shadow = new DropShadow();
        shadow.setRadius(20);
        shadow.setColor(Color.rgb(56, 189, 248, 0.45));
        box.setEffect(shadow);

        title.setFill(Color.web("#38BDF8"));
        title.setStyle("-fx-font-size:19px;-fx-font-weight:bold;");

        info.setTextFill(Color.web("#E2E8F0"));

        fileArea.getChildren().addAll(box, title, info);
        StackPane.setAlignment(info, Pos.BOTTOM_CENTER);

        box.setOnMouseClicked(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select File");

            File file = chooser.showOpenDialog(stage);

            if (file != null) {
                selectedFile = file;
                info.setText("File : " + file.getName());
                title.setText("File Selected!");
                title.setFill(Color.web("#22C55E"));
                box.setFill(Color.rgb(34, 197, 94, 0.12));
                shadow.setColor(Color.rgb(34, 197, 94, 0.45));
                save.setDisable(false);
            }
        });

        box.setOnDragOver(e -> {
            Dragboard board = e.getDragboard();

            if (board.hasFiles() && board.getFiles().size() == 1) {
                e.acceptTransferModes(TransferMode.COPY);
            }

            e.consume();
        });

        box.setOnDragDropped(e -> {
            Dragboard board = e.getDragboard();
            boolean success = false;

            if (board.hasFiles() && board.getFiles().size() == 1) {

                selectedFile = board.getFiles().get(0);

                info.setText("File : " + selectedFile.getName());
                title.setText("File Dropped Successfully");
                title.setFill(Color.web("#22C55E"));
                box.setFill(Color.rgb(34, 197, 94, 0.12));
                box.setStroke(Color.web("#22C55E"));
                shadow.setColor(Color.rgb(34, 197, 94, 0.45));
                save.setDisable(false);

                success = true;
            }

            e.setDropCompleted(success);
            e.consume();
        });

        compress.setOnAction(e -> {

            if (selectedFile != null) {

                try {
                    File tempFile = File.createTempFile("compressed_", ".huff");
                    HuffmanCompressor.compress(selectedFile.getAbsolutePath(), tempFile.getAbsolutePath());

                    processedFile = tempFile;
                    title.setText("Compression Successful\n\n" +
                            HuffmanCompressor.getCompressionInfo(selectedFile.getAbsolutePath(),
                                    tempFile.getAbsolutePath())
                    );
                    info.setText("Ready to Save");
                    title.setFill(Color.web("#22C55E"));
                    save.setDisable(false);

                } catch (Exception ex) {

                    title.setText("Compression Failed!");
                    title.setFill(Color.web("#EF4444"));
                }
            }
        });

        decompress.setOnAction(e -> {
            if (selectedFile != null) {
                try {
                    File tempFile = File.createTempFile("decompressed_", ".decoded");

                    HuffmanCompressor.decompress(
                            selectedFile.getAbsolutePath(),
                            tempFile.getAbsolutePath()
                    );

                    processedFile = tempFile;
                    title.setText("Decompression Successful\n\n" +
                            HuffmanCompressor.getDecompressionInfo(selectedFile.getAbsolutePath(),
                                    tempFile.getAbsolutePath())
                    );
                    info.setText("Ready to Save");
                    title.setFill(Color.web("#22C55E"));
                    save.setDisable(false);

                } catch (Exception ex) {
                    title.setText("Decompression Failed!");
                    title.setFill(Color.web("#EF4444"));
                }
            }
        });

        save.setOnAction(e -> {
            if (processedFile != null) {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Save File");
                chooser.setInitialFileName(processedFile.getName());

                File destination = chooser.showSaveDialog(stage);

                if (destination != null) {
                    try {
                        Files.copy(processedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        info.setText("Saved : " + destination.getAbsolutePath());
                        title.setText("File Saved Successfully!");
                        title.setFill(Color.web("#22C55E"));

                    } catch (Exception ex) {
                        title.setText("Saving Failed!");
                        title.setFill(Color.web("#EF4444"));
                    }
                }
            }
        });

        reset.setOnAction(e -> {
            selectedFile = null;
            processedFile = null;

            title.setText("Drop File Here\n\n\t   or\n\nClick to Browse");
            title.setFill(Color.web("#38BDF8"));

            info.setText("No File Selected Yet");
            info.setTextFill(Color.web("#E2E8F0"));

            box.setFill(Color.rgb(56, 189, 248, 0.12));
            box.setStroke(Color.web("#38BDF8"));
            shadow.setColor(Color.rgb(56, 189, 248, 0.45));

            save.setDisable(true);
        });

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:#020617;");
        root.setCenter(fileArea);
        root.setRight(buttonBox);
        root.setTop(navigation);

        BorderPane.setAlignment(fileArea, Pos.CENTER);
        BorderPane.setAlignment(buttonBox, Pos.CENTER);
        BorderPane.setMargin(buttonBox, new Insets(0, 50, 0, 0));

        return new Scene(root,1300,750);
    }
}


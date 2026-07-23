package ds.comsats.idms.idms.reader;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.File;
import java.io.IOException;

import ds.comsats.idms.idms.navigation.SceneHistory;

public class Reader {

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

        HBox navigation2 = new HBox(15, back);
        navigation2.setAlignment(Pos.CENTER_LEFT);


        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:#020617;");


        StackPane viewer = new StackPane();
        viewer.setPrefSize(750,580);

        viewer.setStyle("""
                -fx-background-color:#0B1220;
                -fx-border-color:#38BDF8;
                -fx-border-width:2;
                -fx-background-radius:25;
                -fx-border-radius:25;
                """);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(25);
        shadow.setColor(Color.rgb(56,189,248,0.4));
        viewer.setEffect(shadow);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(700);
        imageView.setFitHeight(550);
        imageView.setPreserveRatio(true);

        TextArea text = new TextArea();
        text.setWrapText(true);
        text.setEditable(false);
        text.setPrefSize(700,550);
        text.setVisible(false);

        text.setStyle("""
                -fx-control-inner-background:#0B1220;
                -fx-background-color:#0B1220;
                -fx-text-fill:white;
                -fx-font-size:15px;
                -fx-border-color:transparent;
                """);

        viewer.getChildren().addAll(imageView,text);

        BorderPane.setMargin(viewer,new Insets(25));
        root.setCenter(viewer);

        VBox sideBar = new VBox(20);
        sideBar.setPrefWidth(280);
        sideBar.setPadding(new Insets(25));
        sideBar.setStyle("-fx-background-color:#0B1220;");

        String buttonStyle = """
                -fx-background-color:linear-gradient(to right,#2563EB,#38BDF8);
                -fx-text-fill:white;
                -fx-font-size:15px;
                -fx-font-weight:bold;
                -fx-background-radius:20;
                -fx-cursor:hand;
                """;

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files","*.pdf")
        );

        PDDocument[] document = new PDDocument[1];
        PDFRenderer[] renderer = new PDFRenderer[1];
        int[] currentPage = {0};

        Button openButton = new Button("Open PDF");
        openButton.setPrefSize(200,45);
        openButton.setStyle(buttonStyle);

        TextField fileNameField = new TextField();
        fileNameField.setPromptText("File Path");
        fileNameField.setEditable(false);

        fileNameField.setStyle("""
                -fx-background-color:#020617;
                -fx-control-inner-background:#020617;
                -fx-text-fill:white;
                -fx-prompt-text-fill:#94A3B8;
                -fx-border-color:#38BDF8;
                -fx-background-radius:15;
                -fx-border-radius:15;
                -fx-padding:10;
                """);

        Label modeLabel = new Label("Reading Mode");
        modeLabel.setTextFill(Color.WHITE);
        modeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD,16));

        RadioButton radio1 = new RadioButton("Image View");
        radio1.setTextFill(Color.WHITE);
        radio1.setFont(Font.font("Segoe UI",15));

        RadioButton radio2 = new RadioButton("Text View");
        radio2.setTextFill(Color.WHITE);
        radio2.setFont(Font.font("Segoe UI",15));

        ToggleGroup group = new ToggleGroup();
        radio1.setToggleGroup(group);
        radio2.setToggleGroup(group);
        radio1.setSelected(true);

        Button previousButton = new Button("Previous");
        previousButton.setPrefSize(200,45);
        previousButton.setStyle(buttonStyle);

        Button nextButton = new Button("Next");
        nextButton.setPrefSize(200,45);
        nextButton.setStyle(buttonStyle);

        openButton.setOnAction(e->{
            File file = fileChooser.showOpenDialog(stage);
            if(file!=null){
                try{
                    if(document[0]!=null) document[0].close();

                    document[0] = Loader.loadPDF(file);
                    renderer[0] = new PDFRenderer(document[0]);
                    currentPage[0] = 0;

                    imageView.setImage(SwingFXUtils.toFXImage(renderer[0].renderImageWithDPI(currentPage[0],150), null));

                    PDFTextStripper reader = new PDFTextStripper();
                    reader.setSortByPosition(true);
                    text.setText(reader.getText(document[0]));
                    Logger.getLogger("org.apache.pdfbox").setLevel(Level.SEVERE);
                    fileNameField.setText(file.getName());

                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        radio2.setOnAction(e->{
            if(radio2.isSelected()){
                imageView.setVisible(false);
                text.setVisible(true);
                previousButton.setVisible(false);
                nextButton.setVisible(false);
            }
        });

        radio1.setOnAction(e->{
            if(radio1.isSelected()){
                text.setVisible(false);
                imageView.setVisible(true);
                previousButton.setVisible(true);
                nextButton.setVisible(true);
            }
        });

        previousButton.setOnAction(e->{
            if(document[0]!=null && currentPage[0]>0){
                try{
                    currentPage[0]--;
                    imageView.setImage(
                            SwingFXUtils.toFXImage(renderer[0].renderImageWithDPI(currentPage[0],150), null)
                    );
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        nextButton.setOnAction(e->{
            if(document[0]!=null && currentPage[0]<document[0].getNumberOfPages()-1){
                try{
                    currentPage[0]++;
                    imageView.setImage(
                            SwingFXUtils.toFXImage( renderer[0].renderImageWithDPI(currentPage[0],150),  null ) );
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        VBox radioBox = new VBox(12);
        radioBox.setPadding(new Insets(15));

        radioBox.setStyle("""
                -fx-background-color:#020617;
                -fx-border-color:#1E293B;
                -fx-background-radius:18;
                -fx-border-radius:18;
                """);

        radioBox.getChildren().addAll(modeLabel, radio1, radio2);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox navigation = new VBox(15);
        navigation.setAlignment(Pos.CENTER);

        navigation.getChildren().addAll( nextButton, previousButton );

        sideBar.getChildren().addAll(navigation2, openButton, fileNameField, radioBox, spacer, navigation);


        BorderPane.setMargin(sideBar,new Insets(20));
        root.setLeft(sideBar);

        return new Scene(root,1300,750);
    }
}
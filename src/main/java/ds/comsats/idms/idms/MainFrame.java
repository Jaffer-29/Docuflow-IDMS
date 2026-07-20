package ds.comsats.idms.idms;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;

import ds.comsats.idms.idms.navigation.SceneHistory;
import ds.comsats.idms.idms.filegui.SelectFileGUI;
import ds.comsats.idms.idms.reader.Reader;


public class MainFrame extends Application {

    private SceneHistory history;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        history = new SceneHistory(stage);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color:#020617;");

        Label heading = new Label("File Manager");
        heading.setTextFill(Color.WHITE);
        heading.setStyle("-fx-font-size:42px;-fx-font-weight:700;");

        Label subHeading = new Label("Intelligent Document Management System");
        subHeading.setTextFill(Color.web("#94A3B8"));
        subHeading.setStyle("-fx-font-size:18px;");

        HBox cards = new HBox(40);
        cards.setAlignment(Pos.CENTER);

        StackPane compress = createCard("⬇", "Compress / Decompress", "Manage compressed files");
        compress.setOnMouseClicked(e -> {
            SelectFileGUI fileGUI = new SelectFileGUI();
            Scene scene = fileGUI.getScene(stage,history);
            history.push(scene);
        });

        StackPane read = createCard("📄", "File Reading", "Read and analyze files");
        read.setOnMouseClicked(e -> {
            Reader reader = new Reader();
            Scene scene = reader.getScene(stage,history);
            history.push(scene);
        });


        cards.getChildren().addAll(compress, read);

        VBox layout = new VBox(20, heading, subHeading, cards);
        layout.setAlignment(Pos.CENTER);
        root.getChildren().add(layout);
        Scene scene = new Scene(root,1300,750);
        history.push(scene);

        Image icon = new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream("/Logo.png"),
                        "Logo.png not found in resources"
                )
        );
        stage.getIcons().add(icon);

        stage.setTitle("File Manager");
        stage.show();
    }


    private StackPane createCard(String icon,String title,String description){

        StackPane container = new StackPane();
        VBox content = new VBox(18);
        content.setAlignment(Pos.CENTER);
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size:45px;");

        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-font-size:24px;-fx-font-weight:bold;");

        Label descLabel = new Label(description);
        descLabel.setTextFill(Color.web("#CBD5E1"));
        descLabel.setStyle("-fx-font-size:15px;");

        content.getChildren().addAll(iconLabel,titleLabel,descLabel);

        Rectangle card = new Rectangle(370,260);
        card.setArcWidth(35);
        card.setArcHeight(35);
        card.setFill(Color.rgb(15,23,42,0.75));
        card.setStroke(Color.web("#38BDF8"));
        card.setStrokeWidth(1.5);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(35);
        shadow.setColor(Color.rgb(56,189,248,0.25));
        card.setEffect(shadow);

        container.getChildren().addAll(card,content);

        container.setOnMouseEntered(e -> {
            card.setFill(Color.rgb(56,189,248,0.15));
            card.setStrokeWidth(3);
            container.setScaleX(1.06);
            container.setScaleY(1.06);
        });

        container.setOnMouseExited(e -> {
            card.setFill(Color.rgb(15,23,42,0.75));
            card.setStrokeWidth(1.5);
            container.setScaleX(1);
            container.setScaleY(1);
        });
        return container;
    }
}
package views.templates;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CastleView extends Application {

    private ImageView imageView;

    public CastleView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void start(Stage stage) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("path toward the image"));
        ImageView imageView = new ImageView(image);
        imageView.setImage(image);

        imageView.setFitWidth(75);
        imageView.setPreserveRatio(true);

        stage.setScene(scene);
        stage.show();

    }
}

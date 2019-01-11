import helpers.Config;
import helpers.Screen;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import views.controllers.GameViewController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle(Config.getValue("projectName")); // set the title of the window
        Group root = new Group(); // define the root group
        Scene scene = new Scene(root, 200, 200, Color.TRANSPARENT); // define the color of the scene
        Image image = new Image("/background.png");
        scene.setFill(new ImagePattern(image));
        stage.setScene(scene);
        stage.setResizable(Boolean.valueOf(Config.getValue("resizable"))); // define if the window is resizable
        // define the size of the stage to be as max as possible
        stage.setX(Screen.getXMin());
        stage.setY(Screen.getYMin());
        stage.setWidth(Screen.getXMax());
        stage.setHeight(Screen.getYMax());
        stage.show(); // show the stage
        new GameViewController(root); // new GameViewController view controller
        // set a music on background
        final File file = new File("C:\\Users\\PdN\\Music\\Symphony No. 9 _ Beethoven.mp3");
        final Media media = new Media(file.toURI().toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}

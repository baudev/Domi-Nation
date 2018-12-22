import helpers.Config;
import helpers.Screen;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import views.controllers.GameViewController;

public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(Config.getValue("projectName")); // set the title of the window
        Group root = new Group(); // define the root group
        Scene scene = new Scene(root, 200, 200, Color.TRANSPARENT); // define the color of the scene
        stage.setScene(scene);
        stage.setResizable(Boolean.valueOf(Config.getValue("resizable"))); // define if the window is resizable
        // define the size of the stage to be as max as possible
        stage.setX(Screen.getXMin());
        stage.setY(Screen.getYMin());
        stage.setWidth(Screen.getXMax());
        stage.setHeight(Screen.getYMax());
        stage.show(); // show the stage
        new GameViewController(root); // new GameViewController view controller
    }
}

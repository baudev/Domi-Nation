package views.templates;

import helpers.Screen;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import views.interfaces.OnGameModeClickListener;

public class GameModeView extends Parent {

    private OnGameModeClickListener onGameModeClickListener;

    public GameModeView() {
        // Define layout
        GridPane gridPane = new GridPane();

        // Define title
        Text text = new Text();
        text.setText("Select the game mode");
        gridPane.add(text, 1, 0);

        // Add the buttons of each modes
        int i = 0;
        for(models.enums.GameMode gameMode : models.enums.GameMode.values()){
            Button button = new Button();
            button.setText(gameMode.toString());
            button.setLayoutX(10);
            button.setLayoutY(10);

            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) { // when the user click on the following button
                    getOnGameModeClickListener().onGameModeClickListener(gameMode); // transmit the event to the view controller callback
                }
            });

            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {

                }
            });
            button.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {

                }
            });
            button.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    button.setTranslateY(2);

                }
            });
            button.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    button.setTranslateY(-2);
                }
            });



            gridPane.add(button, i, 1); // add the button to the layout
            i++;
        }

        gridPane.setMinSize(Screen.percentageToXDimension(100), Screen.percentageToYDimension(100)); // define the size of the gridPane layout
        gridPane.setAlignment(Pos.CENTER); // center the layout
        this.getChildren().add(gridPane); // add the layout to the root group
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public OnGameModeClickListener getOnGameModeClickListener() {
        return onGameModeClickListener;
    }

    public void setOnGameModeClickListener(OnGameModeClickListener onGameModeClickListener) {
        this.onGameModeClickListener = onGameModeClickListener;
    }
}

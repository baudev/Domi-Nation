package views.templates;

import helpers.Screen;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.enums.PlayerColor;
import views.interfaces.OnPlayerColorClickListener;

import java.util.List;

/**
 * JavaFX view allowing a Player to choose his color.
 */
public class ColorPlayerView extends Parent {

    private OnPlayerColorClickListener onPlayerColorClickListener;

    /**
     * Generates the view allowing Player to choose his color
     * @param playerColorList {@link List} containing available {@link PlayerColor}s.
     * @param playerNumber Number of {@link models.classes.Player}s.
     */
    public ColorPlayerView(List<PlayerColor> playerColorList, int playerNumber) {
        // Define layout
        GridPane gridPane = new GridPane();

        // Define title
        Text text = new Text();
        text.setText("Player " + playerNumber + ", select a color");
        gridPane.add(text, 1, 0);

        // Add the buttons of each possibilities
        int i = 0;
        for(models.enums.PlayerColor playerColor : playerColorList){
            Button button = new Button();
            button.setText(String.valueOf(playerColor.toString()));
            button.setLayoutX(10);
            button.setLayoutY(10);

            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) { // when the user click on the following button
                    getOnPlayerColorClickListener().onPlayerColorClickListener(playerColor); // transmit the event to the view controller callback
                }
            });

            gridPane.add(button, i, 1); // add the button to the layout
            i++;
        }

        gridPane.setMinSize(Screen.percentageToXDimension(100), Screen.percentageToYDimension(100)); // define the size of the gridPane layout
        gridPane.setAlignment(Pos.CENTER); // center the layout
        this.getChildren().add(gridPane); // add the layout to the root group
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Gets the callback of OnPlayerColorClickListener.
     * @return Callback of OnPlayerColorClickListener
     * @see OnPlayerColorClickListener
     */
    public OnPlayerColorClickListener getOnPlayerColorClickListener() {
        return onPlayerColorClickListener;
    }

    /**
     * Sets the callback of OnPlayerColorClickListener.
     * @param onPlayerColorClickListener the callback of OnPlayerColorClickListener.
     */
    public void setOnPlayerColorClickListener(OnPlayerColorClickListener onPlayerColorClickListener) {
        this.onPlayerColorClickListener = onPlayerColorClickListener;
    }
}

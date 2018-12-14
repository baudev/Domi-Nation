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
import models.interfaces.OnPlayerColorClickListener;

import java.util.List;

public class ColorPlayer extends Parent {

    private OnPlayerColorClickListener onPlayerColorClickListener;

    public ColorPlayer(List<PlayerColor> playerColorList, int playerNumber) {
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

    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public OnPlayerColorClickListener getOnPlayerColorClickListener() {
        return onPlayerColorClickListener;
    }

    public void setOnPlayerColorClickListener(OnPlayerColorClickListener onPlayerColorClickListener) {
        this.onPlayerColorClickListener = onPlayerColorClickListener;
    }
}

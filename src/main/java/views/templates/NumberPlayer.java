package views.templates;

import helpers.Screen;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.enums.PlayerNumber;
import views.interfaces.OnPlayerNumberClickListener;

public class NumberPlayer extends Parent {

    private OnPlayerNumberClickListener onPlayerNumberClickListener;

    public NumberPlayer() {
        // Define layout
        GridPane gridPane = new GridPane();

        // Define title
        Text text = new Text();
        text.setText("Select the number of players");
        gridPane.add(text, 1, 0);

        // Add the buttons of each possibilities
        int i = 0;
        for(PlayerNumber numberPlayer : PlayerNumber.values()){
            Button button = new Button();
            button.setText(String.valueOf(numberPlayer.getValue()));
            button.setLayoutX(10);
            button.setLayoutY(10);

            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) { // when the user click on the following button
                    getOnPlayerNumberClickListener().onPlayerNumberClickListener(numberPlayer); // transmit the event to the view controller callback
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

    public OnPlayerNumberClickListener getOnPlayerNumberClickListener() {
        return onPlayerNumberClickListener;
    }

    public void setOnPlayerNumberClickListener(OnPlayerNumberClickListener onPlayerNumberClickListener) {
        this.onPlayerNumberClickListener = onPlayerNumberClickListener;
    }
}

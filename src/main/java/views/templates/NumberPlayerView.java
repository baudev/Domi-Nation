package views.templates;

import helpers.Screen;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.enums.PlayerNumber;
import views.interfaces.OnPlayerNumberClickListener;

public class NumberPlayerView extends Parent {

    private OnPlayerNumberClickListener onPlayerNumberClickListener;

    public NumberPlayerView() {
        // Define background

        Image background = new Image("/kingDomino.png");        //add the background image of the game
        ImageView imageView = new ImageView(background);
        imageView.setFitWidth(Screen.percentageToXDimension(100));
        imageView.setFitHeight(Screen.percentageToYDimension(100));
        this.getChildren().add(imageView);

        // Define banner
        StackPane stackPane1 = new StackPane();                 //add the banner image
        Image imageParchemin = new Image("/parchemin.png");
        ImageView parchemin = new ImageView(imageParchemin);
        stackPane1.getChildren().add(parchemin);
        stackPane1.setMinSize(Screen.percentageToXDimension(100), Screen.percentageToYDimension(100));  //positioning the banner image
        stackPane1.setAlignment(Pos.CENTER);
        stackPane1.setTranslateY(Screen.percentageToYDimension(-20));
        this.getChildren().add(stackPane1);


        // Define title
        StackPane stackPane = new StackPane();          //add the title on the banner
        Text text = new Text();
        text.setText("Select the number of players");
        text.setFont(Font.font("null", FontWeight.BOLD,35));
        text.setTextAlignment(TextAlignment.CENTER);
        stackPane.getChildren().add(text);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setMinSize(Screen.percentageToXDimension(100), Screen.percentageToYDimension(50)); // define the size of the stackpane layout

        this.getChildren().add(stackPane);

        // Define layout
        HBox hBox = new HBox();  // create an Hbox to align horizontally the button
        hBox.setSpacing(20);    // set the space between each button

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

            hBox.getChildren().add(button); // add the button to the layout
            i++;
        }

        hBox.setMinSize(Screen.percentageToXDimension(80), Screen.percentageToYDimension(60)); // define the size of the hbox layout
        hBox.setTranslateX(Screen.percentageToXDimension(10));
        hBox.setTranslateY(Screen.percentageToYDimension(30));
        hBox.setAlignment(Pos.CENTER); // center the layout
        this.getChildren().add(hBox); // add the layout to the root group
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

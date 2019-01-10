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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
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
        StackPane stackPane1 = new StackPane();
        Rectangle backgroundTitle = new Rectangle(Screen.percentageToXDimension(70),Screen.percentageToYDimension(30)); // create the rectangle that will host the banner
        Image banner = new Image("/bannerTitleRed.png");
        backgroundTitle.setFill(new ImagePattern(banner));  //  create a banner
        stackPane1.getChildren().add(backgroundTitle);  //  add to the stackpane the whole banner
        Text text = new Text();
        text.setText("Select the number of player");
        text.setFont(Font.font("null", FontWeight.BOLD,35));
        text.setFill(Color.GOLD);   //  assign the gold color to the text
        stackPane1.getChildren().add(text); //  add the text to the stackpane therefore to the banner
        text.setTranslateX(stackPane1.getWidth()/2);   //   centralize the text
        text.setTranslateY(stackPane1.getHeight()/2);

        stackPane1.setMinSize(Screen.percentageToXDimension(100), Screen.percentageToYDimension(100));  //  Positioning the stackPane
        stackPane1.setAlignment(Pos.CENTER);
        stackPane1.setTranslateY(Screen.percentageToYDimension(-35));

        this.getChildren().add(stackPane1); //add the stackPane to the view


        // Define layout
        HBox hBox = new HBox();  // create an Hbox to align horizontally the button
        hBox.setSpacing(20);    // set the space between each button

        // Add the buttons of each possibilities
        int i = 0;
        for(PlayerNumber numberPlayer : PlayerNumber.values()){
            ButtonView buttonView = new ButtonView(String.valueOf(numberPlayer.getValue()));
            buttonView.setLayoutX(10);
            buttonView.setLayoutY(10);

            buttonView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) { // when the user click on the following button
                    getOnPlayerNumberClickListener().onPlayerNumberClickListener(numberPlayer); // transmit the event to the view controller callback
                }
            });

            buttonView.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    buttonView.setTranslateY(2);

                }
            });
            buttonView.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    buttonView.setTranslateY(-2);
                }
            });


            hBox.getChildren().add(buttonView); // add the button to the layout
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

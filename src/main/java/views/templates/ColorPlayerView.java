package views.templates;

import helpers.Screen;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
        text.setText("Player " + playerNumber + ", select a color");
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
        HBox hBox = new HBox(); // create an Hbox to align horizontally the button
        hBox.setSpacing(20);    // set the space between each button
        // Add the buttons of each possibilities
        int i = 0;
        for(models.enums.PlayerColor playerColor : playerColorList){
            ButtonView buttonView = new ButtonView(String.valueOf(playerColor.toString()),false);
            buttonView.setLayoutX(10);
            buttonView.setLayoutY(10);

            buttonView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    Bloom bloom = new Bloom();      //add a bloom effect when the mouse is on the button
                    bloom.setThreshold(0.6);
                    buttonView.setEffect(bloom);
                }
            });
            buttonView.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    buttonView.setEffect(null); //  remove the bloom effect when the mouse is no longer on the button
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


            buttonView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) { // when the user click on the following button
                    getOnPlayerColorClickListener().onPlayerColorClickListener(playerColor); // transmit the event to the view controller callback
                }
            });

            hBox.getChildren().add(buttonView); // add the button to the layout
            i++;
        }
        hBox.setMinSize(Screen.percentageToXDimension(80), Screen.percentageToYDimension(60)); // define the size of the hbox layout
        hBox.setTranslateX(Screen.percentageToXDimension(10));
        hBox.setTranslateY(Screen.percentageToYDimension(30));
        hBox.setAlignment(Pos.CENTER);  //center the layout
        this.getChildren().add(hBox); // add the layout to the root group
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

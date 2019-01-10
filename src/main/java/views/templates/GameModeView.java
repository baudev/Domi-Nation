package views.templates;

import helpers.Screen;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.enums.GameMode;
import views.interfaces.OnGameModeClickListener;




public class GameModeView extends Parent {

    private OnGameModeClickListener onGameModeClickListener;

    public GameModeView() {
        // Define background

        Image background = new Image("/kingDomino.png");
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
        text.setText("Select the game mode");
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


        // Add the buttons of each modes
        int i = 0;
        for(models.enums.GameMode gameMode : models.enums.GameMode.values()){
            Button button = new Button();
            button.setPrefSize(150,40);
            button.setText(gameMode.toString());
            button.setLayoutX(10);
            button.setLayoutY(10);
            button.setTextFill(Color.WHITE);
            button.setStyle("-fx-background-color:#A52A2A");

            if(gameMode == GameMode.DYNASTY) {
                button.setDisable(true);
            }

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

    public OnGameModeClickListener getOnGameModeClickListener() {
        return onGameModeClickListener;
    }

    public void setOnGameModeClickListener(OnGameModeClickListener onGameModeClickListener) {
        this.onGameModeClickListener = onGameModeClickListener;
    }
}

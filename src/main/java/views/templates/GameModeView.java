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
        Image imageParchemin = new Image("/parchemin.png");
        ImageView parchemin = new ImageView(imageParchemin);
        stackPane1.getChildren().add(parchemin);
        stackPane1.setMinSize(Screen.percentageToXDimension(100), Screen.percentageToYDimension(100));
        stackPane1.setAlignment(Pos.CENTER);
        stackPane1.setTranslateY(Screen.percentageToYDimension(-20));
        this.getChildren().add(stackPane1);



        // Define title
        StackPane stackPane = new StackPane();
        Text text = new Text();
        text.setText("Select the game mode");
        text.setFont(Font.font("null", FontWeight.BOLD,35));
        text.setTextAlignment(TextAlignment.CENTER);
        stackPane.getChildren().add(text);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setMinSize(Screen.percentageToXDimension(100), Screen.percentageToYDimension(50)); // define the size of the stackpane layout

        this.getChildren().add(stackPane);


        // Define layout
        HBox hBox = new HBox();  // create an Hbox to align horizontally the button
        hBox.setSpacing(20);    // set the space between each button


        // Add the buttons of each modes
        int i = 0;
        for(models.enums.GameMode gameMode : models.enums.GameMode.values()){
            ButtonView buttonView = new ButtonView(gameMode.toString());
            buttonView.setLayoutX(10);
            buttonView.setLayoutY(10);


            if(gameMode == GameMode.DYNASTY) {
                buttonView.setDisable(true);
            }

            buttonView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) { // when the user click on the following button
                    getOnGameModeClickListener().onGameModeClickListener(gameMode); // transmit the event to the view controller callback
                }
            });

            buttonView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {

                }
            });
            buttonView.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {

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

    public OnGameModeClickListener getOnGameModeClickListener() {
        return onGameModeClickListener;
    }

    public void setOnGameModeClickListener(OnGameModeClickListener onGameModeClickListener) {
        this.onGameModeClickListener = onGameModeClickListener;
    }
}

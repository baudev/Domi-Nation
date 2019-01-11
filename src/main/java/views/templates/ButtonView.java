package views.templates;

import helpers.Screen;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ButtonView extends Parent {
    public ButtonView(String buttonText,boolean smallSize) {
        StackPane stackPane = new StackPane();
        Rectangle buttonBackground;
        if(smallSize) {                     //  add a small size to the button
            stackPane.setPrefSize(90,40.5);
            buttonBackground = new Rectangle(90,40.5);
        }
        else{ stackPane.setPrefSize(200,70);    //  add a normal size to the button
            buttonBackground = new Rectangle(200,70);
        }
        stackPane.setPrefSize(200,70);
        Image background = new Image("/bannerButton.png");
        buttonBackground.setFill(new ImagePattern(background));
        buttonBackground.setTranslateY(2);
        stackPane.getChildren().add(buttonBackground);
        Text text = new Text(buttonText);
        text.setFill(Color.GOLD);
        stackPane.getChildren().add(text);
        text.setTranslateX(stackPane.getWidth()/2);
        text.setTranslateY(stackPane.getHeight()/2);
        this.getChildren().add(stackPane);



    }


}

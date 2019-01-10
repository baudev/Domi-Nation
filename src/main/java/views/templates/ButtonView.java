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
    public ButtonView(String buttonText) {
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(200,70);
        Rectangle buttonBackground = new Rectangle(200,70);
        Image background = new Image("/bannerButton4.png");
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

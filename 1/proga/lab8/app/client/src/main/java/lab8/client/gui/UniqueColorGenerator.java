package lab8.client.gui;
import javafx.scene.paint.Color;

public class UniqueColorGenerator {
    public static Color generateColor(String userId) {
        int id = Integer.parseInt(userId);

        //create integer color as much as you want,
        Color[] colors = {Color.BLUE, Color.CYAN, Color.MAGENTA, Color.BISQUE, Color.AZURE, Color.BLUEVIOLET, Color.DARKGREEN, Color.YELLOW, Color.HONEYDEW};
        int colorLength = colors.length - 1;
        int randomNumber = id % colorLength;
        return colors[randomNumber];
    }
}

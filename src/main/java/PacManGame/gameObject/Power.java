package PacManGame.gameObject;

import PacManGame.gameObject.data.GameObjectID;
import javafx.scene.paint.Color;

public class Power extends AbstractGameObject{

    public final static int points = 200;

    public Power(double x, double y, int size) {
        super(x, y, size);
        this.objectOnMapVisualisation.setFill(Color.WHITE);
        this.id = GameObjectID.POWER;
    }
}

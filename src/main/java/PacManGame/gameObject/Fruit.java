package PacManGame.gameObject;

import PacManGame.gameObject.data.GameObjectID;
import javafx.scene.paint.Color;

public class Fruit extends AbstractGameObject {

    public final static int points = 100;

    public Fruit(double x, double y, int size) {
        super(x, y, size);
        this.objectOnMapVisualisation.setFill(Color.RED);
        this.id = GameObjectID.FRUIT;
    }
}

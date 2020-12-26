package PacManGame.gameObject;

import PacManGame.gameObject.data.GameObjectID;
import javafx.scene.paint.Color;

public class Dot extends AbstractGameObject {

    public final static int points = 10;

    public Dot(double x, double y, int size) {
        super(x, y, size);
        this.objectOnMapVisualisation.setFill(Color.WHITE);
        this.id = GameObjectID.DOT;
    }
}

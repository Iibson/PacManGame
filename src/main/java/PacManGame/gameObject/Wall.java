package PacManGame.gameObject;

import PacManGame.gameObject.data.GameObjectID;
import javafx.scene.paint.Color;

public class Wall extends AbstractGameObject {

    public Wall(double x, double y, int size) {
        super(x, y, size);
        this.objectOnMapVisualisation.setFill(Color.DARKBLUE);
        this.id = GameObjectID.WALL;
    }
}

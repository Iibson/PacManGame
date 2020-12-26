package PacManGame.gameObject;

import PacManGame.gameObject.data.GameObjectID;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class AbstractGameObject implements IGameObject {

    Rectangle objectOnMapVisualisation;
    GameObjectID id;
    int size;

    AbstractGameObject(double x, double y, int size) {
        this.size = size;
        this.objectOnMapVisualisation = new Rectangle(x, y, size, size);
    }

    @Override
    public void removedFromMap() {
        this.objectOnMapVisualisation.setFill(Color.BLACK);
    }

    @Override
    public Rectangle getObjectOnMapVisualisation() {
        return objectOnMapVisualisation;
    }
}

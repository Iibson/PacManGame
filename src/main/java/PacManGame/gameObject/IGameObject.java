package PacManGame.gameObject;

import javafx.scene.shape.Rectangle;

public interface IGameObject {

    void removedFromMap();

    Rectangle getObjectOnMapVisualisation();
}

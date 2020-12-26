package PacManGame.gameObject;

import PacManGame.gameMap.GameMap;
import PacManGame.gameObject.data.GameObjectID;
import PacManGame.gameObject.data.Position;
import javafx.scene.paint.Color;

public class Player extends AbstractMovingGameObject {

    public Player(double x, double y, int size, GameMap gameMap) {
        super(x, y, size, gameMap);
        this.objectOnMapVisualisation.setFill(Color.YELLOW);
        this.id = GameObjectID.PLAYER;
    }

    public Position getPlayerPosition() {
        return new Position(this.objectOnMapVisualisation.getX(), this.objectOnMapVisualisation.getY());
    }
}

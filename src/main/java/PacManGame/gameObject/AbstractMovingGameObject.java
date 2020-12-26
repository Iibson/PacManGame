package PacManGame.gameObject;

import PacManGame.gameMap.GameMap;
import PacManGame.gameObject.data.GameObjectMoveDirection;
import PacManGame.gameObject.data.Position;
import javafx.scene.shape.Rectangle;

public abstract class AbstractMovingGameObject extends AbstractGameObject implements IGameMovingObject {

    private GameObjectMoveDirection currentDirection;
    private GameObjectMoveDirection nextWantedDirection;
    private GameMap gameMap;

    AbstractMovingGameObject(double x, double y, int size, GameMap gameMap) {
        super(x, y, size);
        this.currentDirection = GameObjectMoveDirection.STATIONARY;
        this.nextWantedDirection = GameObjectMoveDirection.STATIONARY;
        this.gameMap = gameMap;
    }

    @Override
    public void move() {
        doMovement(this.nextWantedDirection.toPosition(),false);
        if (this.currentDirection.equals(this.nextWantedDirection))
            return;
        doMovement(this.currentDirection.toPosition(),true);
    }

    private void doMovement(Position position, boolean isCurrent){
        if (gameMap.canMoveTo(new Rectangle(this.objectOnMapVisualisation.getX() + position.x, this.objectOnMapVisualisation.getY() + position.y, this.size, this.size))) {
            this.objectOnMapVisualisation.setX(this.objectOnMapVisualisation.getX() + position.x);
            this.objectOnMapVisualisation.setY(this.objectOnMapVisualisation.getY() + position.y);
            if(!isCurrent)
                this.currentDirection = this.nextWantedDirection;
        }
    }

    @Override
    public void positionChanged(double x, double y) {
        this.objectOnMapVisualisation.setX(x);
        this.objectOnMapVisualisation.setY(y);
    }

    public void setNextWantedDirection(GameObjectMoveDirection nextWantedDirection) {
        this.nextWantedDirection = nextWantedDirection;
    }

    GameMap getGameMap() {
        return this.gameMap;
    }

    GameObjectMoveDirection getNextWantedDirection() {
        return this.nextWantedDirection;
    }
}

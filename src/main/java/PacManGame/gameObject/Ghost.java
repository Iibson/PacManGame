package PacManGame.gameObject;

import PacManGame.gameMap.GameMap;
import PacManGame.gameObject.data.GameObjectID;
import PacManGame.gameObject.data.GameObjectMoveDirection;
import PacManGame.gameObject.data.Position;
import javafx.scene.paint.Color;

import java.util.Random;

public class Ghost extends AbstractMovingGameObject {

    private boolean isSacred;
    private int scaredTimer;
    public final static int points = 400;

    public Ghost(double x, double y, int size, GameMap gameMap) {
        super(x, y, size, gameMap);
        this.objectOnMapVisualisation.setFill(Color.RED);
        this.id = GameObjectID.GHOST;
        this.isSacred = false;
    }

    public void setDirectionToPlayer() {
        Position playerPosition = this.getGameMap().getPlayerPosition();
        if (new Random().nextBoolean()) {
            if (playerPosition.x > this.objectOnMapVisualisation.getX())
                this.setNextWantedDirection(GameObjectMoveDirection.RIGHT);
            else if (playerPosition.x < this.objectOnMapVisualisation.getX())
                this.setNextWantedDirection(GameObjectMoveDirection.LEFT);
        } else {
            if (playerPosition.y > this.objectOnMapVisualisation.getY())
                this.setNextWantedDirection(GameObjectMoveDirection.BOTTOM);
            else if (playerPosition.y < this.objectOnMapVisualisation.getY())
                this.setNextWantedDirection(GameObjectMoveDirection.TOP);
        }
    }

    private void reduceTimer() {
        scaredTimer -= 1;
        if(scaredTimer == 0)
            setIsScared(false);
    }

    @Override
    public void move() {
        if(isSacred)
            reduceTimer();
        Position tempPosition = new Position(this.objectOnMapVisualisation.getX(), this.objectOnMapVisualisation.getY());
        super.move();
        if (tempPosition.equals(new Position(this.objectOnMapVisualisation.getX(), this.objectOnMapVisualisation.getY()))) {
            setDirectionToPlayer();
            super.move();
        }
        if (tempPosition.equals(new Position(this.objectOnMapVisualisation.getX(), this.objectOnMapVisualisation.getY()))) {
            setNextWantedDirection(getNextWantedDirection().opposite());
            super.move();
        }
    }

    public void setIsScared(boolean isSacred) {
        if (isSacred)
            this.objectOnMapVisualisation.setFill(Color.CYAN);
        else
            this.objectOnMapVisualisation.setFill(Color.RED);
        this.isSacred = isSacred;
    }

    public boolean isSacred() {
        return this.isSacred;
    }

    public void setScaredTimer(int scaredTimer) {
        this.scaredTimer = scaredTimer;
    }
}

package PacManGame.gameObject;

import PacManGame.gameObject.data.GameObjectMoveDirection;

public interface IGameMovingObject extends IGameObject {

    void move();

    void positionChanged(double x, double y);

    void setNextWantedDirection(GameObjectMoveDirection gameObjectMoveDirection);
}


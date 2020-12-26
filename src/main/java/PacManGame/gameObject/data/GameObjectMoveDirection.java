package PacManGame.gameObject.data;

public enum GameObjectMoveDirection {
    TOP,
    RIGHT,
    BOTTOM,
    LEFT,
    STATIONARY;

    public GameObjectMoveDirection opposite() {
        switch (this) {
            case RIGHT:
                return LEFT;
            case LEFT:
                return RIGHT;
            case TOP:
                return BOTTOM;
            case BOTTOM:
                return TOP;
            default:
                return STATIONARY;
        }
    }

    public Position toPosition() {
        switch (this) {
            case STATIONARY:
                return new Position(0, 0);
            case TOP:
                return new Position(0, -1);
            case LEFT:
                return new Position(-1, 0);
            case RIGHT:
                return new Position(1, 0);
            default:
                return new Position(0, 1);
        }
    }
}

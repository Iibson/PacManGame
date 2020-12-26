package PacManGame.gameMap;

import PacManGame.gameObject.*;
import PacManGame.gameObject.IGameMovingObject;
import PacManGame.gameObject.data.GameObjectMoveDirection;
import PacManGame.gameObject.data.Position;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMap {
    private List<IGameObject> stillGameObjects;
    private List<IGameObject> walls;
    private List<IGameMovingObject> ghosts;
    private List<Position> potentialFruitsPositions;
    private IGameMovingObject player;
    private int points;
    private int lives;
    private int level;
    private IGameMapObserver observer;
    private Position ghostSpawn;
    private Group group;

    public GameMap(IGameMapObserver observer, int level, int lives, int points) {
        this.lives = lives;
        this.points = points;
        this.observer = observer;
        this.level = level;
        this.group = new Group();
        setGameMapObjects(false);
    }

    public Group drawGroup() {
        stillGameObjects.forEach(iGameObject -> group.getChildren().add(iGameObject.getObjectOnMapVisualisation()));
        ghosts.forEach(iGameObject -> group.getChildren().add(iGameObject.getObjectOnMapVisualisation()));
        walls.forEach(iGameObject -> group.getChildren().add(iGameObject.getObjectOnMapVisualisation()));
        group.getChildren().add(player.getObjectOnMapVisualisation());
        return group;
    }

    public void setPlayerDirection(GameObjectMoveDirection gameObjectMoveDirection) {
        this.player.setNextWantedDirection(gameObjectMoveDirection);
    }

    public Position getPlayerPosition() {
        return ((Player) player).getPlayerPosition();
    }

    public boolean canMoveTo(Rectangle rectangle) {
        for (IGameObject wall : walls)
            if (wall.getObjectOnMapVisualisation().getBoundsInParent().intersects(rectangle.getBoundsInParent()))
                return false;
        return true;
    }

    public void gameLoop() {
        moveGameObjects();
        executeCollisions();
        spawnFruits();
        checkWinCondition();
    }

    private void spawnFruits() {
        if (new Random().nextInt(500) != 50 || potentialFruitsPositions.size() == 0)
            return;
        Position position = this.potentialFruitsPositions.get(new Random().nextInt(this.potentialFruitsPositions.size()));
        this.stillGameObjects.add(new Fruit(position.x - 3, position.y - 3, 10));
        this.group.getChildren().add(stillGameObjects.get(stillGameObjects.size() - 1).getObjectOnMapVisualisation());
        stillGameObjects.get(stillGameObjects.size() - 1).getObjectOnMapVisualisation().toBack();
    }

    private void checkWinCondition() {
        if (this.stillGameObjects.size() == 0)
            observer.gameWon();
    }

    private void executeCollisions() {
        IGameObject gameObject = checkCollisions();
        if (gameObject == null)
            return;
        if (Ghost.class.equals(gameObject.getClass()))
            ghostEncounter(gameObject);
        else
            consume(gameObject);
    }

    private void ghostEncounter(IGameObject gameObject) {
        if (((Ghost) gameObject).isSacred())
            eatGhost(((Ghost) gameObject));
        else
            die();
    }

    private void eatGhost(Ghost ghost) {
        points += Ghost.points;
        ghost.getObjectOnMapVisualisation().setX(this.ghostSpawn.x);
        ghost.getObjectOnMapVisualisation().setY(this.ghostSpawn.y);
        ghost.setIsScared(false);
    }

    private void consume(IGameObject gameObject){
        if (Fruit.class.equals(gameObject.getClass())) {
            points += Fruit.points;
            potentialFruitsPositions.add(new Position(gameObject.getObjectOnMapVisualisation().getX(), gameObject.getObjectOnMapVisualisation().getY()));
        } else if (Dot.class.equals(gameObject.getClass())) {
            points += Dot.points;
            potentialFruitsPositions.add(new Position(gameObject.getObjectOnMapVisualisation().getX(), gameObject.getObjectOnMapVisualisation().getY()));
        } else if (Power.class.equals(gameObject.getClass())) {
            points += Power.points;
            scareGhosts();
        }
        stillGameObjects.remove(gameObject);
        group.getChildren().remove(gameObject.getObjectOnMapVisualisation());
    }

    private void scareGhosts() {
        ghosts.forEach(ghost -> {
            ((Ghost) ghost).setIsScared(true);
            ((Ghost) ghost).setScaredTimer(Math.max(50, 500 - 50 * this.level));
        });
    }

    private void die() {
        this.lives--;
        setGameMapObjects(true);
        if (lives == 0)
            this.observer.gameLost();
    }


    private void moveGameObjects() {
        this.ghosts.forEach(ghost -> {
            for (int i = 0; i < (((Ghost) ghost).isSacred() ? 1 : this.level); i++)
                ghost.move();
        });
        for (int i = 0; i < 3; i++)
            player.move();
    }

    private IGameObject checkCollisions() {
        for (IGameMovingObject gameObject : ghosts)
            if ((gameObject).getObjectOnMapVisualisation().getBoundsInParent().intersects(player.getObjectOnMapVisualisation().getBoundsInParent()))
                return gameObject;
        for (IGameObject gameObject : stillGameObjects)
            if (gameObject.getObjectOnMapVisualisation().getBoundsInParent().intersects(player.getObjectOnMapVisualisation().getBoundsInParent()))
                return gameObject;
        return null;
    }

    public String getInfoAboutGame() {
        return "Points: " + this.points + " Lives: " + this.lives;
    }

    public int getLives() {
        return this.lives;
    }

    public int getPoints() {
        return this.points;
    }

    private void setGameMapObjects(boolean isReloading) {
        if (!isReloading) {
            this.stillGameObjects = new ArrayList<>();
            this.walls = new ArrayList<>();
            this.ghosts = new ArrayList<>();
        }
        this.potentialFruitsPositions = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("lvl.txt");
            double currWidth = 10;
            double currHeight = 10;
            int field;
            int ghostCounter = 0;
            while ((field = fileReader.read()) != -1) {
                switch (field) {
                    case 87: //Wall
                        if (!isReloading)
                            this.walls.add(new Wall(currWidth, currHeight, 20));
                        currWidth += 20;
                        break;
                    case 80: //Player
                        if (!isReloading)
                            this.player = new Player(currWidth + 1, currHeight + 1, 18, this);
                        else {
                            this.player.getObjectOnMapVisualisation().setX(currWidth + 1);
                            this.player.getObjectOnMapVisualisation().setY(currHeight + 1);
                        }
                        currWidth += 20;
                        break;
                    case 10: //End Line
                        currHeight += 20;
                        currWidth = 10;
                        break;
                    case 83: //Player
                        if (!isReloading)
                            this.stillGameObjects.add(new Dot(currWidth + 8, currHeight + 8, 4));
                        currWidth += 20;
                        break;
                    case 81: //Power
                        if (!isReloading)
                            this.stillGameObjects.add(new Power(currWidth + 5, currHeight + 5, 10));
                        currWidth += 20;
                        break;
                    case 71: //Ghost
                        if (!isReloading)
                            this.ghosts.add(new Ghost(currWidth + 1, currHeight + 1, 18, this));
                        else {
                            this.ghosts.get(ghostCounter).getObjectOnMapVisualisation().setX(currWidth + 1);
                            this.ghosts.get(ghostCounter).getObjectOnMapVisualisation().setY(currHeight + 1);
                            ghostCounter++;
                        }
                        currWidth += 20;
                        break;
                    case 82: //ghost spawning area
                        this.ghostSpawn = new Position(currWidth, currHeight);
                        currWidth += 20;
                        break;
                    case 32:
                    case 13: //white space or return or Empty Space
                        break;
                    default:
                        currWidth += 20;
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ghosts.forEach(ghost -> {
            ((Ghost) ghost).setDirectionToPlayer();
            ((Ghost) ghost).setIsScared(false);
        });
    }

}

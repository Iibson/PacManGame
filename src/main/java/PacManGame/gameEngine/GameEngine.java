package PacManGame.gameEngine;

import PacManGame.gameEngine.data.EngineStatus;
import PacManGame.gameMap.GameMap;
import PacManGame.gameMap.IGameMapObserver;
import PacManGame.gameObject.data.GameObjectMoveDirection;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class GameEngine implements IGameMapObserver {
    private GameMap gameMap;
    private IGameEngineObserver observer;
    private Label stats;
    private Label info;

    private int level = 1;

    public GameEngine(IGameEngineObserver observer){
        this.observer = observer;
        prepLevel();
        prepLabel();
    }

    private void prepLabel() {
        this.stats = new Label();
        this.stats.setLayoutX(10);
        this.stats.setLayoutY(10);
        this.stats.setTextFill(Color.YELLOW);

        this.info = new Label();
        this.info.setLayoutX(50);
        this.info.setLayoutY(150);
        this.info.setTextFill(Color.YELLOW);
    }

    public Scene drawScene(EngineStatus engineStatus){
        Group group;
        switch (engineStatus) {
            case RUNNING:
                group = this.gameMap.drawGroup();
                group.getChildren().add(stats);
                break;
            case STARTSCREEN:
                group = new Group();
                group.getChildren().add(info);
                info.setText("PACMAN \n PORSZAJ SIĘ WSAD ALBO STRZAŁKAMI \n ZBIERAJ PUNKTY I OWOCE ABY PRZEJŚĆ DALEJ \n UWAŻAJ NA DUCHY! \n NACIŚNIJ DOWOLNY KLAWISZ ABY ROZPOCZĄĆ");
                break;
            default:
                group = new Group();
                group.getChildren().add(info);
                info.setText("PORAŻKA \n twoje statystyki" + this.gameMap.getInfoAboutGame() + "\n NACISNIJ DOWOLNY KLAWISZ ABY SPROBOWAC PONOWNIE");
                break;
        }
        Scene scene =  new Scene(group, 400, 440);
        scene.setFill(Color.BLACK);

        return scene;
    }

    private void prepLevel(){
        this.gameMap = new GameMap(this, 1, 5, 0);
    }

    public void play(){
        this.gameMap.gameLoop();
        this.stats.setText("Level: " + this.level + " " + this.gameMap.getInfoAboutGame());
    }

    public void setMovement(GameObjectMoveDirection gameObjectMoveDirection) {
        this.gameMap.setPlayerDirection(gameObjectMoveDirection);
    }

    @Override
    public void gameLost() {
        observer.changedView(EngineStatus.DEFETESCREEN);
    }

    @Override
    public void gameWon() {
        this.level++;
        this.gameMap = new GameMap(this, level, gameMap.getLives(), gameMap.getPoints());
        observer.changedView(EngineStatus.RUNNING);
    }
}

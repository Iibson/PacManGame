package PacManGame;

import PacManGame.gameEngine.GameEngine;
import PacManGame.gameEngine.IGameEngineObserver;
import PacManGame.gameEngine.data.EngineStatus;
import PacManGame.gameObject.data.GameObjectMoveDirection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application implements IGameEngineObserver, EventHandler<KeyEvent> {

    private Stage window;
    private GameEngine engine;
    private EngineStatus engineStatus;

    @Override
    public void start(Stage stage) {
        engine = new GameEngine(this);
        engineStatus = EngineStatus.STARTSCREEN;
        window = stage;
        Scene scene = engine.drawScene(engineStatus);
        scene.setOnKeyPressed(this);
        window.setScene(scene);
        window.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(30), event -> engine.play()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void changedView(EngineStatus engineStatus) {
        if(engineStatus != EngineStatus.RUNNING)
            engine = new GameEngine(this);
        this.engineStatus = engineStatus;
        Scene scene = engine.drawScene(engineStatus);
        scene.setOnKeyPressed(this);
        window.setScene(scene);
    }

    @Override
    public void handle(KeyEvent event) {
        if(engineStatus != EngineStatus.RUNNING)
            changedView(EngineStatus.RUNNING);
        switch (event.getCode()) {
            case A:
            case LEFT:
                engine.setMovement(GameObjectMoveDirection.LEFT);
                break;
            case W:
            case UP:
                engine.setMovement(GameObjectMoveDirection.TOP);
                break;
            case D:
            case RIGHT:
                engine.setMovement(GameObjectMoveDirection.RIGHT);
                break;
            case S:
            case DOWN:
                engine.setMovement(GameObjectMoveDirection.BOTTOM);
                break;
        }
    }
}
package PacManGame.gameEngine;

import PacManGame.gameEngine.data.EngineStatus;
import javafx.scene.Scene;

public interface IGameEngineObserver {

    void changedView(EngineStatus engineStatus);
}

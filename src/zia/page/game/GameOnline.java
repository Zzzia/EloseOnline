package zia.page.game;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import zia.shape.BaseShape;
import zia.shape.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class GameOnline {
    private int map[][] = new int[Config.y][Config.x];
    private BaseShape shape;
    private List<Position> tempPositions = new ArrayList<>();
    private final int width = Config.width;
    private final int height = Config.height;
    private Timer timer;
    private int score = 0;
    private int time = 400;
    private Canvas gameCanvas;
    private Stage stage;
    private EndListener endListener;

    private Scene gameScene;
    private GraphicsContext gc;
    private double perSize;
}

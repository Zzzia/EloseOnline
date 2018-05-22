package zia.page.game;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import zia.bean.MapWarper;
import zia.server.Client;
import zia.shape.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class Game {

    private boolean isEnd;

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

    private boolean isSingle;

    public Game(boolean isSingle) {
        this.isSingle = isSingle;
        stage = new Stage();
        stage.setTitle("elose");
        if (!isSingle){
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            double width = bounds.getMaxX();
            stage.setX(width / 2 - this.width / 2);
        }
        gameCanvas = new Canvas();
        gameCanvas.setWidth(width);
        gameCanvas.setHeight(height);
        Pane pane = new Pane();
        pane.getChildren().add(gameCanvas);
        gameScene = new Scene(pane, width, height);
        stage.setScene(gameScene);
        perSize = width / Config.x;
        gc = gameCanvas.getGraphicsContext2D();
        initShape();
        setKeyBoard();
        isEnd = false;
        timer = new Timer();
        stage.show();
    }

    public void setEndListener(EndListener endListener) {
        this.endListener = endListener;
        stage.setOnCloseRequest(event -> {
            isEnd = true;
            if (!isSingle)
                Client.getInstance().quit();
            endListener.onEnd(score);
        });
    }

    public void begin() {
        score = 0;
        time = 500;
        autoDown();
    }

    public void close() {
        stage.close();
    }

    private void autoDown() {
        new Thread(() -> {
            while (!isEnd) {
                if (!shape.goDown()) {
                    shape.getShape()
                            .forEach(position -> map[position.getY()][position.getX()] = getColor(shape));
                    tempPositions.clear();
                    clearMap();
                    initShape();
                }
                invalidate();
                try {
                    Thread.sleep(time--);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Thread.yield();
        }).start();
    }

    private void initShape() {
        try {
            create();
            invalidate();
        } catch (Exception e) {
            isEnd = true;
            timer.cancel();
            clearKeyBoard();
            if (!isSingle)
                Client.getInstance().sendData("end");
            if (endListener != null) {
                Platform.runLater(() -> {
                    endListener.onEnd(score);
                });
            }
        }
    }

    private void create() throws Exception {
        int k = Math.abs(new Random().nextInt()) % 7;
        switch (k) {
            case 0:
                shape = new Line(map);
                break;
            case 1:
                shape = new L1(map);
                break;
            case 2:
                shape = new L2(map);
                break;
            case 3:
                shape = new T(map);
                break;
            case 4:
                shape = new Z1(map);
                break;
            case 5:
                shape = new Z2(map);
                break;
            case 6:
                shape = new TI(map);
                break;
        }
    }

    /**
     * 刷新ui
     */
    public void invalidate() {
        if (isEnd) return;
        //在数组中消除原来的方块
        for (Position p : tempPositions) {
            map[p.getY()][p.getX()] = Config.EMPTY;
        }
        tempPositions = shape.getShape();
        //将变化后的方块放入数组
        for (Position p : tempPositions) {
            map[p.getY()][p.getX()] = Config.TEMP;
        }

        //刷新ui
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                double x = j * perSize;
                double y = i * perSize;
                if (map[i][j] == Config.TEMP) {
                    gc.setFill(Color.valueOf(Config.color[getColor(shape)]));
                } else if (map[i][j] == Config.LINE) {
                    gc.setFill(Color.valueOf(Config.color[1]));
                } else if (map[i][j] == Config.L1) {
                    gc.setFill(Color.valueOf(Config.color[2]));
                } else if (map[i][j] == Config.L2) {
                    gc.setFill(Color.valueOf(Config.color[3]));
                } else if (map[i][j] == Config.T) {
                    gc.setFill(Color.valueOf(Config.color[4]));
                } else if (map[i][j] == Config.TI) {
                    gc.setFill(Color.valueOf(Config.color[5]));
                } else if (map[i][j] == Config.Z1) {
                    gc.setFill(Color.valueOf(Config.color[6]));
                } else if (map[i][j] == Config.Z2) {
                    gc.setFill(Color.valueOf(Config.color[7]));
                }
                gc.fillRect(x + 1, y + 1, perSize - 1, perSize - 1);
                if (map[i][j] == Config.EMPTY) {
                    gc.clearRect(x + 1, y + 1, perSize - 1, perSize - 1);
                }
            }
        }
        gc.fillText("得分：" + score, width - 100, 30);
        MapWarper mapWarper = new MapWarper();
        mapWarper.setScore(score);
        mapWarper.setMap(map);
        if (!isSingle)
            Client.getInstance().sendData(new Gson().toJson(mapWarper));
    }

    private void printMap(int map[][]) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("------");
    }

    private void clearKeyBoard() {
        gameScene.setOnKeyPressed(null);
    }

    private void setKeyBoard() {
        gameScene.setOnKeyPressed(event -> {
            switch (event.getText().toLowerCase()) {
                case "a":
                    shape.goLeft();
                    break;
                case "w":
                    shape.change();
                    break;
                case "s":
                    shape.goEnd();
                    shape.getShape()
                            .forEach(position -> map[position.getY()][position.getX()] = getColor(shape));
                    for (Position p : tempPositions) {
                        map[p.getY()][p.getX()] = Config.EMPTY;
                    }
                    tempPositions.clear();
                    clearMap();
                    initShape();
                    break;
                case "d":
                    shape.goRight();
                    break;
            }
            invalidate();
        });
    }

    private int getColor(BaseShape shape) {
        if (shape instanceof L1)
            return Config.L1;
        else if (shape instanceof L2)
            return Config.L2;
        else if (shape instanceof Line)
            return Config.LINE;
        else if (shape instanceof T)
            return Config.T;
        else if (shape instanceof TI)
            return Config.TI;
        else if (shape instanceof Z1)
            return Config.Z1;
        else if (shape instanceof Z2)
            return Config.Z2;
        else return 1;
    }

    /**
     * 消除整行
     */
    private void clearMap() {
        for (int i = 0; i < Config.y; i++) {
            int j = 0;
            for (; j < Config.x; j++) {
                if (map[i][j] == Config.EMPTY)
                    break;
            }
            if (j == Config.x) {
                map[i][0] = Config.CLEAR;
            }
        }
        for (int i = 0; i < Config.y; i++) {
            if (map[i][0] == Config.CLEAR) {
                score = score + 10;
                for (int j = i; j > 0; j--) {
                    for (int k = 0; k < Config.x; k++) {
                        map[j][k] = map[j - 1][k];
                    }
                }
                for (int j = 0; j < Config.x; j++) {
                    map[0][j] = Config.EMPTY;
                }
            }
        }
    }
}
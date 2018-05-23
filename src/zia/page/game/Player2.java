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
import zia.server.MessageListener;
import zia.util.UserRes;

public class Player2 implements MessageListener {

    private final int width = Config.width;
    private final int height = Config.height;
    private int score = 0;
    private Canvas gameCanvas;
    private Stage stage;
    private EndListener endListener;
    private String name = "";

    private Scene gameScene;
    private GraphicsContext gc;
    private double perSize;

    public Player2(String name) {
        this.name = name;
        stage = new Stage();
        stage.setTitle("player 2");
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double w = bounds.getMaxX();
        stage.setX(w / 2 + this.width / 2);
        Client.getInstance().setMessageListener(this);
        gameCanvas = new Canvas();
        gameCanvas.setWidth(width);
        gameCanvas.setHeight(height);
        Pane pane = new Pane();
        pane.getChildren().add(gameCanvas);
        gameScene = new Scene(pane, width, height);
        stage.setScene(gameScene);
        perSize = width / Config.x;
        gc = gameCanvas.getGraphicsContext2D();
        stage.show();
    }

    public void setEndListener(EndListener endListener) {
        this.endListener = endListener;
        stage.setOnCloseRequest(event -> {
            endListener.onEnd(score);
        });
    }

    public void close() {
        stage.close();
    }

    /**
     * 刷新ui
     */
    public void invalidate(int map[][]) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                double x = j * perSize;
                double y = i * perSize;
                if (map[i][j] == Config.TEMP) {
                    gc.setFill(Color.valueOf(Config.color[0]));
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
        gc.fillText("玩家：" + name, 50, 30);
    }

    @Override
    public void onRoomLinked() {

    }

    @Override
    public void onRoomCreated(int roomId) {

    }

    @Override
    public void onOthersJoin(String name) {

    }

    @Override
    public void onJoinSuccess() {

    }

    @Override
    public void onQuit() {
        if (endListener != null)
            endListener.onEnd(score);
    }

    @Override
    public void onGameBegin() {

    }


    private boolean isEnd = false;

    @Override
    public void onGameDataGet(String name, String msg) {
        this.name = name;
        if (name.equals(UserRes.instance.getUserData().getNickname())) return;
        if (msg.length() <= 1) return;
        System.out.println("onGameDataGet  " + msg);
        if (msg.equals("end")) {//他人游戏结束
            if (endListener != null && !isEnd) {
                Platform.runLater(() -> {
                    endListener.onEnd(score);
                    isEnd = true;
                });
            }
        } else {//接受map数据
            MapWarper mapWarper = new Gson().fromJson(msg, MapWarper.class);
            score = mapWarper.getScore();
            invalidate(mapWarper.getMap());
        }
    }
}

package zia.page.room;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import zia.page.game.Player2;
import zia.page.game.Game;
import zia.server.Client;
import zia.server.MessageListener;
import zia.util.UserRes;

/**
 * 创建房间
 */
public class RoomPage implements MessageListener {

    public static final int CREATE = -1;
    private Text status = new Text("正在创建房间");
    private Button begin = new Button("开始游戏");
    private Text message = new Text("等待加入");
    private String name = "";
    private Stage stage;


    public RoomPage(int roomNumber) {
        stage = new Stage();
        stage.setWidth(300);
        stage.setHeight(300);
        stage.setTitle("准备开始");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        if (roomNumber != CREATE) {
            onOthersJoin(UserRes.instance.getUserData().getNickname());
        } else {//选择了创建房间
            Client.getInstance().introduce();
            Client.getInstance().create();
            begin.setOnAction(event -> message.setText("人数不足，无法开始"));
        }

        Client.getInstance().setMessageListener(this);

        grid.add(status, 0, 1, 2, 1);
        grid.add(message, 0, 2, 2, 1);
        grid.add(begin, 0, 3);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void onRoomLinked() {

    }

    @Override
    public void onRoomCreated(int roomId) {
        status.setText("房间号：" + roomId);
    }

    @Override
    public void onOthersJoin(String name) {
        status.setFill(Color.GREEN);
        status.setText("等待开始");
        message.setText(name + "加入了房间");
        this.name = name;
        begin.setOnAction(event -> {
            Client.getInstance().begin();
            onGameBegin();
        });
    }

    @Override
    public void onJoinSuccess() {

    }

    @Override
    public void onQuit() {

    }

    private int endCount = 0;

    @Override
    public void onGameBegin() {
        System.out.println("onGameBegin");
        Platform.runLater(() -> {
            endCount = 0;
            stage.hide();

            Player2 player2 = new Player2(name);
            Game game = new Game(false);
            game.begin();
            game.setEndListener(score -> {
                Client.getInstance().sendData("end" + " " + score);
                endCount++;
                close(game, player2);
            });
            player2.setEndListener(score -> {
                endCount++;
                close(game, player2);
            });
        });
    }

    private void close(Game game, Player2 player2) {
        if (endCount == 2) {
            Client.getInstance().quit();
            game.close();
            player2.close();
            stage.close();
        }
    }

    @Override
    public void onGameDataGet(String msg) {

    }
}
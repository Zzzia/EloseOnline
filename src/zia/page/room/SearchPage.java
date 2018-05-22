package zia.page.room;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import zia.server.Client;
import zia.server.MessageListener;

/**
 * 搜索并加入房间
 */
public class SearchPage implements MessageListener {

    private int roomid = 0;
    private Stage stage;

    public SearchPage() {
        stage = new Stage();
        stage.setTitle("搜索房间");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        stage.setOnCloseRequest(event -> Client.getInstance().destroy());

        Client.getInstance().setMessageListener(this);
        Client.getInstance().introduce();

        Text title = new Text("输入房间号");
        TextField roomNumber = new TextField();
        Button bt = new Button("寻找");
        Text status = new Text();

        grid.add(title, 0, 0, 2, 1);
        grid.add(roomNumber, 0, 1, 2, 1);
        grid.add(bt, 1, 2);
        grid.add(status, 0, 2);

        bt.setOnAction(event -> {
            String room = roomNumber.getText();
            roomid = Integer.parseInt(room);
            Client.getInstance().join(roomid);
        });

        stage.setScene(new Scene(grid));
        stage.show();
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
        if (roomid != 0) {
            new RoomPage(roomid);
            stage.close();
        }
    }

    @Override
    public void onQuit() {

    }

    @Override
    public void onGameBegin() {

    }

    @Override
    public void onGameDataGet(String msg) {

    }
}

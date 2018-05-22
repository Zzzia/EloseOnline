package zia.page.score;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import zia.bean.RankGetResult;
import zia.server.Const;
import zia.server.NetThreadPool;
import zia.util.NetUtil;

import java.io.IOException;

public class Rank {

    private ListView<RankGetResult.DataBean> list = new ListView<>();
    private ObservableList<RankGetResult.DataBean> data = FXCollections.observableArrayList();

    public Rank() {
        Stage stage = new Stage();
        stage.setTitle("排行榜");
        VBox box = new VBox();
        Scene scene = new Scene(box, 300, 300);
        stage.setScene(scene);
        box.getChildren().addAll(list);
        VBox.setVgrow(list, Priority.ALWAYS);
        stage.show();
        NetThreadPool.instance.getExecutor().execute(() -> {
            try {
                RankGetResult result = new Gson().fromJson(NetUtil.getHtml(Const.RANK, "utf-8"), RankGetResult.class);
                if (result.getStatus() != 200) {
                    System.out.println("网络错误");
                    return;
                }
                Platform.runLater(() -> {
                    data.addAll(result.getData());
                    list.setItems(data);
                    list.setCellFactory((ListView<RankGetResult.DataBean> l) -> new RankCell());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private class RankCell extends ListCell<RankGetResult.DataBean> {

        @Override
        protected void updateItem(RankGetResult.DataBean item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null) return;
            Text score = new Text(100, 20, String.valueOf(item.getScore()));
            Text nickname = new Text(100, 20, item.getNickname());
            nickname.maxWidth(100);
            Text date = new Text(100, 20, item.getDate());
            GridPane gridPane = new GridPane();
            gridPane.getColumnConstraints().add(new ColumnConstraints(100));
            gridPane.getColumnConstraints().add(new ColumnConstraints(100));
            gridPane.getColumnConstraints().add(new ColumnConstraints(100));
            gridPane.add(score, 0, 0);
            gridPane.add(nickname, 1, 0);
            gridPane.add(date, 2, 0);
            setGraphic(gridPane);
        }
    }
}

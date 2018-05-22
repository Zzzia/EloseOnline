package zia.page.room;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import zia.server.Client;

import java.io.IOException;

public class ChoosePage {
    public ChoosePage() {
        Stage stage = new Stage();
        stage.setTitle("请选择");
        GridPane grid = new GridPane();

        stage.setOnCloseRequest(event -> Client.getInstance().destroy());

        try {
            Client.getInstance().init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(35, 35, 35, 35));

        Text search = new Text("寻找对局");
        Text create = new Text("创建对局");

        grid.add(search, 0, 0);
        grid.add(create, 0, 1);

        search.setOnMouseClicked(event -> {
            new SearchPage();
            stage.close();
        });
        create.setOnMouseClicked(event -> {
            new RoomPage(RoomPage.CREATE);
            stage.close();
        });

        stage.setScene(new Scene(grid));
        stage.show();
    }
}

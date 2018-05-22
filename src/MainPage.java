import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import zia.page.game.Game;
import zia.page.login.LoginPage;
import zia.page.room.ChoosePage;
import zia.page.score.Rank;
import zia.page.score.ScoreUpdater;
import zia.util.UserRes;

import java.io.IOException;


public class MainPage extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("开始游戏");
        Parent startParent = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        primaryStage.setScene(new Scene(startParent, 300, 500));
        Button startButton = (Button) startParent.lookup("#start");
        Button online = (Button) startParent.lookup("#online");
        Button rankButton = (Button) startParent.lookup("#rank");
        Button exitButton = (Button) startParent.lookup("#exit");
        Label label = (Label) startParent.lookup("#user");
        UserRes.UserData userData = UserRes.instance.getUserData();
        if (UserRes.instance.isLogin()) {
            label.setText("当前账号:" + userData.getNickname());
        } else {
            label.setText("没有登录账号");
        }
        userData.addObserver((o, arg) -> {
            UserRes.UserData data = (UserRes.UserData) arg;
            label.setText("当前账号:" + data.getNickname());
        });
        startButton.setOnAction(event -> {
            primaryStage.hide();
            Game game = new Game(true);
            game.setEndListener(score -> {
                System.out.println("end score = " + score);
                game.close();
                primaryStage.show();
                try {
                    new ScoreUpdater(score);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            game.begin();
        });
        online.setOnAction(event -> {
            if (UserRes.instance.isLogin()) {
                new ChoosePage();
            } else {
                new LoginPage();
            }
        });
        rankButton.setOnAction(event -> new Rank());
        exitButton.setOnAction(event -> new LoginPage());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

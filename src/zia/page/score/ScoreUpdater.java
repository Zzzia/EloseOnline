package zia.page.score;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import zia.bean.BaseResult;
import zia.server.Const;
import zia.server.NetThreadPool;
import zia.util.NetUtil;
import zia.util.UserRes;

import java.io.IOException;

public class ScoreUpdater {

    private Stage stage;
    private TextField textArea;

    public ScoreUpdater(int score) throws IOException {
        stage = new Stage();
        stage.setTitle("提交分数");
        Parent parent = FXMLLoader.load(getClass().getResource("scoreFXML.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        Text scoreText = (Text) parent.lookup("#scoreFXML_score");
        scoreText.setText("得分：" + score);
        textArea = (TextField) parent.lookup("#scoreFXML_nickname");
        Button button = (Button) parent.lookup("#scoreFXML_update");
        UserRes.UserData db = UserRes.instance.getUserData();
        if (db != null) {
            textArea.setText(db.getNickname());
        }

        button.setOnAction(event -> {
            String nickname = textArea.getText();
            NetThreadPool.instance.getExecutor().execute(() -> {
                NetUtil.ParametersBuilder parametersBuilder = new NetUtil.ParametersBuilder();
                parametersBuilder.put("nickname", nickname)
                        .put("appKey", Const.appKey)
                        .put("score", String.valueOf(score));
                if (db != null)
                    parametersBuilder.put("username", db.getUsername());
                try {
                    BaseResult result = new Gson().fromJson(NetUtil.postHtml(Const.RANK, parametersBuilder, "utf-8"), BaseResult.class);
                    if (result.getStatus() == 200) {
                        Platform.runLater(() -> {
                            stage.close();
                            new Rank();
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}

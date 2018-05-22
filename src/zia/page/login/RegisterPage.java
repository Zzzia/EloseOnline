package zia.page.login;

import com.google.gson.Gson;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import zia.bean.BaseResult;
import zia.server.Const;
import zia.util.NetUtil;

import java.io.IOException;

public class RegisterPage {

    public RegisterPage() {
        Stage stage = new Stage();
        stage.setTitle("注册");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label nickName = new Label("昵称:");
        grid.add(nickName, 0, 0);

        TextField nickTextField = new TextField();
        grid.add(nickTextField, 1, 0);

        Label userName = new Label("用户名:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("密码:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button registerBt = new Button("注册");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(registerBt);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        registerBt.setOnAction(event -> {
            NetUtil.ParametersBuilder parametersBuilder = new NetUtil.ParametersBuilder()
                    .put("appKey", Const.appKey)
                    .put("username", userTextField.getText())
                    .put("password", pwBox.getText())
                    .put("nickname", nickTextField.getText());
            try {
                BaseResult baseResult = new Gson().fromJson(NetUtil.postHtml(Const.REGISTER, parametersBuilder), BaseResult.class);
                if (baseResult.getStatus() == 200) {
                    stage.hide();
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText(baseResult.getInfo());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);

        stage.show();
    }
}

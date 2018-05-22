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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import zia.bean.UserBean;
import zia.server.Const;
import zia.util.NetUtil;
import zia.util.UserRes;

import java.io.IOException;

public class LoginPage {

    private Stage stage;

    public LoginPage() {
        stage = new Stage();
        //舞台名称
        stage.setTitle("俄罗斯方块Online");
        /*
         * GridPane
         * 登陆表单，使用一个GridPane布局，这样就会创建一个灵活的行列表格，我们可以在这些表格里添加控件。 也可以合并行列。
         * 我们把GridPane的实例赋给grid变量。
         * 1：改变排列方式为居中。
         * 2：gap属性控制行和列的间距。
         * 3：padding属性控制gridpane边缘的空间。Inset的顺序是上，右、下、左，这里都是25像素。
         *
         */
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("登录");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button loginBt = new Button("登录");
        Button registerBt = new Button("注册");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(registerBt);
        hbBtn.getChildren().add(loginBt);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        loginBt.setOnAction(event -> {
            NetUtil.ParametersBuilder parametersBuilder = new NetUtil.ParametersBuilder()
                    .put("appKey", Const.appKey)
                    .put("username", userTextField.getText())
                    .put("password", pwBox.getText());
            try {
                UserBean userBean = new Gson().fromJson(NetUtil.postHtml(Const.LOGIN, parametersBuilder), UserBean.class);
                if (userBean.getStatus() == 200) {
                    actiontarget.setFill(Color.GREEN);
                    actiontarget.setText(userBean.getInfo());
                    UserRes.instance.setUserData(userBean.getData());
                    stage.hide();
                } else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText(userBean.getInfo());
                }
            } catch (IOException e) {
                e.printStackTrace();
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText(e.toString());
            }
        });

        registerBt.setOnAction(event -> new RegisterPage());

        Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);

        stage.show();
    }

}

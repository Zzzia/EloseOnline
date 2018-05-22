package zia.util;

import com.google.gson.Gson;
import com.sun.istack.internal.Nullable;
import zia.bean.UserBean;

import java.io.*;

class UserDataUtil {

    static void write(UserBean.DataBean dataBean) throws IOException {
        File file = new File("data.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(new Gson().toJson(dataBean));
        out.flush();
        out.close();
    }

    @Nullable
    static UserBean.DataBean get() {
        File filename = new File("data.txt"); // 要读取以上路径的input。txt文件
        if (!filename.exists())
            return null;
        String json = readTxtFile(filename.getPath());
        System.out.println(json);
        return new Gson().fromJson(json, UserBean.DataBean.class);
    }

    private static String readTxtFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    content.append(lineTxt);// txt换行
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return content.toString();
    }
}

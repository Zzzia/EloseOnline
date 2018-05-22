package zia.server;

import javafx.application.Platform;
import zia.util.UserRes;

import java.io.*;
import java.net.Socket;

/**
 * 用户端
 */
public class Client extends Socket {

    private static final String SERVER_IP = "119.28.181.233"; // 服务端IP
    private static final int SERVER_PORT = 9821; // 服务端端口
    public static final String HEAD_INTRODUCE = "me";
    public static final String HEAD_CREATE = "cr";
    public static final String HEAD_JOIN = "jn";
    public static final String HEAD_QUIT = "qt";
    public static final String HEAD_GAME = "gm";
    private MessageListener messageListener = null;

    public static final String SERVER = "SERVER";

    private Socket client;
    private Writer writer;
    private boolean isEnd = false;
    private Thread reciveThread;

    private Client() throws IOException {
        super(SERVER_IP, SERVER_PORT);
    }

    public void init() throws IOException {
        isEnd = false;
        this.client = this;
        this.writer = new OutputStreamWriter(this.getOutputStream(), "UTF-8");

        reciveThread = new Thread(new ReceiveMsgTask());
        reciveThread.start();

    }

    /**
     * 第一步，上传自己身份信息
     */
    public void introduce() {
        String msg = HEAD_INTRODUCE + " " + getUserData().getUsername() + " " + getUserData().getNickname();
        sendMessage(msg);
    }

    /**
     * 请求创建房间
     */
    public void create() {
        String msg = HEAD_CREATE + " " + getUserData().getUsername();
        sendMessage(msg);
    }

    /**
     * 请求加入房间
     *
     * @param roomId
     */
    public void join(int roomId) {
        String msg = HEAD_JOIN + " " + getUserData().getUsername() + " " + roomId;
        sendMessage(msg);
    }

    /**
     * 开始游戏
     */
    public void begin() {
        String msg = HEAD_GAME + " " + getUserData().getUsername() + " " + "begin";
        sendMessage(msg);
    }

    /**
     * 发送游戏数据
     *
     * @param data
     */
    public void sendData(String data) {
        if (client.isClosed()) return;
        String msg = HEAD_GAME + " " + getUserData().getUsername() + " " + data;
        sendMessage(msg);
    }

    /**
     * 退出
     */
    public void quit() {
        String msg = HEAD_QUIT + " " + getUserData().getUsername() + " " + "bey";
        sendMessage(msg);
        destroy();
    }

    private void sendMessage(String msg) {
        if (client.isClosed()) return;
        System.out.println("ClientSend  " + msg);
        try {
            writer.write(msg);
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserRes.UserData getUserData() {
        return UserRes.instance.getUserData();
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    /**
     * 销毁，不然贼烫
     */
    public void destroy() {
        try {
            reciveThread.interrupt();
            writer.close();
            client.close();
            SingletonHolder.instance = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ReceiveMsgTask implements Runnable {

        private BufferedReader buff;

        @Override
        public void run() {
            try {
                this.buff = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
                while (!isEnd && !client.isClosed()) {
                    String result = buff.readLine();
                    if (result != null) {
                        System.out.println("Client  " + result);
                        if (messageListener != null) {
                            String split[] = result.split(" ");
                            Platform.runLater(() -> {
                                if (split[1].equals(Client.SERVER)) {//服务器消息
                                    if (split[0].equals(Client.HEAD_INTRODUCE) && split[2].equals("ok")) {//连接服务器成功
                                        messageListener.onRoomLinked();
                                    } else if (split[0].equals(Client.HEAD_CREATE) && split[2].equals("ok")) {//加入房间成功
                                        messageListener.onJoinSuccess();
                                    } else if (split[0].equals(Client.HEAD_CREATE)) {//房间创建成功
                                        messageListener.onRoomCreated(Integer.parseInt(split[2]));
                                    } else if (split[0].equals(Client.HEAD_GAME)) {//接受游戏数据
                                        messageListener.onGameDataGet(split[2]);
                                    }
                                } else {//其他玩家的消息
                                    if (split[0].equals(Client.HEAD_CREATE) && split[2].equals("join")) {//有人加入房间
                                        messageListener.onOthersJoin(split[1]);
                                    } else if (split[0].equals(Client.HEAD_GAME) && split[2].equals("begin")) {//游戏开始
                                        messageListener.onGameBegin();
                                    } else if (split[0].equals(Client.HEAD_GAME)) {//游戏数据
                                        messageListener.onGameDataGet(split[2]);
                                    } else if (split[0].equals(Client.HEAD_QUIT)) {//退出游戏
                                        messageListener.onQuit();
                                    }
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    buff.close();
                    // 关闭连接
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class SingletonHolder {
        private static Client instance = null;
    }

    public static Client getInstance() {
        if (SingletonHolder.instance == null) {
            try {
                SingletonHolder.instance = new Client();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SingletonHolder.instance;
    }
}

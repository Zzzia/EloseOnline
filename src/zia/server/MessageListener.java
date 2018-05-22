package zia.server;

public interface MessageListener {

    void onRoomLinked();//连接服务器成功

    void onRoomCreated(int roomId);//服务器创建房间成功

    void onOthersJoin(String name);//有人加入房间

    void onJoinSuccess();//加入成功

    void onQuit();//解散房间

    void onGameBegin();//开始游戏

    void onGameDataGet(String msg);//接受游戏数据
}

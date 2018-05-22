package zia.server;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public enum  NetThreadPool {
    instance;

    private Executor executor;

    private NetThreadPool(){
        executor = Executors.newCachedThreadPool();
    }

    public Executor getExecutor() {
        return executor;
    }
}

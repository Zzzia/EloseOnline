package zia.bean;

import java.io.Serializable;
import java.util.Arrays;

public class MapWarper implements Serializable {
    private int map[][];
    private int score;

    @Override
    public String toString() {
        return "MapWarper{" +
                "map=" + Arrays.toString(map) +
                ", score=" + score +
                '}';
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

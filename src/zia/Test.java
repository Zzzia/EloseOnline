package zia;

import zia.shape.BaseShape;
import zia.shape.Line;

public class Test {
    public static void main(String[] args) throws Exception {
        int map[][] = new int[20][10];
        BaseShape line = new Line(map);
        line.goDown();
        System.out.println(line);
        line.goDown();
        System.out.println(line);
        line.goLeft();
        System.out.println(line);
        line.change();
        System.out.println(line);
    }
}

package zia.shape;

import zia.page.game.Config;

import java.util.List;

public abstract class BaseShape {

    private int mod = 0;

    protected int x, y;
    private int map[][];
    private int width, height;

    @Override
    public String toString() {
        return getShape().toString();
    }

    public BaseShape(int map[][]) throws Exception {
        this.map = map;
        this.width = map[0].length;
        this.height = map.length;
        y = 0;
        x = width / 2;
        for (Position p : getShape()) {
            if (map[p.getY()][p.getX()] != Config.EMPTY) {
                throw new Exception("end");
            }
        }
    }

    protected abstract List<Position> getShapeMod0(Position position);

    protected abstract List<Position> getShapeMod1(Position position);

    protected abstract List<Position> getShapeMod2(Position position);

    protected abstract List<Position> getShapeMod3(Position position);

    public void change() {
        //先变化，看是否有问题，有问题就-1
        mod = (mod + 1) % 4;
        int minY = 100;
        int maxY = -100;
        int minX = 100;
        int maxX = -100;
        for (Position p : getShape()) {
            if (p.getY() < minY)
                minY = p.getY();
            if (p.getY() > maxX)
                maxY = p.getY();
            if (p.getX() < minX)
                minX = p.getX();
            if (p.getX() > maxX)
                maxX = p.getX();
        }
        //如果越界，返回之前的状态
        if (minX <= 0 || maxX >= width || maxY >= height) {
            mod = (mod - 1) % 4;
        } else if (minY < 0) {
            int tempY = Math.abs(minY) + y;
            //如果被挡住或者到了最底下，返回之前的状态
            if (isEnd(new Position(x, tempY))) {
                mod = Math.abs(mod - 1) % 4;
            } else {
                y = tempY;
            }
        } else {
            if (isEnd(getGravity())) {
                mod = Math.abs(mod - 1) % 4;
            }
        }
        System.out.println("mod = " + mod);
    }

    public boolean goDown() {
        if (isEnd(new Position(x, y + 1))) {
            return false;
        } else {
            y++;
            return true;
        }
    }

    public void goLeft() {
        if (!isOut(new Position(x - 1, y)))
            x--;
    }

    public void goRight() {
        if (!isOut(new Position(x + 1, y)))
            x++;
    }

    public boolean goEnd() {
        while (true) {
            if (!goDown())
                break;
        }
        return false;
    }

    protected List<Position> getShape(Position position) {
        switch (mod) {
            case 0:
                return getShapeMod0(position);
            case 1:
                return getShapeMod1(position);
            case 2:
                return getShapeMod2(position);
            case 3:
                return getShapeMod3(position);
        }
        return null;
    }

    public List<Position> getShape() {
        return getShape(getGravity());
    }

    /**
     * 判断是否能够继续下落
     *
     * @param position
     * @return
     */
    protected boolean isEnd(Position position) {
        List<Position> positions = getShape(position);
        int i = 0;
        for (; i < positions.size(); i++) {
            Position p = positions.get(i);
            //是否超界
            if (p.getY() >= Config.y)
                break;
            //下面是否有方块
            if (map[p.getY()][p.getX()] != Config.EMPTY &&
                    map[p.getY()][p.getX()] != Config.TEMP)
                break;
        }
        return i != positions.size();
    }

    /**
     * 判断是否超出屏幕宽度
     *
     * @param position
     * @return
     */
    protected boolean isOut(Position position) {
        for (Position p : getShape(position)) {
            if (p.getX() < 0 || p.getX() >= width || p.getY() < 0
                    || (map[p.getY()][p.getX()] != Config.EMPTY &&
                    map[p.getY()][p.getX()] != Config.TEMP)) {
                return true;
            }
        }
        return false;
    }

    protected int[][] getMap() {
        return map;
    }

    public int getMod() {
        return mod;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getGravity() {
        return new Position(x, y);
    }
}

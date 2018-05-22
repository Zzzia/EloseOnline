package zia.shape;

import java.util.ArrayList;
import java.util.List;

/*
        #
       ###
 */

public class T extends BaseShape {
    public T(int[][] map) throws Exception {
        super(map);
    }

    @Override
    protected List<Position> getShapeMod0(Position position) {
        List<Position> positions = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        positions.add(new Position(x, y));
        positions.add(new Position(x, y + 1));
        positions.add(new Position(x - 1, y + 1));
        positions.add(new Position(x + 1, y + 1));
        return positions;
    }

    @Override
    protected List<Position> getShapeMod1(Position position) {
        List<Position> positions = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        positions.add(new Position(x, y));
        positions.add(new Position(x, y + 1));
        positions.add(new Position(x, y - 1));
        positions.add(new Position(x - 1, y));
        return positions;
    }

    @Override
    protected List<Position> getShapeMod2(Position position) {
        List<Position> positions = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        positions.add(new Position(x, y));
        positions.add(new Position(x, y + 1));
        positions.add(new Position(x - 1, y));
        positions.add(new Position(x + 1, y));
        return positions;
    }

    @Override
    protected List<Position> getShapeMod3(Position position) {
        List<Position> positions = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        positions.add(new Position(x, y));
        positions.add(new Position(x, y + 1));
        positions.add(new Position(x, y - 1));
        positions.add(new Position(x + 1, y));
        return positions;
    }
}

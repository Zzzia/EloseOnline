package zia.shape;

import java.util.ArrayList;
import java.util.List;

/*
        ##
        #
        #
 */
public class L2 extends BaseShape {
    public L2(int[][] map) throws Exception {
        super(map);
    }

    @Override
    protected List<Position> getShapeMod0(Position position) {
        List<Position> positions = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        positions.add(new Position(x, y));
        positions.add(new Position(x + 1, y));
        positions.add(new Position(x, y + 1));
        positions.add(new Position(x, y + 2));
        return positions;
    }

    @Override
    protected List<Position> getShapeMod1(Position position) {
        List<Position> positions = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        positions.add(new Position(x, y));
        positions.add(new Position(x, y - 1));
        positions.add(new Position(x + 1, y));
        positions.add(new Position(x + 2, y));
        return positions;
    }

    @Override
    protected List<Position> getShapeMod2(Position position) {
        List<Position> positions = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        positions.add(new Position(x, y));
        positions.add(new Position(x - 1, y));
        positions.add(new Position(x, y - 1));
        positions.add(new Position(x, y - 2));
        return positions;
    }

    @Override
    protected List<Position> getShapeMod3(Position position) {
        List<Position> positions = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        positions.add(new Position(x, y));
        positions.add(new Position(x - 1, y));
        positions.add(new Position(x - 2, y));
        positions.add(new Position(x, y + 1));
        return positions;
    }
}

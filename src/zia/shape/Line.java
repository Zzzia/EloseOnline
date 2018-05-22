package zia.shape;

import java.util.ArrayList;
import java.util.List;

/**
 * ****
 */
public class Line extends BaseShape {

    public Line(int[][] map) throws Exception {
        super(map);
    }

    @Override
    protected List<Position> getShapeMod0(Position position) {
        int x = position.getX();
        int y = position.getY();
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Position p = new Position(x - 2 + i, y);
            positions.add(p);
        }
        return positions;
    }

    @Override
    protected List<Position> getShapeMod1(Position position) {
        int x = position.getX();
        int y = position.getY();
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Position p = new Position(x, y - 2 + i);
            positions.add(p);
        }
        return positions;
    }

    @Override
    protected List<Position> getShapeMod2(Position position) {
        return getShapeMod0(position);
    }

    @Override
    protected List<Position> getShapeMod3(Position position) {
        return getShapeMod1(position);
    }

}

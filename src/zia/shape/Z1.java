package zia.shape;

import java.util.ArrayList;
import java.util.List;

/*
         #
        ##
        #
 */
public class Z1 extends BaseShape {
    public Z1(int[][] map) throws Exception {
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
        positions.add(new Position(x - 1, y + 2));
        return positions;
    }

    @Override
    protected List<Position> getShapeMod1(Position position) {
        List<Position> positions = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        positions.add(new Position(x, y));
        positions.add(new Position(x - 1, y));
        positions.add(new Position(x, y + 1));
        positions.add(new Position(x + 1, y + 1));
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

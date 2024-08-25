package cleancode.minesweeper.tobe.cell;

public class LandMineCell implements Cell {

    private static final String LAND_MINE_SIGN = "☼";

    private final CellState cellState = CellState.initialize();
    @Override
    public boolean hasLandMineCount() {
        return false;
    }

    @Override
    public boolean isLandMine() {
        return true;
    }

    @Override
    public String getSign() {
        if(isChecked()){
            return LAND_MINE_SIGN;
        }
        if(isFlagged()){
            return FLAG_SIGN;
        }
        return UNCHECKED_SIGN;
    }


    @Override
    public void flag() {
        cellState.flag();
    }

    @Override
    public void open() {
        cellState.open();
    }

    @Override
    public boolean isChecked() {
        return cellState.isChecked();
    }

    @Override
    public boolean isOpened() {
        return cellState.isOpened();
    }

    @Override
    public boolean isFlagged() {
        return cellState.isFlagged();
    }
}

package cleancode.minesweeper.tobe.minesweeper.board.cell;

public interface Cell {

    boolean hasLandMineCount();

    boolean isLandMine();

    CellSnapshot getSnapshot();

    void flag();

    void open();

    boolean isChecked();

    boolean isOpened();

    boolean isFlagged();
}

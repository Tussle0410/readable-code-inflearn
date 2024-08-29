package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.Cells;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.List;

public class GameBoard {
    private  final Cell[][] board;
    private final int landMineCount;
    private GameStatus gameStatus;

    public GameBoard(GameLevel gameLevel) {
        int colSize = gameLevel.getColSize();
        int rowSize = gameLevel.getRowSize();
        board = new Cell[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();
        initializeGameStatus();
    }
    public  void initializeGame() {
        initializeGameStatus();
        CellPositions cellPositions = CellPositions.from(board);

        initializeEmptyCells(cellPositions);

        List<CellPosition> landMineCellPositions = cellPositions.extractRandomPositions(landMineCount);
        initializeLandMineCells(landMineCellPositions);

        List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMineCellPositions);
        initializeNumberCells(numberPositionCandidates);
    }

    private  void initializeGameStatus() {
        gameStatus =  GameStatus.IN_PROGRESS;
    }

    private void initializeEmptyCells(CellPositions cellPositions) {
        List<CellPosition> allPositions = cellPositions.getPositions();
        for(CellPosition cellPosition : allPositions){
            updateCellAt(cellPosition, new EmptyCell());
        }
    }

    private void initializeLandMineCells(List<CellPosition> landMineCellPositions) {
        for(CellPosition cellPosition : landMineCellPositions){
            updateCellAt(cellPosition, new LandMineCell());
        }
    }

    private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
        for(CellPosition candidatePosition : numberPositionCandidates){
            int count = countNearbyLandMines(candidatePosition);
            if(count != 0){
                updateCellAt(candidatePosition, new NumberCell(count));
            }
        }
    }

    private void updateCellAt(CellPosition cellPosition, Cell cell) {
        board[cellPosition.getRowIndex()][cellPosition.getColIndex()] = cell;
    }

    public  void openSurroundedCells(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCell(cellPosition)) {
            return;
        }
        openOneCellAt(cellPosition);

        if (doesCellHaveLandMineCount(cellPosition)) {
            return;
        }

        List<CellPosition> surroundedPositions = calculateSurroundedPositions(cellPosition, getRowSize(), getColSize());
        surroundedPositions.forEach(this::openSurroundedCells);

    }

    private static boolean canMovePosition(CellPosition cellPosition, RelativePosition relativePosition) {
        return cellPosition.canCalculatePositionBy(relativePosition);
    }

    public  boolean isAllCellChecked() {

        Cells cells = Cells.from(board);

        return cells.isAllChecked();
    }
    public void flagAt(CellPosition cellPosition) {
        findCell(cellPosition ).flag();
        checkIfGameIsOver();
    }
    public void openOneCellAt(CellPosition cellPosition) {
        findCell(cellPosition).open();
    }
    public  boolean isLandMineCell(CellPosition cellPosition) {
        return findCell(cellPosition).isLandMine();
    }
    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        return cellPosition.isRowIndexMoreThanOrEqual(rowSize)
                || cellPosition.isColIndexMoreThanOrEqual(colSize);
    }

    public int getRowSize() {
        return board.length;
    }
    public int getColSize() {
        return board[0].length;
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    private int countNearbyLandMines(CellPosition cellPosition) {

        long count = calculateSurroundedPositions(cellPosition, getRowSize(), getColSize()).stream()
                .filter(this::isLandMineCell)
                .count();


        return (int) count;
    }

    private static List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
        return RelativePosition.SURROUNDED_POSITIONS.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(position -> position.isRowIndexLessThan(rowSize))
                .filter(position -> position.isColIndexLessThan(colSize))
                .toList();
    }
    private  void checkIfGameIsOver() {
        if (isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }
    private  void changeGameStatusToWin() {
        gameStatus = GameStatus.WIN;
    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        return findCell(cellPosition).isOpened();
    }
    public boolean isInProgress() {
        return gameStatus == GameStatus.IN_PROGRESS;
    }
    public CellSnapshot getSnapshot(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSnapshot();
    }

    public void openAt(CellPosition cellPosition) {
        if (isLandMineCell(cellPosition)) {
            openOneCellAt(cellPosition);
            changeGameStatusToLose();
            return;
        }
        openSurroundedCells(cellPosition);
        checkIfGameIsOver();
    }
    private  void changeGameStatusToLose() {
        gameStatus = GameStatus.LOSE;
    }

    public boolean isWinStatus() {
        return gameStatus == GameStatus.WIN;
    }

    public boolean isLoseStatus() {
        return gameStatus == GameStatus.LOSE;
    }
}

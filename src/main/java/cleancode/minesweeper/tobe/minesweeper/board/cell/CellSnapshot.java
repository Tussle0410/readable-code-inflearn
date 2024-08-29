package cleancode.minesweeper.tobe.minesweeper.board.cell;

import java.util.Objects;

public class CellSnapshot {

    private final CellSnapshotStatus status;
    private final int nearByLandMineCount;

    public CellSnapshot(CellSnapshotStatus status, int nearByLandMineCount) {
        this.status = status;
        this.nearByLandMineCount = nearByLandMineCount;
    }
    public static CellSnapshot of(CellSnapshotStatus status, int nearByLandMineCount){
        return new CellSnapshot(status, nearByLandMineCount);
    }

    public static CellSnapshot ofEmpty(){
        return new CellSnapshot(CellSnapshotStatus.EMPTY, 0);
    }


    public static CellSnapshot ofFlag(){
        return new CellSnapshot(CellSnapshotStatus.FLAGGED, 0);
    }

    public static CellSnapshot ofLandMine(){
        return new CellSnapshot(CellSnapshotStatus.LAND_MINE, 0);
    }

    public static CellSnapshot ofNumber(int nearByLandMineCount){
        return new CellSnapshot(CellSnapshotStatus.NUMBER, nearByLandMineCount);
    }

    public static CellSnapshot ofUnchecked(){
        return new CellSnapshot(CellSnapshotStatus.UNCHECKED, 0);
    }

    public boolean isSameStatus(CellSnapshotStatus cellSnapshotStatus) {
        return status == cellSnapshotStatus;
    }

    public CellSnapshotStatus getStatus() {
        return status;
    }

    public int getNearByLandMineCount() {
        return nearByLandMineCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        CellSnapshot that = (CellSnapshot) o;
        return nearByLandMineCount == that.nearByLandMineCount && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, nearByLandMineCount);
    }
}

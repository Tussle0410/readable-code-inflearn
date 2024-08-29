package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

import java.util.Arrays;

public enum CellSignProvider implements CellSignProvidable{
    EMPTY("빈 셀", CellSnapshotStatus.EMPTY){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return EMPTY_SIGN;
        }
    },
    FLAG("깃발", CellSnapshotStatus.FLAGGED){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return FLAG_SIGN;
        }
    },
    LAND_MINE("지뢰", CellSnapshotStatus.LAND_MINE){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return LAND_MINE_SIGN;
        }
    },
    NUMBER("숫자", CellSnapshotStatus.NUMBER){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return String.valueOf(cellSnapshot.getNearByLandMineCount());
        }
    },
    UNCHECKED("확인 전", CellSnapshotStatus.UNCHECKED){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return UNCHECKED_SIGN;
        }
    };

    private static final String EMPTY_SIGN = "■";
    private static final String FLAG_SIGN = "⚑";
    private static final String UNCHECKED_SIGN = "□";
    private static final String LAND_MINE_SIGN = "☼";

    private final String description;
    private final CellSnapshotStatus cellSnapshotStatus;

    CellSignProvider(String description, CellSnapshotStatus cellSnapshotStatus) {
        this.description = description;
        this.cellSnapshotStatus = cellSnapshotStatus;
    }


    @Override
    public boolean support(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(cellSnapshotStatus);
    }

    public static String findCellSignFrom(CellSnapshot snapshot){

        CellSignProvider cellSignProvider = findBy(snapshot);

        return cellSignProvider.provide(snapshot);
    }

    private static CellSignProvider findBy(CellSnapshot snapshot) {
        return Arrays.stream(values())
                .filter(provider -> provider.support(snapshot))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 셀입니다."));
    }
}

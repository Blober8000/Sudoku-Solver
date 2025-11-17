import java.util.ArrayList;

public class BruteForce {
    private static Board EmptyBoard = new Board();
    public static Board trialError(Board board) {
        EmptyBoard.initializeBoard(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        ArrayList<Cell> empty = findEmpty(board);
        int i;
        while (true) {
            Cell emptyCell = empty.get(0);
            Board newBoard = new Board();
            for (i = 1; i < 10; i++) {
                if (emptyCell.getCandidates()[i] != 0) {
                    newBoard = new Board();
                    newBoard.initializeBoard(board.boardCopy());
                    newBoard.getCell(emptyCell.getX(), emptyCell.getY()).setValue(i);
                    break;
                }
            }
            Boolean regression = main.main(newBoard);
            if (Boolean.TRUE.equals(regression)) {
                for (i = 0; i < 81; i++) {
                    board.getCell(i).setValue(newBoard.getCell(i).getValue());
                }
                return board;
            } else if (Boolean.FALSE.equals(regression)){
                int[] temp = emptyCell.getCandidates();
                for (int j = 1; j < 10; j++) {
                    if(j==i){
                        temp[j] = 0;
                        break;
                    }
                }
                boolean emptyCandidates = true;
                for (int k = 1; k < 10; k++) {
                    if(temp[k]!=0){
                        emptyCandidates = false;
                        break;
                    }
                }
                if(!emptyCandidates) {
                    emptyCell.setCandidates(temp);
                } else{
                    emptyCell.setCandidates(temp);
                    empty.remove(0);
                }
                if(empty.isEmpty()){
                    return EmptyBoard;
                }
            } else if (regression == null){
                newBoard = trialError(newBoard);
                if (!newBoard.equals(EmptyBoard)) {
                    return newBoard;
                } else {
                    int[] temp = emptyCell.getCandidates();
                    for (int j = 1; j < 10; j++) {
                        if(j==i){
                            temp[j] = 0;
                            break;
                        }
                    }
                    boolean emptyCandidates = true;
                    for (int k = 1; k < 10; k++) {
                        if(temp[k]!=0){
                            emptyCandidates = false;
                            break;
                        }
                    }
                    if(!emptyCandidates) {
                        emptyCell.setCandidates(temp);
                    } else{
                        emptyCell.setCandidates(temp);
                        empty.remove(0);
                    }
                    if(empty.isEmpty()){
                        return EmptyBoard;
                    }
                }
            }
        }
    }

    public static ArrayList<Cell> findEmpty(Board board) {
        ArrayList<Cell> empty = new ArrayList<>();
        for (Cell cell : board.getCells()) {
            if (cell.getValue() == 0) {
                empty.add(cell);
            }
        }
        return empty;
    }
}

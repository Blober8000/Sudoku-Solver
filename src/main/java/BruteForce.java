import java.util.ArrayList;

public class BruteForce {
    private static Board EmptyBoard = new Board();
    public static Board trialError(Board board) {
        EmptyBoard.initializeBoard(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        ArrayList<Cell> empty = findEmpty(board);
        empty = organizeEmpty(empty);
        if(empty.isEmpty()){
            return board;
        }
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

    public static ArrayList<Cell> organizeEmpty(ArrayList<Cell> empty) {

        empty.sort((cell1, cell2) -> {
            int count1 = 0;
            int count2 = 0;

            for (int k = 1; k < 10; k++) {
                if (cell1.getCandidates()[k] != 0) {
                    count1++;
                }
            }

            for (int k = 1; k < 10; k++) {
                if (cell2.getCandidates()[k] != 0) {
                    count2++;
                }
            }

            return Integer.compare(count1, count2);
        });

        int count;
        do{
            count = 0;
            for (int k : empty.get(0).getCandidates()) {
                if (k != 0){
                    count ++;
                }
            }
            if(count == 0){
                empty.remove(0);
            }
        }while(count == 0 && !empty.isEmpty());

        return empty;
    }
}

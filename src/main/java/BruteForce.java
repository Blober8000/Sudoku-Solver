import java.util.ArrayList;

public class BruteForce {

    public static Board trialError(Board board) {
        ArrayList<Cell> empty = findEmpty(board);
        empty = organizeEmpty(empty);
        int i;
        while (true) {
            Cell emptyCell = empty.getFirst();
            Board newBoard = new Board();
            for (i = 1; i < 10; i++) {
                if (emptyCell.getCandidates()[i] != 0) {
                    newBoard = new Board();
                    newBoard.initializeBoard(board.boardCopy());
                    newBoard.getCell(emptyCell.getX(), emptyCell.getY()).setValue(i);
                    break;
                }
            }
            if (main.main(newBoard)) {
                for (i = 0; i < 81; i++) {
                    board.getCell(i).setValue(newBoard.getCell(i).getValue());
                }
                return board;
            } else {
                int[] temp = emptyCell.getCandidates();
                for (int j = 1; j < 10; j++) {
                    if (j == i) {
                        temp[j] = 0;
                        break;
                    }
                }
                boolean emptyCandidates = true;
                for (int k = 1; k < 10; k++) {
                    if (temp[k] != 0) {
                        emptyCandidates = false;
                        break;
                    }
                }
                if (!emptyCandidates) {
                    emptyCell.setCandidates(temp);
                } else {
                    emptyCell.setCandidates(temp);
                    empty.removeFirst();
                }
                if (empty.isEmpty()) {
                    return board;
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
        boolean sorted;
        do{
            sorted = true;
            int count1 = 0;
            int count2 = 0;

            for (int i = 0; i < empty.size()-1; i++) {
                for (int v : empty.get(i).getCandidates()) {
                    if (v != 0){
                        count1 ++;
                    }
                }

                for (int v : empty.get(i+1).getCandidates()) {
                    if (v != 0){
                        count2 ++;
                    }
                }

                if (count1 > count2) {
                    sorted = false;
                    Cell temp = empty.get(i+1);
                    empty.set(i+1, empty.get(i));
                    empty.set(i, temp);
                }
            }
        }while(!sorted);

        return empty;
    }
}
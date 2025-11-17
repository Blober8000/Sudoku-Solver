import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        long time =  System.currentTimeMillis();
        Board board = new Board();
        boolean check;
        check = board.initializeBoard(new int[]
                {0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 5, 0, 6, 0, 8, 0, 0, 0, 7, 0, 0, 0, 0, 0, 3, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 5, 8, 0, 0, 0, 0, 2, 0, 5, 0, 4, 9, 6, 0, 4, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 5, 0}
        );
        if (check) {
            board.drawBoard();
        }
        Logic.solveBoard(board);
        if(!board.checkCompletion()){
            board = BruteForce.trialError(board);
        }
        board.drawBoard();
        System.out.println();
        long time1 = System.currentTimeMillis();
        System.out.printf("Time: %.3f seconds", (time1-time)/1000.0);

    }

    public static Boolean main(Board board) {
        Logic.solveBoard(board);
        if(!board.checkCompletion() && !Logic.checkCorrectness(board)){
            return false;
        } else if(board.checkCompletion() && !Logic.checkCorrectness(board)){
            return false;
        } else if(!board.checkCompletion() && Logic.checkCorrectness(board)){
            return null;
        } else if(board.checkCompletion() && Logic.checkCorrectness(board)){
            return true;
        }
        return null;
    }
}

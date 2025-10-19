import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        Board board = new Board();
        boolean check;
        check = board.initializeBoard(new int[]{
                0, 3, 0, 0, 4, 0, 0, 0, 7,
                0, 4, 0, 0, 2, 1, 3, 9, 0,
                1, 9, 0, 0, 0, 0, 0, 8, 0,
                3, 7, 8, 2, 0, 0, 0, 1, 0,
                4, 0, 0, 5, 1, 8, 7, 0, 0,
                0, 0, 1, 4, 0, 0, 0, 2, 0,
                0, 1, 3, 7, 0, 0, 0, 4, 0,
                2, 5, 7, 9, 0, 0, 8, 3, 0,
                9, 0, 0, 0, 0, 3, 0, 7, 2});
        if (check) {
            board.drawBoard();
        }
        Logic.solveBoard(board);
        if(!board.checkCompletion()){
            BruteForce.trialError(board);
        }
        board.drawBoard();
        System.out.println(Logic.checkCorrectness(board));
        System.out.println();

    }

    public static boolean main(Board board) {
        Logic.solveBoard(board);
        board.drawBoard();
        if(!board.checkCompletion()){
            return false;
        } else{
            return true;
        }
    }
}

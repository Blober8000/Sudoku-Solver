import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        Board board = new Board();
        boolean check;
        check = board.initializeBoard(new int[]{
                6, 7, 0, 0, 0, 0, 0, 0, 2,
                4, 0, 0, 0, 0, 8, 0, 7, 0,
                0, 0, 5, 0, 0, 6, 8, 1, 0,
                0, 0, 0, 8, 0, 0, 3, 0, 0,
                0, 1, 4, 0, 0, 3, 6, 8, 9,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 3, 0, 0, 4, 0, 0, 0,
                7, 0, 0, 0, 1, 9, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 9, 4});
        if (check) {
            board.drawBoard();
        }
        Logic.solveBoard(board);
        if(!board.checkCompletion()){
            board = BruteForce.trialError(board);
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

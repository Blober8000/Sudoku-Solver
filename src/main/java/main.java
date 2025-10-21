import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        long time =  System.currentTimeMillis();
        Board board = new Board();
        boolean check;
        check = board.initializeBoard(new int[81]);
        if (check) {
            board.drawBoard();
            int i = new Scanner(System.in).nextInt();
        }
        Logic.solveBoard(board);
        if(!board.checkCompletion()){
            board = BruteForce.trialError(board);
        }
        board.drawBoard();
        System.out.println(Logic.checkCorrectness(board));
        System.out.println();
        long time1 = System.currentTimeMillis();
        System.out.printf("Time: %.3f seconds", (time1-time)/1000.0);

    }

    public static boolean main(Board board) {
        Logic.solveBoard(board);
        board.drawBoard();
        if(!board.checkCompletion() || !Logic.checkCorrectness(board)){
            return false;
        } else{
            return true;
        }
    }
}

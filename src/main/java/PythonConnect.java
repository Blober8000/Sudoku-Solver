import java.util.Arrays;

public class PythonConnect {
    public static void main(String[] args) {
        Board board = new Board();
        boolean check;
        int[] temp = new int[81];
        int counter = 0;
        for (String cell : args){
            temp[counter] = Integer.parseInt(cell);
            counter++;
        }
        check = board.initializeBoard(temp);
        Logic.solveBoard(board);
        if(!board.checkCompletion()){
            board = BruteForce.trialError(board);
        }
        System.out.println(Arrays.toString(board.boardCopy()));
    }
}

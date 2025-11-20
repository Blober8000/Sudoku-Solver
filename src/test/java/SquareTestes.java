import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SquareTestes {
    private static Board board;
    @BeforeAll
    public static void setup(){
        System.out.println("Começar testes...");
        board = new Board();
    }

    @AfterAll
    public static void teardown(){
        board = null;
        System.out.println("Os testes acabaram");
    }

    @Test
    public void obviousPairsTest(){
        boolean check;
        check = board.initializeBoard(new int[]
                {
                        0, 0, 2, 0, 8, 5, 0, 0, 4,
                        0, 0, 0, 0, 3, 0, 0, 6, 0,
                        0, 0, 4, 2, 1, 0, 0, 3, 0,
                        0, 0, 0, 0, 0, 0, 0, 5, 2,
                        0, 0, 0, 0, 0, 0, 3, 1, 0,
                        9, 0, 0, 0, 0, 0, 0, 0, 0,
                        8, 0, 0, 0, 0, 6, 0, 0, 0,
                        2, 5, 0, 4, 0, 0, 0, 0, 8,
                        0, 0, 0, 0, 0, 1, 6, 0, 0
                });
        board.parseCandidates(false);
        for (Cell cell : board.getSquares()[2].getCells()) {
            System.out.println(Arrays.toString(cell.getCandidates()));
        }
        board.getSquares()[2].obviousPairs(false);
        System.out.println();
        for (Cell cell : board.getSquares()[2].getCells()) {
            System.out.println(Arrays.toString(cell.getCandidates()));
        }
        board.drawBoard();
    }

}

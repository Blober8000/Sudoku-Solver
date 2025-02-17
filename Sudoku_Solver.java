import java.util.Arrays;
import java.util.Scanner;

public class Sudoku_Solver {
    public static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    public static int[][][] board = new int[9][9][10];
    public static String red = "\u001B[31m", reset = "\u001B[0m", blue = "\u001B[34m";
    public static Scanner scanner = new Scanner(System.in);
    public static int[][] order = {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}, {0, 3}, {0, 4}, {0, 5}, {1, 3}, {1, 4}, {1, 5}, {2, 3}, {2, 4}, {2, 5}, {0, 6}, {0, 7}, {0, 8}, {1, 6}, {1, 7}, {1, 8}, {2, 6}, {2, 7}, {2, 8}, {3, 0}, {3, 1}, {3, 2}, {4, 0}, {4, 1}, {4, 2}, {5, 0}, {5, 1}, {5, 2}, {3, 3}, {3, 4}, {3, 5}, {4, 3}, {4, 4}, {4, 5}, {5, 3}, {5, 4}, {5, 5}, {3, 6}, {3, 7}, {3, 8}, {4, 6}, {4, 7}, {4, 8}, {5, 6}, {5, 7}, {5, 8}, {6, 0}, {6, 1}, {6, 2}, {7, 0}, {7, 1}, {7, 2}, {8, 0}, {8, 1}, {8, 2}, {6, 3}, {6, 4}, {6, 5}, {7, 3}, {7, 4}, {7, 5}, {8, 3}, {8, 4}, {8, 5}, {6, 6}, {6, 7}, {6, 8}, {7, 6}, {7, 7}, {7, 8}, {8, 6}, {8, 7}, {8, 8}};
    public static int[][] Square = {{0, 1, 2, 3, 4, 5, 6, 7, 8}, {9, 10, 11, 12, 13, 14, 15, 16, 17}, {18, 19, 20, 21, 22, 23, 24, 25, 26}, {27, 28, 29, 30, 31, 32, 33, 34, 35}, {36, 37, 38, 39, 40, 41, 42, 43, 44}, {45, 46, 47, 48, 49, 50, 51, 52, 53}, {54, 55, 56, 57, 58, 59, 60, 61, 62}, {63, 64, 65, 66, 67, 68, 69, 70, 71}, {72, 73, 74, 75, 76, 77, 78, 79, 80}};
    public static final int CELL_NUMBER = 0;

    public static void main(String[] args) {
        if (!Arrays.equals(args, new String[]{})) {
            if (args.length != 81) {
                System.out.println("Invalid number of arguments");
            } else {
                UseArgs(args);
                while (true) {
                    DrawBoard();
                    int[] in = Input();
                    if (in == null) {
                        break;
                    } else {
                        UpdateBoard(in);
                    }
                }
                PrepareBoard();
                DrawBoard();
                SolveBoard();
                DrawBoard();
            }
        } else {
            while (true) {
                DrawBoard();
                int[] in = Input();
                if (in == null) {
                    break;
                } else {
                    UpdateBoard(in);
                }
            }
            PrepareBoard();
            SolveBoard();
            DrawBoard();
        }
    }

    public static void DrawBoard() {
        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
        System.out.print("  ");
        for (String ch : letters) {
            System.out.printf("%s%s%s ", red, ch, reset);
        }
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.printf("%s%d%s ", red, i + 1, reset);
            for (int j = 0; j < 9; j++) {
                if (board[i][j][CELL_NUMBER] == 0) {
                    System.out.printf("%s ", "*");
                } else {
                    if (board[i][j][1] == board[i][j][2] && board[i][j][2] == board[i][j][3] && board[i][j][3] == board[i][j][4] && board[i][j][4] == board[i][j][5] && board[i][j][5] == board[i][j][6] && board[i][j][6] == board[i][j][7] && board[i][j][7] == board[i][j][8] && board[i][j][8] == board[i][j][9]) {
                        System.out.printf("%d ", board[i][j][CELL_NUMBER]);
                    } else {
                        System.out.printf("%s%d%s ", blue, board[i][j][CELL_NUMBER], reset);
                    }
                }
            }
            System.out.println();
        }
    }

    public static int[] Input() {
        while (true) {
            System.out.println("Insert the coords and number (A 1 1): ");
            String inputS = scanner.nextLine();
            if (inputS.equals("End")) {
                break;
            }
            String[] inputArr = inputS.split(" ");
            if (inputArr.length != 3) {
                System.out.println("Invalid input");
                continue;
            }
            int x = 0, y, num;
            boolean passed = false;
            for (String let : letters) {
                if (inputArr[0].equals(let)) {
                    x = Arrays.asList(letters).indexOf(let) + 1;
                    passed = true;
                }
            }
            if (!passed) {
                System.out.println("Invalid input");
                continue;
            }
            try {
                y = Integer.parseInt(inputArr[1]);
                if (y <= 0 || y >= 10) {
                    System.out.println("Invalid input");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                continue;
            }
            try {
                num = Integer.parseInt(inputArr[2]);
                if (num < 0 || num >= 10) {
                    System.out.println("Invalid input");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                continue;
            }
            int[] output = new int[3];
            output[0] = x;
            output[1] = y;
            output[2] = num;
            return output;
        }
        return null;
    }

    public static void UpdateBoard(int[] input) {
        board[input[1] - 1][input[0] - 1][0] = input[2];
    }

    public static void PrepareBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 1; k < 10; k++) {
                    if (board[i][j][CELL_NUMBER] == 0) {
                        board[i][j][k] = k;
                    }
                }
            }
        }
    }

    public static void SolveBoard() {
        int square;
        int current, currentsq;
        int count, countsq, acc, protection = 0, counter;
        int x, y, xsq, ysq, xStore = 0, yStore = 0;
        while (true) {
            protection++;
            count = 0;
            for (int i = 0; i < 81; i++) {
                x = order[i][0];
                y = order[i][1];
                if (board[x][y][CELL_NUMBER] == 0) {
                    UpdateNumber(i);
                    for (int j = 0; j < 9; j++) {
                        current = board[x][j][CELL_NUMBER];
                        if (current != 0) {
                            board[x][y][current] = 0;
                        }
                    }
                    for (int j = 0; j < 9; j++) {
                        current = board[j][y][CELL_NUMBER];
                        if (current != 0) {
                            board[x][y][current] = 0;
                        }
                    }
                    square = i / 9;
                    countsq = 0;
                    acc = 0;
                    for (int sq : Square[square]) {
                        xsq = order[sq][0];
                        ysq = order[sq][1];
                        current = board[xsq][ysq][CELL_NUMBER];
                        if (current != 0) {
                            board[x][y][current] = 0;
                            countsq++;
                        }
                    }
                    if (countsq == 8) {
                        DrawBoard();
                        for (int sq : Square[square]) {
                            xsq = order[sq][0];
                            ysq = order[sq][1];
                            currentsq = board[xsq][ysq][CELL_NUMBER];
                            if (currentsq != 0) {
                                acc += currentsq;
                            } else {
                                xStore = xsq;
                                yStore = ysq;
                            }
                        }
                        board[xStore][yStore][0] = 45 - acc;
                    }
                    if (protection >= 25) {
                        for (int j = 1; j < 10; j++) {
                            counter = 0;
                            for (int k = 0; k < 9; k++) {
                                if (board[x][k][j] != 0) {
                                    counter++;
                                }
                            }
                            if (counter == 1) {
                                for (int k = 0; k < 9; k++) {
                                    if (board[x][k][j] != 0) {
                                        board[x][k][0] = j;
                                        for (int l = 1; l < 10; l++) {
                                            if ((board[x][k][l] != board[x][k][CELL_NUMBER])) {
                                                board[x][k][l] = 0;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (protection >= 50) {
                        for (int j = 1; j < 10; j++) {
                            counter = 0;
                            for (int k = 0; k < 9; k++) {
                                if (board[k][y][j] != 0) {
                                    counter++;
                                }
                            }
                            if (counter == 1) {
                                for (int k = 0; k < 9; k++) {
                                    if (board[k][y][j] != 0) {
                                        board[k][y][CELL_NUMBER] = j;
                                        for (int l = 1; l < 10; l++) {
                                            if ((board[k][y][l] != board[k][y][CELL_NUMBER])) {
                                                board[k][y][l] = 0;
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                    if (protection >= 75) {
                        for (int j = 1; j < 10; j++) {
                            counter = 0;
                            for (int k : Square[square]) {
                                if (board[order[k][0]][order[k][1]][j] != 0) {
                                    counter++;
                                }
                            }
                            if (counter == 1) {
                                for (int k : Square[square]) {
                                    if (board[order[k][0]][order[k][1]][j] != 0) {
                                        board[order[k][0]][order[k][1]][CELL_NUMBER] = j;
                                        for (int l = 1; l < 10; l++) {
                                            if (board[order[k][0]][order[k][1]][l] != board[order[k][0]][order[k][1]][CELL_NUMBER]) {
                                                board[order[k][0]][order[k][1]][l] = 0;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    UpdateNumber(i);
                    count++;
                }
            }
            if (count == 0) {
                break;
            }
            DrawBoard();
            if (protection >= 100) {
                while (true) {
                    int[] in;
                    in = Input();
                    if (in == null) {
                        break;
                    } else {
                        System.out.println(Arrays.toString(board[in[1] - 1][in[0] - 1]));
                    }
                }
                protection=0;
            }
        }
    }

    public static void UpdateNumber(int i) {
        int cont;
        int x, y;
        cont = 0;
        x = order[i][0];
        y = order[i][1];
        for (int j = 1; j < 10; j++) {
            if ((board[x][y][j] == 0)) {
                cont++;
            }
        }
        if (cont == 8) {
            for (int j = 1; j < 10; j++) {
                if ((board[x][y][j] != 0)) {
                    board[x][y][CELL_NUMBER] = j;
                }
            }
        }
    }

    public static void UseArgs(String[] args) {
        int x, y;
        for (int i = 0; i < 81; i++) {
            x = order[i][0];
            y = order[i][1];
            board[x][y][CELL_NUMBER] = Integer.parseInt(args[i]);
        }
    }
}

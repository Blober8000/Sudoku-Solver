public class Logic {
    public static void solveBoard(Board board) {
        int counter = 0;
        boolean stop = false, failed = false;
        while (!board.checkCompletion() && !stop && !failed) {
            failed = pencilMarking(board, failed);
            failed = nakedSingles(board, failed);
            failed = pencilMarking(board, failed);
            failed = hiddenSingles(board, failed);
            failed = nakedSingles(board, failed);
            failed = pencilMarking(board, failed);
            counter++;
            if (counter == 1) {
                stop = true;
            }
        }
    }

    public static boolean checkCorrectness(Board board) {
        boolean check = true;
        Cell[] temp;
        for (int i = 1; i < 10; i++) {
            temp = board.getRows()[i].getCells();
            for (int j = 0; j < 9; j++) {
                for (int k = j; k < 9; k++) {
                    if (j != k) {
                        if (temp[j].equals(temp[k]) && temp[j].getValue() != 0 && temp[k].getValue() != 0) {
                            check = false;
                        }
                    }
                }
            }
        }
        for (int i = 1; i < 10; i++) {
            temp = board.getColumns()[i].getCells();
            for (int j = 0; j < 9; j++) {
                for (int k = j; k < 9; k++) {
                    if (j != k) {
                        if (temp[j].equals(temp[k]) && temp[j].getValue() != 0 && temp[k].getValue() != 0) {
                            check = false;
                        }
                    }
                }
            }
        }
        for (int i = 1; i < 10; i++) {
            temp = board.getSquares()[i].getCells();
            for (int j = 0; j < 9; j++) {
                for (int k = j; k < 9; k++) {
                    if (j != k) {
                        if (temp[j].equals(temp[k]) && temp[j].getValue() != 0 && temp[k].getValue() != 0) {
                            check = false;
                        }
                    }
                }
            }
        }

        return check;
    }

    public static boolean pencilMarking(Board board, boolean failed) {
        //do ten passes to ensure every pencil marking that can be done is done
        if (!failed) {
            for (int i = 0; i < 3 && !failed; i++) {
                board.parseCandidates(failed);
                failed = !checkCorrectness(board);
                board.updateBoard(failed);
                failed = !checkCorrectness(board);
            }
            return failed;
        }
        return true;
    }

    public static boolean nakedSingles(Board board, boolean failed) {
        //do a few passes to ensure every naked single is found
        if (!failed) {
            for (int t = 0; t < 3 && !failed; t++) {
                for (int i = 1; i < 10 && !failed; i++) {
                    board.getRows()[i].nakedSingles(failed);
                    failed = !checkCorrectness(board);
                    board.getColumns()[i].nakedSingles(failed);
                    failed = !checkCorrectness(board);
                    board.getSquares()[i].nakedSingles(failed);
                    failed = !checkCorrectness(board);
                }
            }
            return failed;
        }
        return true;
    }

    public static boolean hiddenSingles(Board board, boolean failed) {
        //do a few passes to ensure every hidden single is found
        if (!failed) {
            for (int t = 0; t < 3 && !failed; t++) {
                for (int i = 1; i < 10 && !failed; i++) {
                    board.getRows()[i].hiddenSingles(failed);
                    failed = !checkCorrectness(board);
                    board.getColumns()[i].hiddenSingles(failed);
                    failed = !checkCorrectness(board);
                    board.getSquares()[i].hiddenSingles(failed);
                    failed = !checkCorrectness(board);
                }
            }
            return failed;
        }
        return true;
    }
}

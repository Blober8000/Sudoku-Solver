public class Board {
    public static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    public static final String red = "\u001B[31m", reset = "\u001B[0m", blue = "\u001B[34m", gray = "\u001B[90m", brightBlue = "\u001B[94m";

    private Row[] rows;
    private Column[] columns;
    private Square[] squares;
    private Cell[] cells;

    public Board() {
        // index 0 is left empty.
        // only utilizes 1-9 for ease
        rows = new Row[10];
        columns = new Column[10];
        squares = new Square[10];
        cells = new Cell[81];
        for (int i = 1; i < 10; i++) {
            rows[i] = new Row(this, i);
            columns[i] = new Column(this, i);
            squares[i] = new Square(this, i);
        }
    }

    public Board(Board board) {
        rows = board.rows;
        columns = board.columns;
        squares = board.squares;
        cells = board.cells;
    }

    public Row rowFinder(int y) {
        return switch (y) {
            case 1 -> rows[1];
            case 2 -> rows[2];
            case 3 -> rows[3];
            case 4 -> rows[4];
            case 5 -> rows[5];
            case 6 -> rows[6];
            case 7 -> rows[7];
            case 8 -> rows[8];
            case 9 -> rows[9];
            default -> null;
        };
    }

    public Column columnFinder(int x) {
        return switch (x) {
            case 1 -> columns[1];
            case 2 -> columns[2];
            case 3 -> columns[3];
            case 4 -> columns[4];
            case 5 -> columns[5];
            case 6 -> columns[6];
            case 7 -> columns[7];
            case 8 -> columns[8];
            case 9 -> columns[9];
            default -> null;
        };
    }

    public Square squareFinder(int x, int y) {
        if (x >= 1 && x <= 3) {
            if (y >= 1 && y <= 3) {
                return squares[1];
            } else if (y >= 4 && y <= 6) {
                return squares[4];
            } else if (y >= 7 && y <= 9) {
                return squares[7];
            } else return null;
        } else if (x >= 4 && x <= 6) {
            if (y >= 1 && y <= 3) {
                return squares[2];
            } else if (y >= 4 && y <= 6) {
                return squares[5];
            } else if (y >= 7 && y <= 9) {
                return squares[8];
            } else return null;
        } else if (x >= 7 && x <= 9) {
            if (y >= 1 && y <= 3) {
                return squares[3];
            } else if (y >= 4 && y <= 6) {
                return squares[6];
            } else if (y >= 7 && y <= 9) {
                return squares[9];
            } else return null;
        } else return null;
    }

    public boolean initializeBoard(int[] input) {
        if (input.length != 81) {
            return false;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[j * 9 + i] = new Cell(this, i + 1, j + 1, input[j * 9 + i]);
            }
        }
        for (int i = 1; i < 10; i++) {
            rows[i].initializeRow(i);
            columns[i].initializeColumn(i);
            squares[i].initializeSquare(i);
        }
        return true;
    }

    public void drawBoard() {
        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
        System.out.print("  ");
        for (String ch : letters) {
            System.out.printf("%s%s%s ", red, ch, reset);
        }
        System.out.println();
        for (int i = 0; i < 81; i++) {
            if (i % 9 == 0) {
                System.out.printf("%s%d%s ", red, i / 9 + 1, reset);
            }
            Cell temp = this.getCell(i);
            if (temp.getSquare().equals(squares[1]) || temp.getSquare().equals(squares[3]) || temp.getSquare().equals(squares[5]) || temp.getSquare().equals(squares[7]) || temp.getSquare().equals(squares[9])) {
                if (temp.getValue() == 0) {
                    System.out.printf("%s ", "*");
                } else {
                    if (temp.getGiven()) {
                        System.out.printf("%s%d%s ", reset, temp.getValue(), reset);
                    } else {
                        System.out.printf("%s%d%s ", brightBlue, temp.getValue(), reset);
                    }
                }
            } else {
                if (temp.getValue() == 0) {
                    System.out.printf("%s%s%s ", gray, "*", reset);
                } else {
                    if (temp.getGiven()) {
                        System.out.printf("%s%d%s ", gray, temp.getValue(), reset);
                    } else {
                        System.out.printf("%s%d%s ", blue, temp.getValue(), reset);
                    }
                }
            }
            if (i % 9 == 8) {
                System.out.println();
            }
        }
    }

    public Cell getCell(int index) {
        return cells[index];
    }

    public Cell getCell(int x, int y) {
        return cells[(y - 1) * 9 + (x - 1)];
    }

    public Cell getCell(String xLetter, int y) {
        int x = 0;
        for (int i = 0; i < 9; i++) {
            if (letters[i].equals(xLetter)) {
                x = i;
                break;
            }
        }
        return cells[(y - 1) * 9 + x];
    }

    public Row[] getRows() {
        return rows;
    }

    public Column[] getColumns() {
        return columns;
    }

    public Square[] getSquares() {
        return squares;
    }

    public void updateBoard(boolean failed) {
        if (!failed) {
            for (int i = 0; i < 81; i++) {
                this.cells[i].pencilMarking();
            }

        }
    }

    public void parseCandidates(boolean failed) {
        if (!failed) {
            for (int i = 0; i < 81; i++) {
                this.cells[i].updateCandidates();
            }
        }
    }

    public boolean checkCompletion() {
        int counter = 0;
        for (int i = 0; i < 81; i++) {
            if (cells[i].getValue() != 0) {
                counter++;
            }
        }
        if (counter == 81) {
            return true;
        } else return false;
    }

    public Cell[] getCells() {
        return cells;
    }

    public int[] boardCopy() {
        int[] newBoard = new int[81];
        for (int i = 0; i < 81; i++) {
            newBoard[i] = this.cells[i].getValue();
        }
        return newBoard;
    }
}

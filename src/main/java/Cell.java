import java.util.Arrays;

public class Cell {
    public static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    private Board board;
    private Row row;
    private Column column;
    private Square square;
    private int x;
    private int y;
    private boolean given;
    private int[] candidates;
    private int value;

    public Cell() {
    }

    public Cell(Board board, int x, int y, int value) {
        this.board = board;
        this.x = x;
        this.y = y;
        if (value != 0) given = true;
        else given = false;
        if (value != 0) {
            int[] temp = new int[10];
            for (int i = 0; i < 10; i++) {
                if (i != value) {
                    temp[i] = 0;
                } else {
                    temp[i] = value;
                }
            }
            this.candidates = Arrays.copyOf(temp,temp.length);;
        } else {
            this.candidates = new int[10];
            for (int i = 0; i < 10; i++) {
                this.candidates[i] = i;
            }
        }
        this.value = value;
        row = board.rowFinder(y);
        column = board.columnFinder(x);
        square = board.squareFinder(x, y);
    }

    public int getValue() {
        return value;
    }

    public boolean getGiven() {
        return given;
    }

    public void pencilMarking() {
        if (this.value == 0) {
            int count = 0;
            int only = 0;
            for (int i = 1; i < 10; i++) {
                if (this.candidates[i] == 0) {
                    count++;
                } else {
                    only = i;
                }
            }
            if (count == 8) {
                this.setValue(only);
                System.out.println("Pencil Marking " + this + " " + (only));
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Row getRow() {
        return row;
    }

    public Column getColumn() {
        return column;
    }

    public Square getSquare() {
        return square;
    }

    public int[] getCandidates() {
        return Arrays.copyOf(candidates, candidates.length);
    }

    @Override
    public boolean equals(Object obj) {
        Cell temp = (Cell) obj;
        return this.value == temp.getValue();

    }

    @Override
    public String toString() {
        return String.format("%s %d %d", letters[x - 1], y, value);
    }

    public void setValue(int value) {
        this.value = value;
        int[] temp = new int[10];
        for (int i = 0; i < 10; i++) {
            if (i != value) {
                temp[i] = 0;
            } else {
                temp[i] = value;
            }
        }
        System.out.println(Arrays.toString(temp));
        this.candidates = Arrays.copyOf(temp,temp.length);
    }

    public void updateCandidates() {
        if (this.value == 0) {
            for (int i = 0; i < 10; i++) this.candidates[i] = i;

            for (Cell cell : this.row.getCells()) {
                if (cell.getValue() != 0) {
                    this.candidates[cell.getValue()] = 0;
                }
            }

            for (Cell cell : this.column.getCells()) {
                if (cell.getValue() != 0) {
                    this.candidates[cell.getValue()] = 0;
                }
            }

            for (Cell cell : this.square.getCells()) {
                if (cell.getValue() != 0) {
                    this.candidates[cell.getValue()] = 0;
                }
            }
        }
    }

    public void setCandidates(int[] candidates) {
        this.candidates = Arrays.copyOf(candidates,candidates.length);
    }
}

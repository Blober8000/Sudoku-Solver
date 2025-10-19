public class Row {
    private final Board board;
    private Cell[] cells;
    private int number;

    public Row(Board board, int number) {
        this.board = board;
        cells = new Cell[9];
        this.number = number;
    }

    public void initializeRow(int row) {
        int counter = 0;
        for (int i = 0; i < 81; i++) {
            Cell temp = board.getCell(i);
            if (temp.getY() == row) {
                cells[counter] = temp;
                counter++;
            }
        }

    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public boolean equals(Object obj) {
        Row temp = (Row) obj;
        if (this.number == temp.getNumber()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Row %d", this.number);
    }

    public Cell[] getCells() {
        return this.cells;
    }

    public void nakedSingles(boolean failed) {
        if (!failed) {
            boolean test = false;
            int counter = 0;
            int sum = 0;
            Cell temp = new Cell();
            for (Cell cell : cells) {
                if (cell.getValue() != 0) {
                    counter++;
                }
            }
            if (counter == 8) {
                test = true;
            }
            if (test) {
                for (Cell cell : cells) {
                    if (cell.getValue() != 0) {
                        sum += cell.getValue();
                    } else {
                        temp = cell;
                    }
                }
                System.out.println("Row Naked " + temp + " " + (45 - sum));
                temp.setValue(45 - sum);
                board.parseCandidates(failed);
            }
        }
    }

    public void hiddenSingles(boolean failed) {
        if(!failed) {
            int counter;
            int value = 0;
            boolean found = false;
            Cell temp;
            for (int i = 1; i < 10; i++) {
                counter = 0;
                for (Cell cell : cells) {
                    if (cell.getValue() != 0) {
                        continue;
                    } else if (cell.getCandidates()[i] != 0) {
                        counter++;
                    }
                }
                if (counter == 1) {
                    value = i;
                    found = true;
                    break;
                }
            }
            if (found) {
                for (Cell cell : cells) {
                    if (cell.getValue() != 0) {
                        continue;
                    } else if (cell.getCandidates()[value] != 0) {
                        temp = cell;
                        System.out.println("Row Hidden " + temp + " " + (value));
                        temp.setValue(value);
                        board.parseCandidates(failed);
                        break;
                    }
                }
            }
        }
    }
}

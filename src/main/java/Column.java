import java.util.ArrayList;

public class Column {
    public static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    private final Board board;
    private Cell[] cells;
    private int number;

    public Column(Board board, int number) {
        this.board = board;
        cells = new Cell[9];
        this.number = number;
    }

    public void initializeColumn(int column) {
        int counter = 0;
        for (int i = 0; i < 81; i++) {
            Cell temp = board.getCell(i);
            if (temp.getX() == column) {
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
        Column temp = (Column) obj;
        if (this.number == temp.getNumber()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Column %s", letters[this.number - 1]);
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
                temp.setValue(45 - sum);
                board.parseCandidates(failed);
            }
        }
    }

    public void hiddenSingles(boolean failed) {
        if (!failed) {
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
                        temp.setValue(value);
                        board.parseCandidates(failed);
                        break;
                    }
                }
            }
        }
    }

    public void obviousPairs(boolean failed) {
        if (!failed) {
            int counter;
            int pos = 0;
            int[] candidates = new int[9];
            for (Cell cell : cells) {
                counter = 0;
                for (int i : cell.getCandidates()) {
                    if (i != 0) {
                        counter++;
                    }
                }
                candidates[pos] = counter;
                pos++;
            }

            pos = 0;
            ArrayList<Integer> positions = new ArrayList<>();
            for (int i : candidates) {
                if (i == 2) {
                    positions.add(pos);
                }
                pos++;
            }

            int[][] pairs = new int[positions.size()][2];
            for (int i = 0; i < positions.size(); i++) {
                int[] pair = new int[2];
                for (int j : cells[positions.get(i)].getCandidates()) {
                    if (j != 0) {
                        if (pair[0] == 0) {
                            pair[0] = j;
                        } else {
                            pair[1] = j;
                        }
                    }
                }
                pairs[i] = pair;
            }

            for (int i = 0; i < pairs.length; i++) {
                for (int j = i+1; j < pairs.length; j++) {
                    if (pairs[i][0] == pairs[j][0] && pairs[i][1] == pairs[j][1]) {
                        int candidate1 = pairs[i][0], candidate2 = pairs[i][1];
                        pos = 0;
                        for (Cell cell : cells) {
                            if (pos == positions.get(i) || pos == positions.get(j)) {
                                pos++;
                                continue;
                            }
                            int[] temp = cell.getCandidates();
                            temp[candidate1] = 0;
                            temp[candidate2] = 0;
                            cell.setCandidates(temp);
                            pos++;
                        }
                        board.parseCandidates(failed);
                    }
                }
            }
        }
    }
}

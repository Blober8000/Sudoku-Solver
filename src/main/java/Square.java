public class Square{
    private final Board board;
    private Cell[] cells;
    private int number;

    public Square(Board board, int number){
        this.board = board;
        cells = new Cell[9];
        this.number = number;
    }

    public void initializeSquare(int square){
        int counter = 0;
        int xMin,xMax,yMin,yMax;
        switch(square){
            case 1: xMin=1; xMax=3;yMin=1; yMax=3; break;
            case 4: xMin=1; xMax=3;yMin=4; yMax=6; break;
            case 7: xMin=1; xMax=3;yMin=7; yMax=9; break;
            case 2: xMin=4; xMax=6;yMin=1; yMax=3; break;
            case 5: xMin=4; xMax=6;yMin=4; yMax=6; break;
            case 8: xMin=4; xMax=6;yMin=7; yMax=9; break;
            case 3: xMin=7; xMax=9;yMin=1; yMax=3; break;
            case 6: xMin=7; xMax=9;yMin=4; yMax=6; break;
            case 9: xMin=7; xMax=9;yMin=7; yMax=9; break;
            default: xMin=0; xMax=0; yMin=0; yMax=0;
        }

        for(int i = 0; i < 81; i++) {
            Cell temp = board.getCell(i);
            if (temp.getX() >= xMin && temp.getX() <= xMax) {
                if (temp.getY() >= yMin && temp.getY() <= yMax) {
                    cells[counter] = temp;
                    counter++;
                }
            }
        }
    }

    public int getNumber(){
        return this.number;
    }

    @Override
    public boolean equals(Object obj){
        Square temp = (Square) obj;
        if(this.number == temp.getNumber()){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return String.format("Square %d", this.number);
    }

    public Cell[] getCells(){
        return this.cells;
    }

    public void nakedSingles(boolean failed){
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
}

package alda.backtracking;


public class ChessBoard {
    private boolean board[][] = new boolean[8][8];

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if (i == 0) stringBuilder.append("\n      1   2   3   4   5   6   7   8\n");
            stringBuilder.append("    ---------------------------------\n");
            for (int j = 0; j < 8; j++) {
                if (j == 0) stringBuilder.append((i + 1) + "   ");
                stringBuilder.append((board[i][j]) ? "| Q " : "|   ");
                if (j == 7) stringBuilder.append("|\t");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("    ---------------------------------\n");
        return stringBuilder.toString();
    }

    public void setPiece(int row, int column) {
        board[row][column] = true;
    }

    public void removePiece(int row, int column) {
        board[row][column] = false;
    }

    public boolean isThreatened(int row, int column) {
        for (int i = 0; i < 8; i++) {
            if (board[row][i]) {
                return true;
            }

            if (board[i][column]) {
                return true;
            }

            if (isThreatenedDiagonal(row, column, "upLeft")) return true;
            if (isThreatenedDiagonal(row, column, "upRight")) return true;
            if (isThreatenedDiagonal(row, column, "downLeft")) return true;
            if (isThreatenedDiagonal(row, column, "downRight")) return true;
        }
        return false;
    }

    private boolean isThreatenedDiagonal(int row, int column, String direction) {
        int x = row;
        int y = column;
        while (x >= 0 && y >= 0 && x <= 7 && y <= 7) {
            if (board[x][y]) {
                return true;
            }
            if (direction.equals("upLeft")) {
                x--;
                y--;
            }
            if (direction.equals("upRight")) {
                x--;
                y++;
            }
            if (direction.equals("downLeft")) {
                x++;
                y--;
            }
            if (direction.equals("downRight")) {
                x++;
                y++;
            }
        }
        return false;
    }

    public void findNextSolution(int row) {
        if (row == -1) row = 0; //row will only be -1 once ALL 92 solutions have been found.
        if (row == 8) return; //If SuccessfulSolution on last row end (ArrayIndexOutOfBoundsException: 8)

        if (rowIsEmpty(row)) {
            if (piecePlacedOnRowSuccessful(row)) {
                findNextSolution(row + 1);
            } else {
                findNextSolution(row - 1);
            }

        } else { //If Row is Not Empty
            if (movePieceOnCurrentRow(row)) {
                findNextSolution(row + 1);
            } else {
                findNextSolution(row - 1);
            }
        }
    }

    private boolean movePieceOnCurrentRow(int row) {
        boolean addPiece = false;
        for (int i = 0; i < 8; i++) {

            if (addPiece && !isThreatened(row, i)) {
                setPiece(row, i);
                return true;
            }

            if (board[row][i] && !addPiece) {
                removePiece(row, i);
                addPiece = true;
            }

        }
        return false;
    }

    private boolean rowIsEmpty(int row) {
        for (int i = 0; i < 8; i++) {
            if (board[row][i]) return false;
        }
        return true;
    }

    private boolean piecePlacedOnRowSuccessful(int row) {
        for (int i = 0; i < 8; i++) {
            if (!isThreatened(row, i)) {
                setPiece(row, i);
                return true;
            }
        }
        return false;
    }
}


/**
 * @author Adrian Bergman adbe0777 bergman.adrian@gmail.com
 * @author Sebastian Backstrom Pino sebc5325 s.backstrompino@gmail.com
 * @author Martin Senden mase4691 martin.senden@gmail.com
 * @since 2018-03-01
 */

package alda.backtracking;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;

public class EightQueens {
    private Queue<String> savedSolutions = new LinkedList<>();
    private ChessBoard board = new ChessBoard();

    public static void main(String[] args) {
        EightQueens eQ = new EightQueens();
        eQ.go();
    }

    public void go() {
        System.out.println("Welcome to our Eight Queen Solver.");
        board.findNextSolution(0);
        System.out.println(board);
        savedSolutions.add(board.toString());

        for (int i = 1; i < 93; i++) {
            board.findNextSolution(7);
            System.out.println(board);
            savedSolutions.add(board.toString());
        }
        exportSavedSolutionsToFile();
    }

    private void exportSavedSolutionsToFile() {
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream("output.txt"));
            System.setOut(printStream);
            int i = 1;
            while (savedSolutions.peek() != null) {
                System.out.println("\nSolution " + i);
                System.out.println(savedSolutions.poll());
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("IOExceFileNotFoundExceptionption: " + e);
        }
    }
}

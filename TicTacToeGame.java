import java.util.Random;
import java.util.Scanner;

public class TicTacToeGame {

    static char[] board = new char[9];
    static int scoreAI = 0;
    static int scoreOpponent = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println("The grid counts down from left to right like on paper, 9 would be the last, first would be the first, 5");
        System.out.println("would be the middle");
        initializeBoard();
        playGame();
    }

    public static void initializeBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
        }
    }

    public static void printBoard() {
        System.out.println("\n");
        System.out.println(board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("--+---+--");
        System.out.println(board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("--+---+--");
        System.out.println(board[6] + " | " + board[7] + " | " + board[8]);
        System.out.println("\n");
    }

    public static boolean isValidMove(int position) {
        return position >= 0 && position < 9 && board[position] == ' ';
    }

    public static boolean checkWinner(char player) {
        return (board[0] == player && board[1] == player && board[2] == player) ||
               (board[3] == player && board[4] == player && board[5] == player) ||
               (board[6] == player && board[7] == player && board[8] == player) ||
               (board[0] == player && board[3] == player && board[6] == player) ||
               (board[1] == player && board[4] == player && board[7] == player) ||
               (board[2] == player && board[5] == player && board[8] == player) ||
               (board[0] == player && board[4] == player && board[8] == player) ||
               (board[2] == player && board[4] == player && board[6] == player);
    }

    public static boolean isBoardFull() {
        for (char c : board) {
            if (c == ' ') {
                return false;
            }
        }
        return true;
    }

    public static void playGame() {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        char currentPlayer = 'O'; 
        boolean gameWon = false;

        while (!gameWon) {
            printBoard();
            int move = -1;

            if (currentPlayer == 'O') {
                System.out.println("Opponent's turn. Choose a position (1-9): ");
                if (scanner.hasNextInt()) {
                    move = scanner.nextInt() - 1;
                    if (isValidMove(move)) {
                        board[move] = 'O';
                        currentPlayer = 'X';
                    } else {
                        System.out.println("Invalid move. Try again.");
                        continue;
                    }
                } else {
                    System.out.println("Invalid input. Enter a number between 1 and 9.");
                    scanner.next(); 
                    continue;
                }
            } else if (currentPlayer == 'X') {
                move = aiMove();
                System.out.println("AI chooses position: " + (move + 1));
                board[move] = 'X';
                currentPlayer = 'O';
            }

            if (checkWinner('X')) {
                printBoard();
                System.out.println("AI wins!");
                scoreAI++;
                gameWon = true;
            } else if (checkWinner('O')) {
                printBoard();
                System.out.println("Opponent wins!");
                scoreOpponent++;
                gameWon = true;
            } else if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                gameWon = true;
            }
        }

        System.out.println("Score: AI = " + scoreAI + ", Opponent = " + scoreOpponent);
        System.out.println("Play again? (y/n): ");
        String playAgain = scanner.next();
        if (playAgain.equalsIgnoreCase("y")) {
            initializeBoard();
            playGame();
        } else {
            System.out.println("Game Over!");
        }
    }

    public static int aiMove() {
        int move = findWinningMove('X');
        if (move != -1) return move;

        move = findWinningMove('O');
        if (move != -1) return move;

        if (board[4] == ' ') return 4;

        int[] corners = {0, 2, 6, 8};
        for (int corner : corners) {
            if (board[corner] == ' ') return corner;
        }

        Random rand = new Random();
        do {
            move = rand.nextInt(9);
        } while (!isValidMove(move));

        return move;
    }

    public static int findWinningMove(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i * 3] == player && board[i * 3 + 1] == player && board[i * 3 + 2] == ' ') return i * 3 + 2;
            if (board[i * 3 + 1] == player && board[i * 3 + 2] == player && board[i * 3] == ' ') return i * 3;
            if (board[i * 3] == player && board[i * 3 + 2] == player && board[i * 3 + 1] == ' ') return i * 3 + 1;

            if (board[i] == player && board[i + 3] == player && board[i + 6] == ' ') return i + 6;
            if (board[i + 3] == player && board[i + 6] == player && board[i] == ' ') return i;
            if (board[i] == player && board[i + 6] == player && board[i + 3] == ' ') return i + 3;
        }
        if (board[0] == player && board[4] == player && board[8] == ' ') return 8;
        if (board[4] == player && board[8] == player && board[0] == ' ') return 0;
        if (board[2] == player && board[4] == player && board[6] == ' ') return 6;
        if (board[4] == player && board[6] == player && board[2] == ' ') return 2;

        return -1;
    }
}

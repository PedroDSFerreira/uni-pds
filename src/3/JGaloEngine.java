package lab03;


public class JGaloEngine implements JGaloInterface {
    private char[][] board;
    private char currentPlayer;

    private JGaloEngine(char firstPlayer) {
        board = newBoard();
        currentPlayer = firstPlayer;
    }

    public char getActualPlayer() {
        return currentPlayer;
    }

    public boolean setJogada(int lin, int col) {
        if (board[lin - 1][col - 1] == ' ') {
            board[lin - 1][col - 1] = currentPlayer;
            // Change the current player
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            return true;
        }
        return false;
    }

    public boolean isFinished() {
        for (int i = 0; i < 3; i++) {
            // Check the rows and columns
            if ((board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) || 
                (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i])) {
                return true;
            }
        }

        // Check the diagonals
        if ((board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) || 
            (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
            return true;
        }

        // Check if the board is full
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;

    }

    public char checkResult() {
        for (int i = 0; i < 3; i++) {
            // Check the rows
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
            // Check the columns
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        }

        // Check the diagonals
        if ((board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) || 
            (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
            return board[1][1];
        }


        return ' ';
    }

    private static char[][] newBoard() {
        char[][] board = new char[3][3];
        // Fill the board with spaces
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        return board;
    }
    
    public static JGaloEngine getInstance(Character firstPlayer) {
        return new JGaloEngine(firstPlayer);
    }
}

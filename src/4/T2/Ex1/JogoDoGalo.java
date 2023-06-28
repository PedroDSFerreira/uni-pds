package lab04.T2.Ex1;

public class JogoDoGalo implements JGaloInterface{

    private char player;
    private char[][] board = new char[3][3];
    private char winner;

    public JogoDoGalo(char player) {
        this.player = player;
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                board[i][j] = ' ';
    }

    public char getActualPlayer() {
        return player;
    }

    public boolean setJogada(int lin, int col) {
        boolean retVal;
        if(board[lin-1][col-1] == 'X' || board[lin-1][col-1] == 'O')
            retVal = false;
        else{
            board[lin-1][col-1] = this.player;
            retVal = true;
        }
        if(player == 'X')
            player = 'O';
        else
            player = 'X';
        return retVal;
    }

    public boolean isFinished() {
        for(int i = 0; i < 3; i++){
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' '){
                this.winner = board[i][0];
                return true;
            }
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' '){
                this.winner = board[0][i];
                return true;
            }
        }
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ')
        {
            this.winner = board[0][0];
            return true;
        }
        if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' '){
            this.winner = board[0][2];
            return true;
        }
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(board[i][j] == ' ')
                    return false;
        this.winner = ' ';
        return true;
    }

    public char checkResult(){
        return this.winner;
    }

}

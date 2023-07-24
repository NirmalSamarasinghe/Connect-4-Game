package lk.ijse.dep.service;

public class AiPlayer extends Player {
    private Board newBoard;

    public AiPlayer(Board newBoard) {
        super();
        this.newBoard = newBoard;
    }

    @Override
    public void movePiece(int col) {

        col = findBestMove();
        newBoard.updateMove(col, Piece.GREEN);
        newBoard.getBoardUI().update(col, false);
        if (newBoard.findWinner().getWinningPiece().equals(Piece.EMPTY)) {
            if (!newBoard.existLegalMove()) {
                newBoard.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
            }
        } else {
            newBoard.getBoardUI().notifyWinner(newBoard.findWinner());
        }
    }

    public int findBestMove() {

        boolean userWinningState = false;
        int tiedColumn = 0;


            for (int i = 0; i < this.newBoard.NUM_OF_COLS; i++) {
                if (this.newBoard.isLegalMove(i) && this.newBoard.existLegalMove()) {
                    int row = this.newBoard.findNextAvailableSpot(i);
                    this.newBoard.updateMove(i, Piece.GREEN);
                    int score = minimax(0, false);
                    this.newBoard.updateMove(i, row, Piece.EMPTY);

                    if (score == 1) {
                        return i;
                    }

                    if (score == -1) {
                        userWinningState = true;
                    } else {
                        tiedColumn = i;
                    }
                }
            }
        if (userWinningState && this.newBoard.isLegalMove(tiedColumn)) {
            return tiedColumn;
        }

        int col=0;

        do {
            col = (int) (Math.random() * 6);
        } while (!newBoard.isLegalMove(col));

        return col;
    }

    public int minimax(int depth,boolean maximizingPlayer){

        Winner winner = this.newBoard.findWinner();

        if (winner.getWinningPiece().equals(Piece.GREEN)){
            return 1;
        }

        if (winner.getWinningPiece().equals(Piece.BLUE)){
            return -1;
        }

        if (depth ==2 || !this.newBoard.existLegalMove()){
            return 0;
        }

        if (maximizingPlayer){
            for (int i=0;i<this.newBoard.NUM_OF_COLS;i++){
                if (this.newBoard.isLegalMove(i)) {
                    int row = this.newBoard.findNextAvailableSpot(i);
                    this.newBoard.updateMove(i, Piece.GREEN);
                    int heuristicVal = minimax(depth+1, false);
                    this.newBoard.updateMove(i,row,Piece.EMPTY);

                    if (heuristicVal==1){
                        return 1;
                    }
                }
            }
        }else {
            for (int i = 0; i < this.newBoard.NUM_OF_COLS; i++) {
                if (this.newBoard.isLegalMove(i)) {
                    int row = this.newBoard.findNextAvailableSpot(i);
                    this.newBoard.updateMove(i, Piece.BLUE);
                    int heuristicVal = minimax(depth + 1, true);
                    this.newBoard.updateMove(i, row, Piece.EMPTY);

                    if (heuristicVal == -1) {
                        return -1;
                    }
                }
            }
        }
        return 0;
    }
}






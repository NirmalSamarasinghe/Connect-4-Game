package lk.ijse.dep.service;

public class HumanPlayer extends Player{
    private Board newBoard;

    public HumanPlayer(Board newBoard) {

        this.newBoard = newBoard;
    }

    @Override
    public void movePiece(int col) {
        if (newBoard.isLegalMove(col)){
            newBoard.updateMove(col,Piece.BLUE);
            newBoard.getBoardUI().update(col,true);
            if (newBoard.findWinner().getWinningPiece().equals(Piece.EMPTY)){
                if (!newBoard.existLegalMove()){
                    newBoard.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
                }
            }else{
                newBoard.getBoardUI().notifyWinner(newBoard.findWinner());

            }
        }

    }
}

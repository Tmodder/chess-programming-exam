package chess;
import java.util.ArrayList;
import java.util.Collection;

public interface PieceMovesCalculator {
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition);

    static boolean canTakeSquare(ChessBoard board, ChessPosition movePosition,
                                 ChessGame.TeamColor myColor)
    {
        ChessPiece blockingPiece = board.getPiece(movePosition);
        if (blockingPiece != null)
        {
            return blockingPiece.getTeamColor() != myColor;
        }
        return true;
    }

    static Collection<ChessMove> addLine(ChessBoard board, ChessPosition myPosition, String lineType)
    {
        Collection<ChessMove> moves = new ArrayList<>();
        int rowShift = 0;
        int colShift = 0;
        switch(lineType) {
            case "rank":
                rowShift = 1;
                break;
            case "file":
                colShift = 1;
                break;
            case "right":
                colShift = 1;
                rowShift = 1;
                break;
            case "left":
                colShift = -1;
                rowShift = 1;
                break;
            default:
                break;
            }
        for (int i = 0; i < 2; i++)
        {
            boolean isBlocked = false;
            boolean offBoard = false;
            while (!isBlocked && !offBoard)
            {
                int moveRow = myPosition.getRow() + rowShift;
                int moveCol = myPosition.getColumn() + colShift;
                if (moveRow > 8 || moveRow < 1 || moveCol > 8 || moveCol < 1)
                {
                    offBoard = true;
                    break;
                }

                var pos = new ChessPosition(moveRow,moveCol);
                if(!PieceMovesCalculator.canTakeSquare(board,pos,board.getPiece(myPosition).getTeamColor()))
                {
                    isBlocked = true;
                    break;
                }

                var move = new ChessMove(myPosition,pos,null);
                moves.add(move);
                ChessPiece blockingPiece = board.getPiece(pos);
                if(blockingPiece != null)
                {
                    isBlocked = true;
                    break;
                }
                if (rowShift < 0)
                {
                    rowShift += -1;
                }
                    else if(rowShift > 0) {
                        rowShift += 1;
                }
                if (colShift < 0)
                {
                    colShift += -1;
                }
                else if(colShift > 0) {
                    colShift += 1;
                }


            }
            offBoard = false;
            isBlocked = false;
            if (rowShift < 0)
            {
                rowShift = 1;
            }
            else if(rowShift > 0) {
                rowShift = -1;
            }
            if (colShift < 0)
            {
                colShift = 1;
            }
            else if(colShift > 0) {
                colShift = -1;
            }
        }
        return moves;
    }

}

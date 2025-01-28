package chess;
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

}

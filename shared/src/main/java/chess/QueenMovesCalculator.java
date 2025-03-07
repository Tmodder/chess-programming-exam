package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMovesCalculator implements PieceMovesCalculator {

    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<ChessMove>();
        String[] lineList = {"rank", "file", "right", "left"};
        for (String line : lineList)
        {
            moves.addAll(PieceMovesCalculator.addLine(board,myPosition,line));
        }
        return moves;
    }
}

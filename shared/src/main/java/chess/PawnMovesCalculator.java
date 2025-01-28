package chess;

import java.util.Collection;
import java.util.ArrayList;
import java.util.EnumSet;

public class PawnMovesCalculator implements PieceMovesCalculator{
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition)
    {
        var myColor = board.getPiece(myPosition).getTeamColor();
        var moves = new ArrayList<ChessMove>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessPosition> movePositions = new ArrayList<ChessPosition>();
        if (row == 7 && myColor == ChessGame.TeamColor.BLACK || row == 2 && myColor == ChessGame.TeamColor.WHITE)
        {
            movePositions.addAll(advance(board,myPosition,myColor,true));
        }
        else
        {
            movePositions.addAll(advance(board, myPosition, myColor, false));
        }
        movePositions.addAll(tryCapture(board, myPosition, myColor));
        if (!movePositions.isEmpty())
        {
            for (ChessPosition pos : movePositions)
            {
                if(pos.getRow() == 8 && myColor == ChessGame.TeamColor.WHITE ||
                        pos.getRow() == 1 && myColor == ChessGame.TeamColor.BLACK)
                {
                    EnumSet<ChessPiece.PieceType> typeSet = EnumSet.range(ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.ROOK);
                    for (ChessPiece.PieceType type : typeSet)
                    {
                        var move = new ChessMove(myPosition,pos,type);
                        moves.add(move);
                    }
                }
                else {
                    var move = new ChessMove(myPosition, pos, null);
                    moves.add(move);
                }

            }
        }
        return moves;

    }

    public Collection<ChessPosition> advance(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor myColor,
                                             boolean isDouble)
    {
        Collection<ChessPosition> movePositions = new ArrayList<ChessPosition>();
        int rowShift = myColor == ChessGame.TeamColor.WHITE ? 1 : -1;
        int moveRow = myPosition.getRow() + rowShift;
        var movePos = new ChessPosition(moveRow, myPosition.getColumn());
        int i = 0;
        do
        {
            if(board.getPiece(movePos) == null)
            {
                movePositions.add(movePos);
            }
            else
            {
                return movePositions;
            }
            if(i == 1)
            {
                isDouble = false;
            }
            moveRow += rowShift;
            movePos = new ChessPosition(moveRow, myPosition.getColumn());
            i++;
        }  while(isDouble);
       return movePositions;
    }

    public Collection<ChessPosition> tryCapture(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor myColor)
    {
        int rowShift = myColor == ChessGame.TeamColor.WHITE ? 1 : -1;
        int moveRow = myPosition.getRow() + rowShift;
        var leftCapture = new ChessPosition(moveRow,myPosition.getColumn() - 1);
        var rightCapture = new ChessPosition(moveRow,myPosition.getColumn() + 1);
        var capturePos = new ArrayList<ChessPosition>();
        if (leftCapture.getColumn() > 0 && leftCapture.getColumn() < 9)
        {
            capturePos.add(leftCapture);
        }
        if (rightCapture.getColumn() > 0 && rightCapture.getColumn() < 9)
        {
            capturePos.add(rightCapture);
        }
        var finalPos = new ArrayList<ChessPosition>();
        for (ChessPosition pos : capturePos)
        {
            if (board.getPiece(pos)!= null && PieceMovesCalculator.canTakeSquare(board,pos,myColor))
            {
                finalPos.add(pos);
            }
        }
        return finalPos;

    }



}

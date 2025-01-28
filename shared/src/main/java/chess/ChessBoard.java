package chess;

import java.util.Arrays;
import java.util.Objects;


/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] board = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = 8 - position.getRow();
        int col = position.getColumn() - 1;
        board[row][col] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = 8 - position.getRow();
        int col = position.getColumn() - 1;
        return board[row][col];
    }

    public void loadBoard(String boardText)
    {
        char[] chars = boardText.toCharArray();
        int row = 0;
        int col = 0;
        for (char c : chars)
        {
            switch(c)
            {
                case '~':
                    col++;
                case ' ':
                    break;
                case '\n':
                    row++;
                    col = 0;
                    break;
                default:
                    var color = Character.isLowerCase(c) ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE;
                    c = Character.toLowerCase(c);
                    var type = charToPiece(c);
                    var piece = new ChessPiece(color, type);
                    board[row][col] = piece;
                    col++;
                    break;
            }
        }
    }

    public ChessPiece.PieceType charToPiece(char c)
    {
        return switch(c)
        {
            case 'k' -> ChessPiece.PieceType.KING;
            case 'q' -> ChessPiece.PieceType.QUEEN;
            case 'b' -> ChessPiece.PieceType.BISHOP;
            case 'r' -> ChessPiece.PieceType.ROOK;
            case 'n' -> ChessPiece.PieceType.KNIGHT;
            case 'p' -> ChessPiece.PieceType.PAWN;
            default -> null;
        };
    }



    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        String initBoard = """
                r n b q k b n r
                p p p p p p p p
                ~ ~ ~ ~ ~ ~ ~ ~
                ~ ~ ~ ~ ~ ~ ~ ~
                ~ ~ ~ ~ ~ ~ ~ ~
                ~ ~ ~ ~ ~ ~ ~ ~
                P P P P P P P P
                R N B Q K B N R
                """;
        loadBoard(initBoard);
    }
}

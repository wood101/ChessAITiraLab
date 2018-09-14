/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Board;
import java.util.Collection;

/**
 * The class for the queen piece
 * @author janne
 */
public class Queen extends Piece{
        private final static int[] PossibleMoveVectors = {-9, -8, -7 -1, 1, 7, 8, 9};
    
    /**
     * Constructor
     * @param position
     * @param pieceSide 
     */
    public Queen (final int position, final Side pieceSide) {
        super(position, pieceSide);
    }
    
    /**
     * Calls PieceUtil class for legal moves.
     * @param board
     * @return 
     */
    
        @Override
    public Collection<Move> getLegalMoves(Board board) {
        return PieceUtil.getLegalVectorMoves(board, this, PossibleMoveVectors);    
    }
    
        @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }
}
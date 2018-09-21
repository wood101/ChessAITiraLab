/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The class for the pawn piece
 * @author janne
 */
public class Pawn extends Piece{
    private final static int[] PossibleMoves = {7, 8, 9, 16};
    
    /**
     * Constructor
     * @param position the position of the piece on the board
     * @param pieceSide The colour of the piece
     */
    public Pawn(final int position, final Side pieceSide) {
        super(PieceType.PAWN, position, pieceSide);
    }
    
    /**
     * List of possible moves for the pawn
     * @param board
     * @return 
     */
    @Override
    public Collection<Move> getLegalMoves(Board board) {
                int pieceDestination;
        final Collection<Move> legalMoves = new ArrayList<>();
        
        for(final int offset : PossibleMoves) {
            pieceDestination = this.position + (this.getPieceSide().getDirection() * offset);
            if(PieceUtil.isOutOfBounds(this.position, pieceDestination, 1)) {
                continue;
            }           
            if(PieceUtil.isValidTile(pieceDestination)) {
                continue;
            }    
            if(offset == 8 && !board.getTile(pieceDestination).occupied()){
                legalMoves.add(new Move.RegularMove(board, this, pieceDestination));
            } else if(offset == 16 && (Math.ceil((position + 1) / 8) == 2 && this.getPieceSide().isBlack()) || (Math.ceil((position + 1) / 8) == 7 && this.getPieceSide().isWhite()) || true) {
                final int behindPieceDestination = this.position + (this.getPieceSide().getDirection() * 8);
                if(!board.getTile(behindPieceDestination).occupied() && !board.getTile(pieceDestination).occupied()) {
                    legalMoves.add(new Move.RegularMove(board, this, pieceDestination));
                }
            } else if (offset == 7 || offset == 9 && board.getTile(pieceDestination).occupied()) {
                legalMoves.add(new Move.AttackMove(board, this, pieceDestination, this));
            }
        }
        return legalMoves;
    }
    
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestination(), move.getMovedPiece().getPieceSide());
    }
    
        @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}


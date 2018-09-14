/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board;

import com.google.common.collect.ImmutableList;
import fi.helsinki.chessai.board.pieces.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class for the chessboard.
 * @author janne
 */
public class Board {
    private final List<Tile> gameboard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    
    /**
     * Constructor
     * @param builder 
     */
    private Board(Builder builder) {
    this.gameboard = createBoard(builder);
    this.whitePieces = activePieces(this.gameboard, Side.WHITE);
    this.blackPieces = activePieces(this.gameboard, Side.BLACK);
    
    final Collection<Move> whiteLegalMoves = legalMoves(this.whitePieces);
    final Collection<Move> blackLegalMoves = legalMoves(this.blackPieces);
    }
    
    /**
     * Returns the tile on a given coordinate.
     * @param tileCoordinate
     * @return 
     */
    public Tile getTile(final int tileCoordinate) {
        return gameboard.get(tileCoordinate);
    }
    
    /**
     * Creates the gameboard of out tiles.
     * @param builder
     * @return List of tiles
     */
    private static List<Tile> createBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtil.NumTiles];
        for (int i = 0; i < BoardUtil.NumTiles; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }
    
    /**
     * Creates the standard chess gameboard with the pieces.
     * @return the gameboard
     */
    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        //Black pieces
        builder.setPiece(new King(4, Side.BLACK));
        builder.setPiece(new Queen(3, Side.BLACK));
        builder.setPiece(new Knight(1, Side.BLACK));
        builder.setPiece(new Knight(6, Side.BLACK));
        builder.setPiece(new Rook(0, Side.BLACK));
        builder.setPiece(new Rook(7, Side.BLACK));
        builder.setPiece(new Bishop(2, Side.BLACK));
        builder.setPiece(new Bishop(5, Side.BLACK));
        for(int i = 8; i <= 15; i++) {
            builder.setPiece(new Pawn(i, Side.BLACK));
        }
        //White pieces
        builder.setPiece(new King(60, Side.WHITE));
        builder.setPiece(new Queen(59, Side.WHITE));
        builder.setPiece(new Knight(57, Side.WHITE));
        builder.setPiece(new Knight(62, Side.WHITE));
        builder.setPiece(new Rook(56, Side.WHITE));
        builder.setPiece(new Rook(63, Side.WHITE));
        builder.setPiece(new Bishop(61, Side.WHITE));
        builder.setPiece(new Bishop(58, Side.WHITE));
        for(int i = 48; i <= 55; i++) {
            builder.setPiece(new Pawn(i, Side.WHITE));
        }
        
        
        builder.setMoveMaker(Side.WHITE);
        return builder.build();
    }
    
    /**
     * Lists the pieces currently on the gameboard.
     * @param gameboard 
     * @param side 
     * @return 
     */
    private Collection<Piece> activePieces(final List<Tile> gameboard, final Side side) {
        final List<Piece> activePieces = new ArrayList<>();
        
        for(final Tile tile : gameboard) {
            if(tile.occupied()) {
                final Piece piece = tile.getPiece();
                if(piece.getPieceSide() == side) {
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }
    
    /**
     * Gives the list of possible legal moves.
     * @param pieces
     * @return 
     */
    private Collection<Move> legalMoves(Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();    
        for (final Piece piece : pieces) {
            legalMoves.addAll(piece.getLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }
    
    /**
     * Class to build the chessboard.
     */
    public static class Builder {
        
        Map<Integer, Piece> boardConfig;
        Side nextMoveMaker;
        
        public Builder() {
            this.boardConfig = new HashMap<>();
        }
        /**
         * Place a piece on the gameboard.
         * @param piece the piece to be placed
         * @return 
         */
        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPosition(), piece);
            return this;
        }
        
        /**
         * Sets whose turn it is at the beginning of the game.
         * @param nextMoveMaker
         * @return 
         */
        public Builder setMoveMaker(final Side nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }
        
        public Board build() {
            return new Board(this);
        }
    }

    /**
     * Represents the chessboard as a String.
     * @return 
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < BoardUtil.NumTiles; i++) {
            final String tileText = this.gameboard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if((i + 1) % 8 == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }
    

}
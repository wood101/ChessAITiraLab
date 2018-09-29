package fi.helsinki.chessai.board;

import fi.helsinki.chessai.board.pieces.Piece;
import java.util.HashMap;
import java.util.Map;

/**
 * A single tile on a chessboard.
 * @author janne
 */
public abstract class Tile {
    protected final int tileCoordinate;
    private static final Map<Integer, EmptyTile> EMPTY_TILES = setAllEmptyTiles();
    private static Map<Integer, EmptyTile> setAllEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for(int i = 0; i <= 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return emptyTileMap;
    }
    /**
     * Makes a new tile.
     * @param tileCoordinate location of the tile
     * @param piece the piece located on the tile
     * @return returns the piece if it is there
     */
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
    }
    
    /**
     * Constructor
     * @param tileCoordinate 
     */
    Tile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }
    

    /**
     * Returns false, if the file doesn't have a piece on it
     * and true if it does.
     * @return  
     */
    public abstract boolean occupied();
    
    /**
     * Returns the piece on the tile.
     * @return 
     */
    public abstract Piece getPiece();
    
    /**
     * Return the coordinate of the tile.
     * @return 
     */
    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    /**
     * Class for an empty tile
     */
    public static final class EmptyTile extends Tile {
        private EmptyTile(int coordinate) {
             super(coordinate);
        }
         
         @Override
        public boolean occupied() {
             return false;
        }
         
         @Override
        public Piece getPiece() {
             return null;
        }
    }
        @Override
        public String toString() {
           return "-";
        }
    /**
     * Class for an occupied tile
     */
    public static final class OccupiedTile extends Tile {
        private final Piece tilePiece;
                
        private OccupiedTile(int tileCoordinate, Piece tilePiece) {
            super(tileCoordinate);
            this.tilePiece = tilePiece;
        }
        
        @Override
        public boolean occupied() {
            return true;
        }
        
        @Override
        public Piece getPiece() {
            return this.tilePiece;
        }
        
        @Override
        public String toString() {
           return getPiece().getPieceSide().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }
    }
    
}

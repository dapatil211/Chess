
public class Move {
	Piece piece;
	int oldRow, oldCol, newRow, newCol;
	Square s1, s2;
	public Move(Piece p, int oldRow, int oldCol, int newRow, int newCol) {
		this.piece = p;
		this.oldRow = oldRow;
		this.oldCol = oldCol;
		this.newCol = newCol;
		this.newRow = newRow;
	}
	public Move(Piece p, Square s1, Square s2) {
		this.piece = p;
		this.oldRow = s1.row;
		this.oldCol = s1.col;
		this.newRow = s2.row;
		this.newCol = s2.col;
		this.s1 = s1;
		this.s2 = s2;
	}
	@Override
	public boolean equals(Object arg0) {
		Move m = (Move) arg0;
		return m.piece.pieceEquals(piece)
				&& m.oldRow == oldRow 
				&& m.oldCol == oldCol 
				&& m.newRow == newRow 
				&& m.newCol == newCol;
	}
	@Override
	public int hashCode() {
		return piece.hashCode() + new Square(oldRow, oldCol).hashCode() * 8 + new Square(newRow, newCol).hashCode();
	}
	@Override
	public String toString() {
		return piece.toString() + " -> (" + newRow + ", " + newCol + ")";
	}
}

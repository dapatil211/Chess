import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import acm.graphics.GCompound;
import acm.graphics.GObject;


public class Board {
	Square[][] board = new Square[8][8];
	HashSet<Piece> pieces;
	HashSet<Move> possMoves;
	LinkedList<Move> lastMoves;
	King blackKing;
	King whiteKing;
	GObject image;
	boolean move = false;
	boolean valid = true;
	
	public Board(Square[][] b) {
		board = b;
		pieces = new HashSet<Piece>();
		possMoves = new HashSet<Move>();
		lastMoves = new LinkedList<Move>();
	}
//	public Board() {
//		update();
//	}
	public Board(Board b, Move m){
		for(int i = 0; i < 8; i ++){
			for(int j = 0; j < 8; j++){
				board[i][j] = new Square(b.board[i][j]);
			}
		}
		lastMoves = new LinkedList<Move>(b.lastMoves);
		pieces = new HashSet<Piece>();
		possMoves = new HashSet<Move>();
		Piece newPiece = null;
		for(Piece p : b.pieces){
			Piece p2 = Piece.clonePiece(p);
			pieces.add(p2);
			if(m.piece.col == p2.col && m.piece.row == p2.row){
				newPiece = p2;
			}
		}
		addKings();
		move(newPiece, m.newRow, m.newCol);
		if(inCheck(newPiece.color)){
			valid = false;
		}
//		board[m.oldRow][m.oldCol].update(null, this);
//		board[m.newRow][m.newCol].update(newPiece, this);
//		try{
//		newPiece.update(m.newRow, m.newCol, this);
//		}
//		catch(Exception e){
//			
//		}
	}
	public boolean inCheck(boolean b){
		if(b){
			return blackKing.isAttacked(this);
		}
		else{
			return whiteKing.isAttacked(this);
		}
	}
	public boolean checkMate(boolean b){
		for(Move m : possMoves){
			if(m.piece.color == b){
				return false;
			}
		}
		return true;
	}
	public void update(){
		possMoves.clear();
		for(Piece p : pieces){
			p.updateMoves(this);
		}
		for(Piece p : pieces){
			p.updateMoves(this);
		}
		for(Piece p : pieces){
			for(Move m : p.possMoves){
				possMoves.add(m);
			}
		}
		
//		for(Move m : possMoves){
//			Board b1 = new Board(this, m);
//			if(b1.inCheck(m.piece.color)){
//				this.possMoves.remove(m);
//			}
//		}
//		whiteKing.updateMoves(this);
//		blackKing.updateMoves(this);
		
	}
	public double score(boolean b){
		double score = 0;
		for(Piece p : pieces){
			if(p.color == b && !(p instanceof King)){
				score += p.score;
			}
		}
		return score;
	}
	public double heuristic(boolean b){
		double score = score(b) - score(!b);
		return score;
	}
	public GObject makeImage(Boolean player){
		GCompound c = new GCompound();
		for(int i = 0; i < board.length; i ++){
			for(int j = 0; j < board[0].length; j++){
				c.add(board[i][j].getImage(player));
			}
		}
		image = c;
		return c;
	}
	public void addKings() {
		for(Piece p : pieces){
			if(p instanceof King){
				if(p.color){
					blackKing = (King) p;
				}
				else{
					whiteKing = (King) p;
				}
			}
		}
		
	}
	public void addToLine(Move move) {
		lastMoves.add(move);
		if(lastMoves.size() > 6){
			lastMoves.removeFirst();
		}		
	}
	public GObject getImage() {
		return image;
	}
	public Move findBestMove(boolean b) {
		update();
		ArrayList<Move> playMoves = new ArrayList<Move>();
		for(Move m : possMoves){
			if(m.piece.color == b){
				playMoves.add(m);
			}
		}
		ArrayList<Double> minimaxVals = new ArrayList<Double>();
		for(Move m : playMoves){
			Node n = new Node(m, this, b);
			if(!n.valid){
				continue;
			}
			minimaxVals.add(minimax(n, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 2));
		}
		int ind = 0;
		double max = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < minimaxVals.size(); i++){
			if(minimaxVals.get(i) > max){
				ind = i;
				max = minimaxVals.get(i);
			}
		}
		return playMoves.get(ind);
	}
	private ArrayList<Move> findMoves(boolean b, HashSet<Move> possMoves){
		ArrayList<Move> playMoves = new ArrayList<Move>();
		for(Move m : possMoves){
			if(m.piece.color == b){
				playMoves.add(m);
			}
		}
		return playMoves;
	}
	private double minimax(Node node, boolean curPlayer, double alpha,
			double beta, int depth) {
		if(depth == 0 || node.b.checkMate(true) || node.b.checkMate(false)){
			return node.val;
		}
		if(curPlayer){
			ArrayList<Move> moves = findMoves(!node.player, node.b.possMoves);
			for(Move m : moves){
				Node n = new Node(m, node);
				if(!n.valid){
					continue;
				}
				alpha = Math.max(alpha, minimax(n, !curPlayer, alpha, beta, depth - 1));
				if(alpha >= beta){
					return beta;
				}
			}
			return alpha;
		}
		else{
			ArrayList<Move> moves = findMoves(!node.player, node.b.possMoves);
			for(Move m :moves){
				Node n = new Node(m, node);
				if(!n.valid){
					continue;
				}
				beta = Math.min(beta, minimax(n, !curPlayer, alpha, beta, depth - 1));
				if(alpha >= beta){
					return alpha;
				}
			}
			return beta;
		}
		
	}
	public void move(Piece p, int row, int col) {
		board[p.row][p.col].update(null, this);
		board[row][col].update(p, this);
		addToLine(new Move(p, p.row, p.col, row, col));
		p.update(row, col, this);
		move = !move;
		update();
	}
}

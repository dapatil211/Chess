import java.util.ArrayList;


public class Node{

	ArrayList<Move> line;
	Board b;
	double val;
	boolean player;
	boolean valid = true;
	public Node(Move m, Board board, boolean player) {
		line = new ArrayList<Move>();
		line.add(m);
		this.b = new Board(board, m);
		if(!b.valid){
			valid = false;
		}
		val = b.heuristic(player);
		m.piece.moved = true;
		this.player = player;
	}
	public Node(Move m, Node n) {
		line = new ArrayList<Move>(n.line);
		line.add(m);
		this.b = new Board(n.b, m);
		if(!b.valid){
			valid = false;
		}
		val = b.heuristic(n.player);
		m.piece.moved = true;
	}

}

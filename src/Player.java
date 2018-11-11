import java.util.ArrayList;

public class Player {
	private char team;
	private String username;
	private ArrayList<String> pieces;
	
	public Player (char team) {
		this.team=team;
		this.username="Player "+ String.valueOf(team);
		pieces=new ArrayList<String>();
	}
	public Player (char team, String username) {
		this.team=team;
		this.username=username;
		pieces=new ArrayList<String>();
	}
	public void addToPieces(String piece) {
		pieces.add(piece);
	}
	public void removeFromPieces(String piece) {
		pieces.remove(piece);
	}
	public ArrayList<String> getPieces() {
		return pieces;
	}
	public char getTeam() {
		return this.team;
	}
	public String getUsername() {
		return this.username;
	}
	public String readMove(ArrayList<ArrayList<String>> list) {
		return "";
	}
}

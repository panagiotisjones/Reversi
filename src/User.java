import java.util.ArrayList;
public class User extends Player {

	public User(char team) {
		super(team);
	}
	public User(char team, String username) {
		super(team,username);
	}
	public String userToSystem(String move) {
		StringBuilder systemMove=new StringBuilder();
		char d1,d2;
		d1=move.charAt(0);
		d2=move.charAt(1);
		if (move.length()==2 && Character.isDigit(d1) && Character.isLetter(d2)) {
			systemMove.append(Character.getNumericValue(d1)-1);
			systemMove.append((int) d2-97);
		}
		else if (move.length()==2 && Character.isLetter(d1) && Character.isDigit(d2)) {
			systemMove.append(Character.getNumericValue(d2)-1);
			systemMove.append((int) d1-97);
		}
		else {
			systemMove.append("bad");
		}
		return systemMove.toString();
	}
	@Override
	public String readMove(ArrayList<ArrayList<String>> list) {
		String choice;
		while (true) {
			System.out.println("Please select move in the format [row][column] or reverse, such as 'a1' or '1a'");
			choice=userToSystem(Game.scanner.nextLine());
			for (ArrayList<String> array: list) {
				if (array.get(0).equals(choice)) {
					return choice;
				}
			}
			System.out.println("Invalid move or invalid format. Valid moves are indicated on the grid above");
		}
	}
}

import java.util.ArrayList;

public class Computer extends Player {
	public Computer(char team) {
		super(team, "AI");
	}
	public String easyMove(ArrayList<ArrayList<String>> list) {
		return list.get((int)Math.random()*list.size()).get(0);
	}
	public String readMove(ArrayList<ArrayList<String>> list) {
		switch (Game.DIFFICULTY) {
		case "easy": return easyMove(list);
		default: return easyMove(list);
		}
	}
}

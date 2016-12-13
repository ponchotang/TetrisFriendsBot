package bot;

import game.Tile;

public class AlgorithmTest extends Algorithm{

	@Override
	public String execute() {
		
		Tile tile = findGap(1, 2).get(1);
		
		System.out.println(tile.row() + " " + tile.column());
		
		return "";
	}
	
}

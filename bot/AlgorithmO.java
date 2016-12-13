package bot;

public class AlgorithmO extends Algorithm{

	@Override
	public String execute() {
		// TODO Make use of findGap to decide on basic piece placements
		return determineHorizontalMovement(4, 8);
	}
	


}

package model;

public class IsWithinPalace implements BoardLimit {

	@Override
	public boolean checkBoardLimit(int targetCol, int targetRow) {
		// TODO Auto-generated method stub
		if (targetCol >= 3 && targetCol <= 5 && targetRow >= 7 && targetRow <= 9
				|| targetCol >= 3 && targetCol <= 5 && targetRow >= 0 && targetRow <= 2) {
			return true;
		}
		return false;
	}

}

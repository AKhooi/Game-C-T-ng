package model;

public class IsWithinHalfBoard implements BoardLimit {

	@Override
	public boolean checkBoardLimit(int targetCol, int targetRow) {
		// TODO Auto-generated method stub
		if (targetCol >= 0 && targetCol <= 8 && targetRow >= 0 && targetRow <= 4
				|| targetCol >= 0 && targetCol <= 8 && targetRow >= 5 && targetRow <= 9) {
			return true;
		}
		return false;
	}

}

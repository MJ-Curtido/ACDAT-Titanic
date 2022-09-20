package titanic.enums;

import java.util.List;

public enum TipoZona {
	PROA(List.of(1, 2, 3)), POPA(List.of(4, 5, 6)), BABOR(List.of(7, 8, 9)), ESTRIBOR(List.of(10, 11, 12));
	
	private List<Integer> escaleras;
	
	private TipoZona(List<Integer> escaleras) {
		this.escaleras = escaleras;
	}
	
	public void setEscaleras(List<Integer> escaleras) {
		this.escaleras = escaleras;
	}
	
	public List<Integer> getEscaleras() {
		return this.escaleras;
	}
}
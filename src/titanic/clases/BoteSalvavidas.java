package titanic.clases;

import titanic.enums.TipoZona;

public class BoteSalvavidas {
	private static Integer id = 0;
	private Integer idBote, numPlaza;
	private TipoZona zona;
	
	public BoteSalvavidas(Integer numPlaza, TipoZona zona) {
		this.numPlaza = numPlaza;
		this.zona = zona;
		this.setIdBote(++id);
	}

	public Integer getIdBote() {
		return idBote;
	}

	public void setIdBote(Integer idBote) {
		this.idBote = id;
	}

	public Integer getNumPlaza() {
		return numPlaza;
	}

	public void setNumPlaza(Integer numPlaza) {
		this.numPlaza = numPlaza;
	}

	public TipoZona getZona() {
		return zona;
	}

	public void setZona(TipoZona zona) {
		this.zona = zona;
	}

	@Override
	public String toString() {
		return "id=" + id + ", numero de plazas=" + numPlaza + ", zona=" + zona;
	}
}
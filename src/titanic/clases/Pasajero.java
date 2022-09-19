package titanic.clases;

import java.time.LocalDate;

import titanic.enums.TipoPais;
import titanic.enums.TipoZona;

public class Pasajero extends Persona {
	private int numHab;

	public Pasajero(String dni, String nombre, TipoPais pais, TipoZona zona, Boolean minusvalia, LocalDate fechaNac, int numHab) {
		super(dni, nombre, pais, zona, minusvalia, fechaNac);
		this.numHab = numHab;
	}

	public int getNumHab() {
		return numHab;
	}

	public void setNumHab(int numHab) {
		this.numHab = numHab;
	}

	@Override
	public String toString() {
		return super.toString() + ", numHab=" + numHab;
	}
}
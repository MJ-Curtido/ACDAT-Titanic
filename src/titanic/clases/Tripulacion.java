package titanic.clases;

import java.time.LocalDate;
import java.util.Comparator;

import titanic.enums.TipoPais;
import titanic.enums.TipoResponsabilidad;
import titanic.enums.TipoZona;

public class Tripulacion extends Persona {
	private TipoResponsabilidad responsabilidad;

	public Tripulacion(String dni, String nombre, TipoPais pais, TipoZona zona, Boolean minusvalia,
			LocalDate fechaNac, TipoResponsabilidad responsabilidad) {
		super(dni, nombre, pais, zona, minusvalia, fechaNac);
		this.responsabilidad = responsabilidad;
	}

	public TipoResponsabilidad getResponsabilidad() {
		return responsabilidad;
	}

	public void setResponsabilidad(TipoResponsabilidad responsabilidad) {
		this.responsabilidad = responsabilidad;
	}

	@Override
	public String toString() {
		return super.toString() + ", responsabilidad=" + responsabilidad;
	}
	
	class ComparadorResponsabilidad implements Comparator<Tripulacion> {
	    @Override
	    public int compare(Tripulacion trip1, Tripulacion trip2) {
	        return trip1.getResponsabilidad().compareTo(trip2.getResponsabilidad());
	    }
	}
}
package titanic.clases;

import java.time.LocalDate;
import java.util.Objects;

import titanic.enums.TipoPais;
import titanic.enums.TipoZona;

public abstract class Persona {
	private String dni, nombre;
	private TipoPais pais;
	private TipoZona zona;
	private Boolean minusvalia;
	private LocalDate fechaNac;

	public Persona(String dni, String nombre, TipoPais pais, TipoZona zona, Boolean minusvalia, LocalDate fechaNac) {
		this.dni = dni;
		this.nombre = nombre;
		this.pais = pais;
		this.zona = zona;
		this.minusvalia = minusvalia;
		this.fechaNac = fechaNac;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoPais getPais() {
		return pais;
	}

	public void setPais(TipoPais pais) {
		this.pais = pais;
	}

	public TipoZona getZona() {
		return zona;
	}

	public void setZona(TipoZona zona) {
		this.zona = zona;
	}

	public Boolean getMinusvalia() {
		return minusvalia;
	}

	public void setMinusvalia(Boolean minusvalia) {
		this.minusvalia = minusvalia;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}
	
	public Boolean esMayorEdad() {
		Boolean mayor = true;
		
		if ((LocalDate.now().getYear() - this.getFechaNac().getYear()) < 18) {
			mayor = false;
			
			if ((LocalDate.now().getMonthValue() - this.getFechaNac().getMonthValue()) <= 0 && (LocalDate.now().getYear() - this.getFechaNac().getYear()) == 17) {
				if ((LocalDate.now().getDayOfMonth() - this.getFechaNac().getDayOfMonth()) <= 0 && (LocalDate.now().getMonthValue() - this.getFechaNac().getMonthValue()) == 0) {
					mayor = true;
				}
			}
		}
		
		return mayor;
	}
	
	public Boolean esAnciano() {
		Boolean anciano = true;
		
		if ((LocalDate.now().getYear() - this.getFechaNac().getYear()) < 65) {
			anciano = false;
			
			if ((LocalDate.now().getMonthValue() - this.getFechaNac().getMonthValue()) <= 0 && (LocalDate.now().getYear() - this.getFechaNac().getYear()) == 64) {
				if ((LocalDate.now().getDayOfMonth() - this.getFechaNac().getDayOfMonth()) <= 0 && (LocalDate.now().getMonthValue() - this.getFechaNac().getMonthValue()) == 0) {
					anciano = true;
				}
			}
		}
		
		return anciano;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni, pais);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(dni, other.dni) && pais == other.pais;
	}

	@Override
	public String toString() {
		return "Persona: dni=" + dni + ", nombre=" + nombre + ", pais=" + pais + ", zona=" + zona + ", minusvalia="
				+ minusvalia + ", fechaNac=" + fechaNac;
	}
}
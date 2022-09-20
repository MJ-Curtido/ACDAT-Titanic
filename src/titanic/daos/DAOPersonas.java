package titanic.daos;

import java.time.LocalDate;
import java.util.ArrayList;

import titanic.clases.Pasajero;
import titanic.clases.Persona;
import titanic.clases.Tripulacion;
import titanic.enums.TipoPais;
import titanic.enums.TipoResponsabilidad;
import titanic.enums.TipoZona;

public class DAOPersonas {
	private static DAOPersonas dao = null;
	private static ArrayList<Persona> lista = null;
	
	private DAOPersonas() {
		
	}
	
	public static DAOPersonas getinstance() {
		if (dao == null) {
			dao = new DAOPersonas();
		}
		
		if (lista == null) {
			lista = new ArrayList<Persona>();
		}
		
		return dao;
	}

	public ArrayList<Persona> getPersonas() {
		if (lista.isEmpty()) {
			Pasajero pers1 = new Pasajero("18181818P", "Manuel", TipoPais.IRLANDA, TipoZona.BABOR, false, LocalDate.of(2007, 7, 18), 7);
			lista.add(pers1);
			Pasajero pers2 = new Pasajero("16161616M", "Paula", TipoPais.ESPAÑA, TipoZona.ESTRIBOR, false, LocalDate.of(2002, 2, 16), 17);
			lista.add(pers2);
			Pasajero pers3 = new Pasajero("77777777C", "Nathan", TipoPais.JAPON, TipoZona.POPA, true, LocalDate.of(1997, 10, 7), 1);
			lista.add(pers3);
			Pasajero pers4 = new Pasajero("11111111G", "Elli", TipoPais.LUXEMBURGO, TipoZona.PROA, true, LocalDate.of(1970, 1, 1), 170);
			lista.add(pers4);
			
			Tripulacion pers5 = new Tripulacion("12345678Q", "Pablo Miguel", TipoPais.LUXEMBURGO, TipoZona.POPA, true, LocalDate.of(2003, 3, 20), TipoResponsabilidad.MUY_ALTA);
			lista.add(pers5);
			Tripulacion pers6 = new Tripulacion("98765432E", "Paco", TipoPais.IRLANDA, TipoZona.ESTRIBOR, true, LocalDate.of(2022, 12, 30), TipoResponsabilidad.MEDIA);
			lista.add(pers6);
			Tripulacion pers7 = new Tripulacion("65874321H", "Truco", TipoPais.JAPON, TipoZona.BABOR, false, LocalDate.of(1100, 3, 25), TipoResponsabilidad.BAJA);
			lista.add(pers7);
			Tripulacion pers8 = new Tripulacion("91287347J", "Trato", TipoPais.ALEMANIA, TipoZona.PROA, false, LocalDate.of(1777, 9, 29), TipoResponsabilidad.MUY_BAJA);
			lista.add(pers8);
		}
		
		return lista;
	}
}
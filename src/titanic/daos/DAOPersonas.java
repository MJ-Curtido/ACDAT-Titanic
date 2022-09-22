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
	
	public static DAOPersonas getInstance() {
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
			Pasajero pers1 = new Pasajero("18181818P", "Manuel", TipoPais.IRLANDA, TipoZona.ESTRIBOR, false, LocalDate.of(1997, 7, 18), 7);
			lista.add(pers1);
			Pasajero pers2 = new Pasajero("16161616M", "Paula", TipoPais.ESPAÑA, TipoZona.ESTRIBOR, false, LocalDate.of(2002, 2, 16), 7);
			lista.add(pers2);
			Pasajero pers3 = new Pasajero("77777777C", "Nathan", TipoPais.JAPON, TipoZona.ESTRIBOR, true, LocalDate.of(2010, 10, 7), 7);
			lista.add(pers3);
			Pasajero pers4 = new Pasajero("11111111G", "Elli", TipoPais.LUXEMBURGO, TipoZona.ESTRIBOR, true, LocalDate.of(2007, 1, 1), 7);
			lista.add(pers4);
			Pasajero pers5 = new Pasajero("00000001M", "Persona1", TipoPais.IRLANDA, TipoZona.BABOR, false, LocalDate.of(1850, 7, 18), 17);
			lista.add(pers5);
			Pasajero pers6 = new Pasajero("00000002M", "Persona2", TipoPais.ESPAÑA, TipoZona.PROA, false, LocalDate.of(2000, 2, 16), 70);
			lista.add(pers6);
			Pasajero pers7 = new Pasajero("00000003M", "Persona3", TipoPais.JAPON, TipoZona.POPA, true, LocalDate.of(1970, 10, 7), 10);
			lista.add(pers7);
			Pasajero pers8 = new Pasajero("00000004M", "Persona4", TipoPais.LUXEMBURGO, TipoZona.PROA, true, LocalDate.of(2009, 1, 1), 10);
			lista.add(pers8);
			
			Tripulacion pers9 = new Tripulacion("12345678Q", "Pablo Miguel", TipoPais.LUXEMBURGO, TipoZona.POPA, true, LocalDate.of(2003, 3, 20), TipoResponsabilidad.MUY_ALTA);
			lista.add(pers9);
			Tripulacion pers10 = new Tripulacion("98765432E", "Paco", TipoPais.IRLANDA, TipoZona.ESTRIBOR, true, LocalDate.of(1970, 12, 30), TipoResponsabilidad.MEDIA);
			lista.add(pers10);
			Tripulacion pers11 = new Tripulacion("65874321H", "Truco", TipoPais.JAPON, TipoZona.BABOR, false, LocalDate.of(1960, 3, 25), TipoResponsabilidad.BAJA);
			lista.add(pers11);
			Tripulacion pers12 = new Tripulacion("91287347J", "Trato", TipoPais.ALEMANIA, TipoZona.PROA, false, LocalDate.of(1987, 9, 29), TipoResponsabilidad.MUY_BAJA);
			lista.add(pers12);
			Tripulacion pers13 = new Tripulacion("00000005M", "Persona13", TipoPais.IRLANDA, TipoZona.BABOR, false, LocalDate.of(1850, 7, 18), TipoResponsabilidad.ALTA);
			lista.add(pers13);
			Tripulacion pers14 = new Tripulacion("00000006M", "Persona14", TipoPais.ESPAÑA, TipoZona.ESTRIBOR, false, LocalDate.of(2000, 2, 16), TipoResponsabilidad.MEDIA);
			lista.add(pers14);
			Tripulacion pers15 = new Tripulacion("00000007M", "Persona15", TipoPais.JAPON, TipoZona.POPA, true, LocalDate.of(1970, 10, 7), TipoResponsabilidad.MUY_BAJA);
			lista.add(pers15);
			Tripulacion pers16 = new Tripulacion("00000008M", "Persona16", TipoPais.LUXEMBURGO, TipoZona.PROA, true, LocalDate.of(2009, 1, 1), TipoResponsabilidad.MUY_ALTA);
			lista.add(pers16);
		}
		
		return lista;
	}
}
package controlador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;

import conexion.Centros;
import modelo.Ciclos;
import modelo.HibernateUtil;

public class Metodos {
	private static SessionFactory sesion = HibernateUtil.getSessionFactory();
	private static Session session = sesion.openSession();
	final String url = "src/centros.json";
    private List<Centros> centros;

	public void conectarJSON() {
		JsonParser parser = new JsonParser();
		try {
			FileReader fr = new FileReader(url);
			JsonElement datos = parser.parse(fr);
			
			parser(datos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	private static void parser(JsonElement datos) {
		if (datos.isJsonArray()) {

			JsonArray array = datos.getAsJsonArray();
			Iterator<JsonElement> iter = array.iterator();

			while (iter.hasNext()) {
				JsonElement entrada = iter.next();
				parser(entrada);
			}

		} else if (datos.isJsonObject()) {

			System.out.println("Objeto");
			JsonObject objeto = datos.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
			while (iter2.hasNext()) {
				Map.Entry<String, JsonElement> ent = iter2.next();
				JsonElement valor = ent.getValue();
				System.out.println("Atributo: " + ent.getKey());
				parser(valor);
			}

		} else if (datos.isJsonPrimitive()) {

			JsonPrimitive primi = datos.getAsJsonPrimitive();

			if (primi.isString()) {
				System.out.println("\tTexto: " + primi.getAsString());
			} else if (primi.isNumber()) {
				System.out.println("\tNumero: " + primi.getAsNumber());
			} else if (primi.isBoolean()) {
				System.out.println("\tTexto: " + primi.getAsBoolean());
			}

		} else if (datos.isJsonNull()) {
			System.out.println("Es nulo");
		}

	}


	public void guardarCiclo(int id, String nombre) {
		Transaction tx = null;
		tx =session.beginTransaction();
		Ciclos newCiclo = new Ciclos();
		newCiclo.setId(id);
		newCiclo.setNombre(nombre);
		session.save(newCiclo);
		tx.commit();
		
	}

	/*public void pruebaSentenciaHQL() {
		String hql ="FROM Ciclos";
        Query q = session.createQuery(hql);
        List<Ciclos> listaCiclos = q.list();

        
        for(int i =0;i<listaCiclos.size();i++) {
        	Ciclos ciclo =listaCiclos.get(i);
        	System.out.println(ciclo.getNombre());
        }
		
	}*/

}

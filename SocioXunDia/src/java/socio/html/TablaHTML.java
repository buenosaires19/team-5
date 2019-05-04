package socio.html;

import java.lang.reflect.Field;
import java.util.List;

public class TablaHTML<E> {

	public TablaHTML() {
	}

	public String getTabla(List<E> lista) {
		if (lista == null || lista.isEmpty()) {
			return "";
		}
		String tabla = "";
		E e = lista.get(0);
		tabla += "<table border=1>";
		tabla += "<tr>";
		Field[] campos = e.getClass().getDeclaredFields();
		for (Field f : campos) {
			tabla += "<th>" + f.getName() + "</th>";
		}
		tabla += "</tr>";
		for (E x : lista) {
			tabla += "<tr>";
			for (Field f : campos) {
				tabla += "<td>";
				String method = "get"
						+ f.getName().substring(0, 1).toUpperCase()
						+ f.getName().substring(1);
				try {
					tabla += e.getClass().getDeclaredMethod(method, null).invoke(x, null) + "";
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				tabla += "</td>";
			}
			tabla += "</tr>";
		}
		tabla += "</table>";
		return tabla;
	}
}

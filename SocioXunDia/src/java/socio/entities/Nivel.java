package socio.entities;

public class Nivel {
	private int id;
	private String nivel_desc;

	public Nivel() { }

	public String getTable() { return "nivel"; }
	
	public Nivel(String nivel_desc) {
		this.nivel_desc = nivel_desc;
	}

	public Nivel(int id, String nivel_desc) {
		this.id = id;
		this.nivel_desc = nivel_desc;
	}

	@Override
	public String toString() {
		return "Nivel{" + "id=" + id + ", nivel_desc=" + nivel_desc + '}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNivel_desc() {
		return nivel_desc;
	}

	public void setNivel_desc(String nivel_desc) {
		this.nivel_desc = nivel_desc;
	}
}

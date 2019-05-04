package socio.entities;

public class Mentor {
	private int id;
	private String nombrementor;
	private String apellidomentor;
	private String mailmentor;
	private int regionid;
	private int profesionid;

	public String getTable() { return "mentor"; }
	
	public Mentor() {	}

	public Mentor(String nombrementor, String apellidomentor, String mailmentor, int regionid, int profesionid) {
		this.nombrementor = nombrementor;
		this.apellidomentor = apellidomentor;
		this.mailmentor = mailmentor;
		this.regionid = regionid;
		this.profesionid = profesionid;
	}

	public Mentor(int id, String nombrementor, String apellidomentor, String mailmentor, int regionid, int profesionid) {
		this.id = id;
		this.nombrementor = nombrementor;
		this.apellidomentor = apellidomentor;
		this.mailmentor = mailmentor;
		this.regionid = regionid;
		this.profesionid = profesionid;
	}

	@Override
	public String toString() {
		return "Mentor{" + "id=" + id + ", nombrementor=" + nombrementor + ", apellidomentor=" + apellidomentor + ", mailmentor=" + mailmentor + ", regionid=" + regionid + ", profesionid=" + profesionid + '}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombrementor() {
		return nombrementor;
	}

	public void setNombrementor(String nombrementor) {
		this.nombrementor = nombrementor;
	}

	public String getApellidomentor() {
		return apellidomentor;
	}

	public void setApellidomentor(String apellidomentor) {
		this.apellidomentor = apellidomentor;
	}

	public String getMailmentor() {
		return mailmentor;
	}

	public void setMailmentor(String mailmentor) {
		this.mailmentor = mailmentor;
	}

	public int getRegionid() {
		return regionid;
	}

	public void setRegionid(int regionid) {
		this.regionid = regionid;
	}

	public int getProfesionid() {
		return profesionid;
	}

	public void setProfesionid(int profesionid) {
		this.profesionid = profesionid;
	}
}
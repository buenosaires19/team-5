package socio.entities;

public class Usuario {
    // DECLARACIÓN DE 7 ATRIBUTOS
    private int id;
    private String nombreusuario;
    private String apellidousuario;
    private String mailusuario;
    private int regionid;
    private int nivelid;
    private int videoid;

    
    public Usuario() {
    } // CONSTRUCTOR "1" VACÍO

	public String getTable() { return "usuario"; }

	public Usuario(String nombreusuario, String apellidousuario, String mailusuario, int regionid, int nivelid, int videoid) {
		this.nombreusuario = nombreusuario;
		this.apellidousuario = apellidousuario;
		this.mailusuario = mailusuario;
		this.regionid = regionid;
		this.nivelid = nivelid;
		this.videoid = videoid;
	}

	public Usuario(int id, String nombreusuario, String apellidousuario, String mailusuario, int regionid, int nivelid, int videoid) {
		this.id = id;
		this.nombreusuario = nombreusuario;
		this.apellidousuario = apellidousuario;
		this.mailusuario = mailusuario;
		this.regionid = regionid;
		this.nivelid = nivelid;
		this.videoid = videoid;
	}

	@Override
	public String toString() {
		return "Usuario{" + "id=" + id + ", nombreusuario=" + nombreusuario + ", apellidousuario=" + apellidousuario + ", mailusuario=" + mailusuario + ", regionid=" + regionid + ", nivelid=" + nivelid + ", videoid=" + videoid + '}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreusuario() {
		return nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getApellidousuario() {
		return apellidousuario;
	}

	public void setApellidousuario(String apellidousuario) {
		this.apellidousuario = apellidousuario;
	}

	public String getMailusuario() {
		return mailusuario;
	}

	public void setMailusuario(String mailusuario) {
		this.mailusuario = mailusuario;
	}

	public int getRegionid() {
		return regionid;
	}

	public void setRegionid(int regionid) {
		this.regionid = regionid;
	}

	public int getNivelid() {
		return nivelid;
	}

	public void setNivelid(int nivelid) {
		this.nivelid = nivelid;
	}

	public int getVideoid() {
		return videoid;
	}

	public void setVideoid(int videoid) {
		this.videoid = videoid;
	}
    
	
} // FIN DE LA CLASE

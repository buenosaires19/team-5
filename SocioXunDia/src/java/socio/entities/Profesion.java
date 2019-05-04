package socio.entities;

public class Profesion {
   private int id;
   private String prof_desc;
   private int video_id;

    public Profesion() {  }
	
	public String getTable() { return "profesion"; }

    public Profesion(String prof_desc, int video_id) {
        this.prof_desc = prof_desc;
        this.video_id = video_id;
    }
    
    public Profesion(int id, String prof_desc, int video_id) {
        this.id = id;
        this.prof_desc = prof_desc;
        this.video_id = video_id;
    }

    @Override
    public String toString() {
        return "Profesion{" + "id=" + id + ", prof_desc=" + prof_desc + ", video_id=" + video_id + '}';
    }
   
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProf_desc() {
        return prof_desc;
    }

    public void setProf_desc(String prof_desc) {
        this.prof_desc = prof_desc;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }
   
   
}

package socio.entities;

public class Videos {
	private int id;
	private String videos_desc;

	public Videos() { }
	
	public String getTable() { return "videos"; }

	public Videos(int id, String videos_desc) {
		this.id = id;
		this.videos_desc = videos_desc;
	}

	public Videos(String videos_desc) {
		this.videos_desc = videos_desc;
	}

	@Override
	public String toString() {
		return "Videos{" + "id=" + id + ", videos_desc=" + videos_desc + '}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVideos_desc() {
		return videos_desc;
	}

	public void setVideos_desc(String videos_desc) {
		this.videos_desc = videos_desc;
	}
}

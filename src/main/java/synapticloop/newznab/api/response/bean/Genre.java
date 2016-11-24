package synapticloop.newznab.api.response.bean;

public class Genre {
	private final Integer id;
	private final Integer categoryId;
	private final String name;

	public Genre(Integer id, Integer categoryId, String name) {
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
	}

	public Integer getId() { return this.id; }

	public Integer getCategoryId() { return this.categoryId; }

	public String getName() { return this.name; }

}

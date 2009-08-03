package de.tud.cs.qpe.editors.query.model;

/**
 * Represents a token color of a queueing petri net.
 * 
 * @author fzipp
 *
 */
public class Color implements Comparable<Color> {
	
	private final String name;
	private final String id;
	private final String realColor;

	/**
	 * Creates a new color.
	 * 
	 * @param name			the name of the color
	 * @param id			a unique ID
	 * @param realColor		the real color in "#rrggbb" format
	 */
	public Color(String name, String id, String realColor) {
		this.name = name;
		this.id = id;
		this.realColor = realColor;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getId() {
		return this.id;
	}

	public String getRealColor() {
		return this.realColor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int compareTo(Color o) {
		return this.name.compareTo(o.getName());
	}
}

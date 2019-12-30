
package org.pixer.pixer.rest;

import javax.validation.constraints.Pattern;

/**
 *
 * @author ador
 */
public class FillMsg {

	@Pattern(regexp = "^#[a-f0-9]{6}")
	private String color;
	private int x;
	private int y;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 17 * hash + this.x;
		hash = 17 * hash + this.y;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final FillMsg other = (FillMsg) obj;
		if (this.x != other.x) {
			return false;
		}
		if (this.y != other.y) {
			return false;
		}
		return true;
	}
	
}

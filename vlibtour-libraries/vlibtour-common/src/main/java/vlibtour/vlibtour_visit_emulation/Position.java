/**
This file is part of the course CSC5002.

Copyright (C) 2017-2019 Télécom SudParis

This is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This software platform is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with the muDEBS platform. If not, see <http://www.gnu.org/licenses/>.

Initial developer(s): Denis Conan
Contributor(s):
 */
package vlibtour.vlibtour_visit_emulation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The class represents a position.
 * <p>
 * Position objects can be serialised using Glassfish Jersey, thus the
 * annotations.
 * <p>
 * Position objects can be serialised using Google Gson, thus the
 * {@code GsonBuilder}.
 * 
 * @author Denis Conan
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "gpsPosition", "description" })
public class Position {
	/**
	 * the name of the position.
	 */
	@JsonProperty("name")
	private String name;
	/**
	 * the GPS position.
	 */
	@JsonProperty("gpsPosition")
	private GPSPosition gpsPosition;
	/**
	 * the description.
	 */
	@JsonProperty("description")
	private String description;
	/**
	 * Gson object to de-serialise. See the JUnit test class {@code TestGPSPosition}
	 * for discovering how easy it is to serialize and deserialize.
	 */
	public static final Gson GSON = new GsonBuilder().create();

	/**
	 * No arguments constructor for use in serialization.
	 */
	public Position() {
	}

	/**
	 * constructs a position that has no POI.
	 * 
	 * @param name        the name of the position.
	 * @param gpsPosition the GPS location.
	 * @param description the description, for instance the address.
	 */
	public Position(final String name, final GPSPosition gpsPosition, final String description) {
		if (name == null || name.equals("")) {
			throw new IllegalArgumentException("name of position is null or empty");
		}
		this.name = name;
		this.gpsPosition = gpsPosition;
		this.description = description;
	}

	/**
	 * constructs a position that has no description and no POI.
	 * 
	 * @param name        the name of the position.
	 * @param gpsPosition the GPS location.
	 */
	public Position(final String name, final GPSPosition gpsPosition) {
		this(name, gpsPosition, null);
	}

	/**
	 * gets the name of the position.
	 * 
	 * @return the name.
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the position.
	 * 
	 * @param name the new name.
	 */
	@JsonProperty("name")
	public void setName(final String name) {
		if (name == null || name.equals("")) {
			throw new IllegalArgumentException("name cannot be null or empty");
		}
		this.name = name;
	}

	/**
	 * gets the GPS position of the position.
	 * 
	 * @return the GPS position.
	 */
	@JsonProperty("gpsPosition")
	public GPSPosition getGpsPosition() {
		return gpsPosition;
	}

	/**
	 * sets the GPS position of position.
	 */
	@JsonProperty("gpsPosition")
	public void setGPSPosition(final GPSPosition gpsPosition) {
		if (gpsPosition == null) {
			throw new IllegalArgumentException("gpsPosition cannot be null");
		}
		this.gpsPosition = gpsPosition;
	}

	/**
	 * gets the description of the position.
	 * 
	 * @return the description.
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * sets the description of the position.
	 */
	@JsonProperty("description")
	public void setDescription(final String description) {
		if (description == null || description.equals("")) {
			throw new IllegalArgumentException("description cannot be null or empty");
		}
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Position other = (Position) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Position [name=" + name + ", location is " + gpsPosition + ", description=" + description + "]";
	}
	public String long_lal() {
		return gpsPosition.getLatitude() +"_"+gpsPosition.getLatitude() ;
	}
}

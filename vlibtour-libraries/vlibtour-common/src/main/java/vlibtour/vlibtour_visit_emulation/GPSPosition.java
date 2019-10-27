/**
This file is part of the course CSC5002.

Copyright (C) 2017_2019 Télécom SudParis

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
Contributor(s): Chantal Taconet
 */
package vlibtour.vlibtour_visit_emulation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.grum.geocalc.DegreeCoordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;


/**
 * The class represents a GPS position. It uses the class
 * {@link com.grum.geocalc.Point} by delegation for providing utility
 * methods.
 * <p>
 * GPSPosition objects can be serialised using Glassfish Jersey, thus the
 * annotations.
 * <p>
 * GPSPosition objects can be serialised using Google Gson, thus the
 * {@code GsonBuilder} in the class {@code Position}.
 * 
 * @author Denis Conan
 * @author Chantal Taconet
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "latitude",
    "longitude"
})
public class GPSPosition {
	/**
	 * the latitude.
	 */
    @JsonProperty("latitude")
	private double latitude;
	/**
	 * the longitude.
	 */
    @JsonProperty("longitude")
	private double longitude;

	/**
	 * No arguments constructor for use in serialization.
	 */
	public GPSPosition() {
	}

	/**
	 * constructs a GPS position.
	 * 
	 * @param latitude
	 *            the latitude.
	 * @param longitude
	 *            the longitude.
	 */
	public GPSPosition(final double latitude, final double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * computes the distance from this location to a target location in meters (crow
	 * flies).
	 * 
	 * @param target
	 *            the target location to calculate the distance from.
	 * @return the distance in meter.
	 */

	public double distanceFrom(final GPSPosition target) {
		return EarthCalc.getDistance(new Point(new DegreeCoordinate(latitude),
				new DegreeCoordinate(longitude)), new Point(new DegreeCoordinate(target.getLatitude()),
				new DegreeCoordinate(target.getLongitude())));
	}

	/**
	 * gets the latitude.
	 * 
	 * @return the latitude.
	 */
    @JsonProperty("latitude")
	public double getLatitude() {
		return latitude;
	}

	/**
	 * sets the latitude.
	 * 
	 * @param latitude the latitude.
	 */
    @JsonProperty("latitude")
	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	/**
	 * gets the longitude.
	 * 
	 * @return the longitude.
	 */
    @JsonProperty("longitude")
	public double getLongitude() {
		return longitude;
	}

	/**
	 * sets the longitude.
	 * 
	 * @param longitude the longitude.
	 */
    @JsonProperty("longitude")
	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof GPSPosition)) {
			return false;
		}
		GPSPosition other = (GPSPosition) obj;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude)) {
			return false;
		}
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "longitude=" + longitude + " and latitude=" + latitude;
	}
}

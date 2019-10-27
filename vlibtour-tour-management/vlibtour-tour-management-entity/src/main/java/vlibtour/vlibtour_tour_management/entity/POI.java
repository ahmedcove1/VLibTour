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
package vlibtour.vlibtour_tour_management.entity;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;




/**
 * The entity bean defining a point of interest (POI). A {@link Tour} is a
 * sequence of points of interest.
 * 
 * For the sake of simplicity, we suggest that you use named queries.
 * 
 * @author Denis Conan
 */
@Entity
public class POI implements Serializable {
	/**
	 * the serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	public String name;
	public String description;
	public double lon;
	public double lat;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int poid;
    @ManyToMany(mappedBy="pois")
	private Collection<Tour> tours ;
	
	public POI() {
		
	}

	
	public POI(String name, String description,double lat,double lon) throws VlibTourTourManagementException{
			if (name == null || name.isEmpty()) {
				throw new VlibTourTourManagementException("The name of the POI is null or Empty");
			}	
			this.name = name;
			if (description == null || description.isEmpty()) {
				throw new VlibTourTourManagementException("The description of the POI is null or Empty");
			}
			this.lon=lon;
			this.lat=lat;
			this.description = description;
		//	poid = id;  
			assert invariant();
	}
	


	public boolean invariant() {
			return name != null && !name.isEmpty() && description != null && !description.isEmpty();
	}

	public int getPoid() {
		return poid;
	}


	public void setPoid(int poid) {
		this.poid = poid;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public Collection<Tour> getTour() {
		return this.tours;
	}

	public void setTour(final Collection<Tour> tours) {
		this.tours = tours;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		long temp;
		temp = Double.doubleToLongBits(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + poid;
		result = prime * result + ((tours == null) ? 0 : tours.hashCode());
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
		POI other = (POI) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
			return false;
		if (Double.doubleToLongBits(lon) != Double.doubleToLongBits(other.lon))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (poid != other.poid)
			return false;

		return true;
	}


	@Override
	public String toString() {
		return "POI [name=" + name + ", description=" + description + ", lon=" + lon + ", lat=" + lat + ", poid=" + poid
				+ ", tours=" + tours + "]";
	}

	

	
	
	
}

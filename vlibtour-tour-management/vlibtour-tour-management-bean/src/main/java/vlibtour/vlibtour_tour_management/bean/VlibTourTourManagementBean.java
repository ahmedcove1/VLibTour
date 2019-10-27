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
package vlibtour.vlibtour_tour_management.bean;


import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.ejb.Stateless;

import vlibtour.vlibtour_tour_management.api.VlibTourTourManagement;
import vlibtour.vlibtour_tour_management.entity.POI;
import vlibtour.vlibtour_tour_management.entity.Tour;
import vlibtour.vlibtour_tour_management.entity.VlibTourTourManagementException;

import javax.persistence.*;

/**
 * This class defines the EJB Bean of the VLibTour tour management.
 * 
 * @author Denis Conan
 */
@Stateless
public class VlibTourTourManagementBean implements VlibTourTourManagement {

	/**
	 * the reference to the entity manager, which persistence context is "pu1".
	 */
	@PersistenceContext(unitName = "pu1")
	private EntityManager em;

	@Override
	public POI CreateAPOI(String name,String description,double lon,double lat) throws VlibTourTourManagementException {
		// Create a new POI
		POI poi;
		poi = new POI(name,description,lat,lon);
		// Persist the POI
		em.persist(poi);
		return poi;

		
	}

	@Override
	public Tour CreateATour(String nom, String description) throws VlibTourTourManagementException {
		Tour tour;
		tour = new Tour(description,nom);
		em.persist(tour);
		return tour;
	}

	@Override
	public String addPOItoATour(String pid,String tid) throws VlibTourTourManagementException {
		Tour tour=em.find(Tour.class,tid);
		POI poi=em.find(POI.class, pid);
		if(tour == null) {
			throw new VlibTourTourManagementException("There is no tour with this ID " + tid);
		}
		if(poi == null) {
			throw new VlibTourTourManagementException("There is no POI with this ID " + tid);
		}
		tour.getPois().add(poi);
		poi.getTour().add(tour);
		return "OK";
	}

	@Override
	public String moveAPOIInTheSequenceOfATour(String pid,String tid ,int index) throws VlibTourTourManagementException {
		Tour tour=em.find(Tour.class,tid);
		POI poi=em.find(POI.class, pid);
		if(tour == null) {
			throw new VlibTourTourManagementException("There is no tour with this ID " + tid);
		}
		if(poi == null) {
			throw new VlibTourTourManagementException("There is no POI with this ID " + pid);
		}
		Vector<POI> pois = tour.getPois();
		if(!pois.contains(poi)) {
			throw new VlibTourTourManagementException("POI n'existe pas dans la liste des POIs");
		}
		pois.remove(poi);
		pois.add(index, poi);
		return "OK";
	}

	@Override
	public String removeAPOIFromAtour(POI poi,Tour tour) throws VlibTourTourManagementException {
		Vector<POI> pois = tour.getPois();
		if(!pois.contains(poi)) {
			throw new VlibTourTourManagementException("POI n'existe pas dans la liste des POIs");
		}
		pois.remove(poi);
		return "OK";
	}

	@Override
	public String modifytheDescriptionOfAPOI(POI poi,String desc) {
		poi.setDescription(desc);
		return null;
	}

	@Override
	public String removeAPOI(String pid) throws VlibTourTourManagementException {
		POI poi=em.find(POI.class, pid);
		if(poi == null) {
			throw new VlibTourTourManagementException("There is no POI with this ID " + pid);
		}
		Collection<Tour> tour=poi.getTour();
		for(Tour t:tour) {
			this.removeAPOIFromAtour(poi, t);
		}
		return "OK";
	}

	@Override
	public List<Tour> listTours() {
		Query q = em.createQuery("select t from Tour t");
		return q.getResultList();
	}

	@Override
	public List<POI> listPOIs() {
		Query q = em.createQuery("select p from POI p");
		return q.getResultList();
	}
	@Override
	public POI findPOIWithPID(int id) throws VlibTourTourManagementException{
		POI poi=em.find(POI.class, id);
		if(poi == null) {
			throw new VlibTourTourManagementException("There is no POI with this ID " + id);
		}
		return poi;
		}
	@Override
	public Tour findTourByTid(int tid) throws VlibTourTourManagementException {
		Tour tour=em.find(Tour.class, tid);
		if(tour == null) {
			throw new VlibTourTourManagementException("There is no POI with this ID " + tid);
		}
		return tour;
		}

	@Override
	public void removeTour(int id) throws VlibTourTourManagementException {
		Tour t=findTourByTid(id);
		em.remove(t);
	}

	@Override
	public void removePOI(int id) throws VlibTourTourManagementException {
		POI poi=findPOIWithPID(id);
		em.remove(poi);
	}

	@Override
	public List<POI> findPOISByName(String name) throws VlibTourTourManagementException {
		if(name =="" || name.isEmpty()) {
			throw new VlibTourTourManagementException("no such POI Name");
		}
		Query q = em.createQuery("select p from POI p where p.name = :name");
		q.setParameter("name", name);
		return q.getResultList();
	}
	@Override
	public List<Tour> findTOursByName(String name) throws VlibTourTourManagementException {
		if(name =="" || name.isEmpty()) {
			throw new VlibTourTourManagementException("no such Tour Name");
		}
		Query q = em.createQuery("select t from Tour t where t.name = :name");
		q.setParameter("name", name);
		return q.getResultList();
	}

	


}

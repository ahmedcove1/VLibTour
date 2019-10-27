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
package vlibtour.vlibtour_tour_management.api;

import java.util.List;

import javax.ejb.Remote;

import vlibtour.vlibtour_tour_management.entity.POI;
import vlibtour.vlibtour_tour_management.entity.Tour;
import vlibtour.vlibtour_tour_management.entity.VlibTourTourManagementException;

/**
 * This interface defines the operation for managing POIs and Tours.
 * 
 * @author Denis Conan
 */
@Remote
public interface VlibTourTourManagement {

	public Tour CreateATour(String nom, String description) throws VlibTourTourManagementException;
	public String addPOItoATour(String pid,String tid) throws VlibTourTourManagementException;
	public String moveAPOIInTheSequenceOfATour(String pid, String tid, int index) throws VlibTourTourManagementException;
	public String removeAPOIFromAtour(POI poi, Tour tour) throws VlibTourTourManagementException;
	public String modifytheDescriptionOfAPOI(POI poi, String desc);
	public String removeAPOI(String pid) throws VlibTourTourManagementException;
	public List<Tour> listTours();
	public List<POI> listPOIs();
	public POI CreateAPOI(String name, String description, double lon, double lat) throws VlibTourTourManagementException;
	public Tour findTourByTid(int id)throws VlibTourTourManagementException;
	public POI findPOIWithPID(int id) throws VlibTourTourManagementException;
	public void removeTour(int id) throws VlibTourTourManagementException;
	public void removePOI(int id)throws VlibTourTourManagementException;
	public List<POI> findPOISByName(String name) throws VlibTourTourManagementException;
	public List<Tour> findTOursByName(String name) throws VlibTourTourManagementException;
}

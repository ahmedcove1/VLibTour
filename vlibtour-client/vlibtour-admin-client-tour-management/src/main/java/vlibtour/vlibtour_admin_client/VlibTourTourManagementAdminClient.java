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
package vlibtour.vlibtour_admin_client;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import javax.naming.InitialContext;

import vlibtour.vlibtour_tour_management.api.VlibTourTourManagement;
import vlibtour.vlibtour_tour_management.entity.POI;
import vlibtour.vlibtour_tour_management.entity.Tour;
import vlibtour.vlibtour_tour_management.entity.VlibTourTourManagementException;
/**
 * This class defines the administration client of the case study vlibtour.
 * <ul>
 * <li>USAGE:
 * <ul>
 * <li>vlibtour.vlibtour_admin_client.VlibTourAdminClient populate toursAndPOIs
 * </li>
 * <li>vlibtour.vlibtour_admin_client.VlibTourAdminClient empty toursAndPOIs
 * </li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Denis Conan
 */
public class VlibTourTourManagementAdminClient {
	private  VlibTourTourManagement vlibTourManagement;
	/**
	 * constructs an instance of the administration client.
	 * 
	 * @throws Exception
	 *             the exception thrown by the lookup.
	 */
	public VlibTourTourManagementAdminClient() throws Exception {
		Context ic = new InitialContext();
		vlibTourManagement = (VlibTourTourManagement) ic.lookup("vlibtour.vlibtour_tour_management.api.VlibTourTourManagement");
	}
	public Tour CreateATour(String nom,String Description) throws Exception{
	
		return 	vlibTourManagement.CreateATour(nom,Description);
	
	}
	public POI CreateAPOI(String nom,String Description,double lng , double lal)throws Exception {
	
		return	vlibTourManagement.CreateAPOI(nom,Description,lng,lal);
	}
	public String addPOItoATour(String pid,String tid) throws VlibTourTourManagementException {
		return vlibTourManagement.addPOItoATour(pid,tid);
	}
	/**
	 * the main of the administration client.
	 * 
	 * @param args
	 *            the command line arguments
	 * @throws Exception
	 *             the exception that can be thrown (none is treated).
	 */
	public static void main(final String[] args) throws Exception {
			
		
	}
}

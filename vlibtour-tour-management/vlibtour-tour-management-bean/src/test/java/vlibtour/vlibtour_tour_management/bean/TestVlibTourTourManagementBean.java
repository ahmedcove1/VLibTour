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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;
import vlibtour.vlibtour_tour_management.api.VlibTourTourManagement;
import vlibtour.vlibtour_tour_management.entity.POI;
import vlibtour.vlibtour_tour_management.entity.Tour;
import vlibtour.vlibtour_tour_management.entity.VlibTourTourManagementException;

public class TestVlibTourTourManagementBean {
	private static EJBContainer ec;
	private static Context ctx;
	private static VlibTourTourManagement vlibTourManagement ;
	@BeforeClass
	public static void setUpClass() throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(EJBContainer.MODULES, new File("target/classes"));
		ec = EJBContainer.createEJBContainer(properties);
		ctx = ec.getContext();
		vlibTourManagement = (VlibTourTourManagement) ctx.lookup("vlibtour.vlibtour_tour_management.api.VlibTourTourManagement");
	}

	@Test
	public void createPOITest1() throws Exception {
		POI poi = vlibTourManagement.CreateAPOI("First POI", "First POI test",2.1, 5.5);
		assert poi != null;
	}

	@Test
	public void findPOIWithPIDTest1() throws Exception {
		POI poi = vlibTourManagement.CreateAPOI("Second POI", "Second POI",2.1, 5.8);
		POI poiFromQuery = vlibTourManagement.findPOIWithPID(poi.getPoid());
		assertEquals(poi.equals(poiFromQuery),true);
	}

	@Test
	public void findAllPOIsWithNameTest1() throws Exception {
		POI poi  = vlibTourManagement.CreateAPOI("Friends", "BEST Tour for friends",5.3, 4.6);
		POI poi1 = vlibTourManagement.CreateAPOI("Venom", "Scary Tour",9.3, 4.9);
		POI poi2 = vlibTourManagement.CreateAPOI("Friends", "Sofie Tucker's song",5.3, 4.6);
		List<POI> pois=vlibTourManagement.findPOISByName("Friends");
		assertEquals(pois.size(),2);
	}

	@Test
	public void findAllPOIsTest1() throws Exception {
		POI poi = vlibTourManagement.CreateAPOI("Friends", "Sofie Tucker",5.3, 4.6);
		POI poi1 = vlibTourManagement.CreateAPOI("Venom", "Scary Tour",9.3, 4.9);
		List<POI> list = vlibTourManagement.listPOIs();
		assertEquals(list.size(),2);
	}

	
	@Test
	public void createTourTest1() throws Exception {
		Tour tour = vlibTourManagement.CreateATour("tour", "BEST tour");
		assert tour != null;
	}

	@SuppressWarnings("deprecation")
	@Test
	public void findTourWithTIDTest1() throws Exception {
		Tour tour = vlibTourManagement.CreateATour("playingtour", "Full of parcs tour");
		Tour tourFromQuery =vlibTourManagement.findTourByTid(tour.getTid());
		Assert.assertTrue(tour.equals(tourFromQuery));
		
	}

	@Test
	public void findAllToursTest1() throws Exception {
		Tour tour = vlibTourManagement.CreateATour("First tour", "It is meee");
		Tour tour1 = vlibTourManagement.CreateATour("Second tour", "Marioo");
		List<Tour> list = vlibTourManagement.listTours();
		System.out.println(list);
		assertEquals(list.size(),2);
		
	}

	
	@Test
	public void findAllToursWithNameTest1() throws Exception {
		Tour tour = vlibTourManagement.CreateATour("First_tour", "It is meee");
		Tour tour1 = vlibTourManagement.CreateATour("Second_tour", "Marioo");
		Tour tour2 = vlibTourManagement.CreateATour("First_tour", "Here we Goo");
		List<Tour> list = vlibTourManagement.findTOursByName("First_tour");
		assertEquals(list.size(),2);
		
	}
	

	@After
	public void tearDown() throws Exception {
		List<POI> pois= vlibTourManagement.listPOIs();
		for(POI a:pois) {
			vlibTourManagement.removePOI(a.getPoid());
		}
		List<Tour> tours= vlibTourManagement.listTours();
		for(Tour t:tours) {
			vlibTourManagement.removeTour(t.getTid());;
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

}

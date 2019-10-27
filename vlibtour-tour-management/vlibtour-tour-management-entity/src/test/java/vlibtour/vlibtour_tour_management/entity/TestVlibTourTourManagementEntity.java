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

import org.junit.Ignore;
import org.junit.Test;

public class TestVlibTourTourManagementEntity {

	
	@Test
	public void createPOITest1() throws Exception {
		POI poi = new POI("Fist POI","First of its kind",0.0,0.0);
		assert poi != null;
	}
	@Test(expected = VlibTourTourManagementException.class)
	public void createPOITest2() throws Exception {
		POI poi = new POI("","No Name",0.0,0.0);
	}
	@Test(expected = VlibTourTourManagementException.class)
	public void createPOITest3() throws Exception {
		POI poi = new POI("POI with no description","",0.0,0.0);
	}
	@Test
	public void createTourTest1() throws Exception {
		Tour tour= new Tour("First of its kind","First Tour");
		assert tour!=null;
	}

	@Test(expected = VlibTourTourManagementException.class)
	public void createTourTest2() throws Exception {
		Tour tour= new Tour("Tour withour a name","");
	}
	@Test(expected = VlibTourTourManagementException.class)
	public void createTourTest3() throws Exception {
		Tour tour= new Tour("","Tour without a description");
	}
}

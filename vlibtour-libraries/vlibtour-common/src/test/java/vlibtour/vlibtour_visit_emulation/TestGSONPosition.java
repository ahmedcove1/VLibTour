// CHECKSTYLE:OFF
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

Initial developer(s): Chantal Taconet
Contributor(s):
 */
package vlibtour.vlibtour_visit_emulation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vlibtour.vlibtour_visit_emulation.GPSPosition;

public class TestGSONPosition {
	Position museeGrevin;
	Position museeGrevinBis;
	Position pyramideLouvres;

	@Before
	public void setUp() {
		museeGrevin = new Position("Musée Grévin", new GPSPosition(48.871799, 2.342355));
		museeGrevinBis = new Position("Musée Grévin", new GPSPosition(48.871799, 2.342355));
		pyramideLouvres = new Position("Pyramide du Louvres", new GPSPosition(48.860959, 2.335757));
	}

	@After
	public void tearDown() {
		museeGrevin = null;
		museeGrevinBis = null;
		pyramideLouvres = null;
	}

	@Test
	public void testDistance() {
		Assert.assertEquals(museeGrevin, museeGrevinBis);
		Assert.assertNotEquals(museeGrevin, pyramideLouvres);
		Assert.assertEquals(Position.GSON.toJson(museeGrevin),Position.GSON.toJson(museeGrevinBis));
//		System.out.println(Position.GSON.toJson(museeGrevin));
		Assert.assertNotEquals(Position.GSON.toJson(museeGrevin),Position.GSON.toJson(pyramideLouvres));
		Assert.assertEquals(museeGrevin,Position.GSON.fromJson(Position.GSON.toJson(museeGrevin), Position.class));
		Assert.assertEquals(museeGrevin,Position.GSON.fromJson(Position.GSON.toJson(museeGrevinBis), Position.class));
		Assert.assertNotEquals(museeGrevin,Position.GSON.fromJson(Position.GSON.toJson(pyramideLouvres), Position.class));
	}
}

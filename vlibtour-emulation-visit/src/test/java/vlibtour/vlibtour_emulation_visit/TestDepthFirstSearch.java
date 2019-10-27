package vlibtour.vlibtour_emulation_visit;
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

Initial developer(s): Denis Conan
Contributor(s):
 */


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vlibtour.vlibtour_emulation_visit.GraphOfPositionsForEmulation;
import vlibtour.vlibtour_visit_emulation.Position;

public class TestDepthFirstSearch {
	private Map<Position, Set<Position>> adjacencySets = new HashMap<>();

	private static Map<Position, Set<Position>> addEdge(final Map<Position, Set<Position>> adjacencyList,
			final int departure, final int destination) {
		return GraphOfPositionsForEmulation.addEdge(adjacencyList, new Position(String.valueOf(departure), null),
				new Position(String.valueOf(destination), null));
	}

	@Before
	public void setUp() {
		addEdge(adjacencySets, 1, 6);
		addEdge(adjacencySets, 6, 9);
		addEdge(adjacencySets, 9, 12);
		addEdge(adjacencySets, 12, 14);
		addEdge(adjacencySets, 14, 16);
		addEdge(adjacencySets, 16, 18);
		addEdge(adjacencySets, 18, 20);
		addEdge(adjacencySets, 20, 22);
		addEdge(adjacencySets, 22, 24);
		addEdge(adjacencySets, 24, 28);
		addEdge(adjacencySets, 28, 31);
		addEdge(adjacencySets, 31, 34);
		addEdge(adjacencySets, 34, 37);
		addEdge(adjacencySets, 37, 40);
		addEdge(adjacencySets, 40, 43);
		addEdge(adjacencySets, 43, 46);
		addEdge(adjacencySets, 46, 47);

		addEdge(adjacencySets, 2, 3);
		addEdge(adjacencySets, 3, 4);
		addEdge(adjacencySets, 4, 5);
		addEdge(adjacencySets, 5, 6);
		addEdge(adjacencySets, 5, 8);
		addEdge(adjacencySets, 8, 10);
		addEdge(adjacencySets, 10, 11);

		addEdge(adjacencySets, 4, 7);
		addEdge(adjacencySets, 7, 11);
		addEdge(adjacencySets, 11, 13);
		addEdge(adjacencySets, 13, 15);
		addEdge(adjacencySets, 15, 17);
		addEdge(adjacencySets, 17, 19);
		addEdge(adjacencySets, 19, 21);
		addEdge(adjacencySets, 21, 23);
		addEdge(adjacencySets, 23, 26);
		addEdge(adjacencySets, 26, 29);
		addEdge(adjacencySets, 29, 32);
		addEdge(adjacencySets, 32, 35);
		addEdge(adjacencySets, 35, 38);
		addEdge(adjacencySets, 38, 41);
		addEdge(adjacencySets, 41, 44);
		addEdge(adjacencySets, 44, 43);

		addEdge(adjacencySets, 26, 25);
		addEdge(adjacencySets, 25, 27);
		addEdge(adjacencySets, 27, 30);
		addEdge(adjacencySets, 30, 33);
		addEdge(adjacencySets, 33, 36);
		addEdge(adjacencySets, 36, 39);
		addEdge(adjacencySets, 39, 42);
		addEdge(adjacencySets, 42, 45);
		addEdge(adjacencySets, 42, 36); // cycle
		addEdge(adjacencySets, 45, 47);
		addEdge(adjacencySets, 47, 46);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNoPathFromUnknownDeparture() {
		List<List<Position>> pathsFromDepartureToDestination = GraphOfPositionsForEmulation.computePathsFromDepartureToDestination(
				adjacencySets, new Position(String.valueOf(0), null), new Position(String.valueOf(46), null));
		Assert.assertNotNull(pathsFromDepartureToDestination);
		Assert.assertEquals(0, pathsFromDepartureToDestination.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNoPathToUnknownDestination() {
		List<List<Position>> pathsFromDepartureToDestination = GraphOfPositionsForEmulation.computePathsFromDepartureToDestination(
				adjacencySets, new Position(String.valueOf(1), null), new Position(String.valueOf(100), null));
		Assert.assertNotNull(pathsFromDepartureToDestination);
		Assert.assertEquals(0, pathsFromDepartureToDestination.size());
	}

	@Test
	public void testOnePathWithoutAnyCycle() {
		List<List<Position>> pathsFromDepartureToDestination = GraphOfPositionsForEmulation.computePathsFromDepartureToDestination(
				adjacencySets, new Position(String.valueOf(21), null), new Position(String.valueOf(43), null));
		Assert.assertNotNull(pathsFromDepartureToDestination);
		Assert.assertEquals(1, pathsFromDepartureToDestination.size());
		Assert.assertEquals(10, pathsFromDepartureToDestination.get(0).size());
	}

	@Test
	public void testOnePathWithoutAnyCycle2() {
		List<List<Position>> pathsFromDepartureToDestination = GraphOfPositionsForEmulation.computePathsFromDepartureToDestination(
				adjacencySets, new Position(String.valueOf(2), null), new Position(String.valueOf(4), null));
		Assert.assertNotNull(pathsFromDepartureToDestination);
		Assert.assertEquals(1, pathsFromDepartureToDestination.size());
		Assert.assertEquals(3, pathsFromDepartureToDestination.get(0).size());
	}

	@Test
	public void testOnePathWithACycle() {
		List<List<Position>> pathsFromDepartureToDestination = GraphOfPositionsForEmulation.computePathsFromDepartureToDestination(
				adjacencySets, new Position(String.valueOf(33), null), new Position(String.valueOf(46), null));
		Assert.assertNotNull(pathsFromDepartureToDestination);
		Assert.assertEquals(1, pathsFromDepartureToDestination.size());
		Assert.assertEquals(7, pathsFromDepartureToDestination.get(0).size());
	}

	@Test
	public void testTwoPathsWithACycle() {
		List<List<Position>> pathsFromDepartureToDestination = GraphOfPositionsForEmulation.computePathsFromDepartureToDestination(
				adjacencySets, new Position(String.valueOf(26), null), new Position(String.valueOf(46), null));
		Assert.assertNotNull(pathsFromDepartureToDestination);
		Assert.assertEquals(2, pathsFromDepartureToDestination.size());
		for (int i = 0; i < pathsFromDepartureToDestination.size(); i++) {
			if (pathsFromDepartureToDestination.get(i).size() != 9
					&& pathsFromDepartureToDestination.get(i).size() != 11) {
				Assert.fail();
			}
		}
	}

	@Test
	public void testManyPathsWithACycle() {
		List<List<Position>> pathsFromDepartureToDestination = GraphOfPositionsForEmulation.computePathsFromDepartureToDestination(
				adjacencySets, new Position(String.valueOf(2), null), new Position(String.valueOf(46), null));
		Assert.assertNotNull(pathsFromDepartureToDestination);
		Assert.assertEquals(5, pathsFromDepartureToDestination.size());
	}

	@Test
	public void testManyPathsWithACycle2() {
		List<List<Position>> pathsFromDepartureToDestination = GraphOfPositionsForEmulation.computePathsFromDepartureToDestination(
				adjacencySets, new Position(String.valueOf(4), null), new Position(String.valueOf(19), null));
		Assert.assertNotNull(pathsFromDepartureToDestination);
		Assert.assertEquals(2, pathsFromDepartureToDestination.size());
	}

	@After
	public void tearDown() {
		adjacencySets = null;
	}

}

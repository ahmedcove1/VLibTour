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
Contributor(s): Chantal Taconet
 */
package vlibtour.vlibtour_emulation_visit;

import static vlibtour.vlibtour_visit_emulation.Log.EMULATION;
import static vlibtour.vlibtour_visit_emulation.Log.LOG_ON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Level;

import vlibtour.vlibtour_visit_emulation.ExampleOfAVisitWithTwoTourists;
import vlibtour.vlibtour_visit_emulation.Position;

/**
 * This class contains all the methods to manipulate a graph of positions for
 * emulating a visit in a scenario. It provides many utility methods. You are
 * free to complement or modify this code.
 * <p>
 * This is only for writing scenarios with the emulation of locations.
 * <p>
 * The data structures are designed to allow the management of the mobility of
 * several users.
 * <p>
 * You are free to use this class internally (in your clients for instance) or
 * through a remote call (in a REST server for instance).
 * <p>
 * Be careful to not use this class to simplify the distributed implementation
 * of the management of the visits!
 * <p>
 * A limitation of the current implementation is that there is only one graph of
 * positions.
 * <p>
 * The first part of the class is the definition of the graph with its methods:
 * e.g. {@link #addEdge(Map, Position, Position)} or
 * {@link #computePathsFromDepartureToDestination(Map, Object, Object)}.
 * <p>
 * The second part of the class is the definition of paths with its methods:
 * {@link #stepInCurrentPath(String)}, etc.
 * <p>
 * Please consult the class {@link VisitEmulationServer} that uses the data
 * structures and the methods of this class.
 * 
 * @author Denis Conan
 */
public final class GraphOfPositionsForEmulation {
	/**
	 * the graph of positions used for the emulation of the visit in scenarios. The
	 * graph is defined has a set of adjacency sets, one per node (position).
	 */
	private static Map<Position, Set<Position>> adjacencySets = new HashMap<>();
	/**
	 * the current paths of the users.
	 */
	private final static Map<String, List<Position>> currentPaths = new HashMap<>();
	/**
	 * the current positions of the users for which the emulator provides these
	 * utility methods.
	 */
	private final static Map<String, Position> currentPositionOfUsers = new HashMap<>();

	/**
	 * private constructor of the utility class.
	 */
	private GraphOfPositionsForEmulation() {
	}

	// the method that are part of the API and that are accessed through delegation

	/**
	 * gets the position of the next POI to visit when arrived at the end of the
	 * current path, that is the next POI in the visit. This position is the last
	 * one of the current path.
	 * 
	 * @param user the identifier of the user.
	 * @return the position of the current POI.
	 */
	static synchronized Position getNextPOIPosition(final String user) {
		if (currentPaths.get(user) == null) {
			throw new IllegalArgumentException("user " + user + " has no current path set (visit not started)");
		}
		return currentPaths.get(user).get(currentPaths.get(user).size() - 1);
	}

	/**
	 * gets the current position of a user.
	 * 
	 * @param user the identifier of the user.
	 * @return the current position of the user.
	 */
	static synchronized Position getCurrentPosition(final String user) {
		return currentPositionOfUsers.get(user);
	}

	/**
	 * steps to the next position in the current path. It returns the new position
	 * of the user, or the same if the end of the path is already reached.
	 * 
	 * @param user the identifier of the user.
	 * @return the new position of the user, or the same if the end of the path is
	 *         already reached.
	 */
	static synchronized Position stepInCurrentPath(final String user) {
		if (adjacencySets == null || adjacencySets.isEmpty()) {
			throw new IllegalStateException("There is no graph of positions");
		}
		List<Position> currentPath = currentPaths.get(user);
		if (currentPath == null) {
			throw new IllegalArgumentException("user " + user + " has no current path set (visit not started)");
		}
		if (currentPaths.get(user).get(currentPaths.get(user).size() - 1).equals(getCurrentPosition(user))) {

			if (LOG_ON && EMULATION.isEnabledFor(Level.DEBUG)) {
				EMULATION.debug(user + ": already at the end of the current path (return the same position)");
			}
		} else {
			int indexNext = currentPath.indexOf(currentPositionOfUsers.get(user)) + 1;
			currentPositionOfUsers.put(user, currentPath.get(indexNext));
			if (LOG_ON && EMULATION.isDebugEnabled()) {
				EMULATION.debug(user + ": step in current path, new position = " + currentPath.get(indexNext));
			}
		}
		return getCurrentPosition(user);
	}

	/**
	 * creates an edge by considering that the departure and the destination
	 * positions.
	 * <p>
	 * This method has visibility {@code package} because it is used only by classes
	 * in the same package and in JUnit tests of the module.
	 * 
	 * @param adjacencySets the adjacency sets before the addition.
	 * @param departure     the departure position.
	 * @param destination   the destination position.
	 * @return the new network that is updated with the new edge.
	 */
	static Map<Position, Set<Position>> addEdge(final Map<Position, Set<Position>> adjacencySets,
			final Position departure, final Position destination) {
		Map<Position, Set<Position>> result = null;
		if (adjacencySets == null) {
			result = new HashMap<>();
		} else {
			result = adjacencySets;
		}
		Set<Position> neighbours = result.get(departure);
		if (neighbours == null) {
			neighbours = new HashSet<>();
			result.put(departure, neighbours);
		}
		neighbours.add(destination);
		return result;
	}

	/**
	 * computes the set of paths from the departure node to the destination node.
	 * This method call
	 * {@link #depthFirstSearchRecursive(Map, Map, Object, Object, List, List)},
	 * which is the recursive algorithm and requires a data structure to indicate
	 * that nodes have been visited.
	 * <p>
	 * This method has visibility {@code package} because it is used only by classes
	 * in JUnit tests of the module.
	 * 
	 * @param <T>           the type of the nodes.
	 * @param adjacencySets the adjacency sets of edges.
	 * @param departure     the departure node.
	 * @param destination   the destination node.
	 * @return the list of paths, which are lists of nodes.
	 */
	static <T> List<List<T>> computePathsFromDepartureToDestination(final Map<T, Set<T>> adjacencySets,
			final T departure, final T destination) {
		Map<T, Boolean> visited = new HashMap<>();
		List<T> path = new ArrayList<>();
		if (adjacencySets.get(departure) == null) {
			throw new IllegalArgumentException("departure position not in the network (" + departure + ")");
		}
		if (adjacencySets.get(destination) == null) {
			throw new IllegalArgumentException("destination position not in the network (" + destination + ")");
		}
		return depthFirstSearchRecursive(adjacencySets, visited, departure, destination, path, null);
	}

	/**
	 * computes the depth first search recursively. When the destination is reached,
	 * the path is added to
	 * {@link accumulatedCompletePathsFromDepartureToDestination}.
	 * 
	 * @param <T>                       the type of the nodes.
	 * @param adjacencySets             the adjacency sets of nodes.
	 * @param visited                   the nodes already visited.
	 * @param departure                 the departure node.
	 * @param destination               the destination node.
	 * @param currentPathInConstruction the path in construction from the departure
	 *                                  node to the destination node.
	 * @param accumulatedPaths          the current accumulated list of (complete)
	 *                                  paths from departure to node.
	 * @return the accumulated list of (complete) paths.
	 */
	private static <T> List<List<T>> depthFirstSearchRecursive(final Map<T, Set<T>> adjacencySets,
			final Map<T, Boolean> visited, final T departure, final T destination,
			final List<T> currentPathInConstruction, final List<List<T>> accumulatedPaths) {
		if (currentPathInConstruction == null) {
			throw new IllegalArgumentException("the current path cannot be an null object");
		}
		visited.put(departure, true);
		currentPathInConstruction.add(departure);
		List<List<T>> result = null;
		if (accumulatedPaths == null) {
			result = new ArrayList<>();
		} else {
			result = accumulatedPaths;
		}
		if (departure.equals(destination)) {
			result.add(new ArrayList<>(currentPathInConstruction));
		} else {
			for (T w : adjacencySets.getOrDefault(departure, Collections.emptySet())) {
				if (!visited.getOrDefault(w, false)) {
					depthFirstSearchRecursive(adjacencySets, visited, w, destination, currentPathInConstruction,
							result);
				}
			}
		}
		currentPathInConstruction.remove(currentPathInConstruction.size() - 1);
		visited.put(departure, false);
		return result;
	}

	/**
	 * gets the graph of positions with the POIs. This is for emulation purposes.
	 * <p>
	 * This method has visibility {@code package} because it is used only by classes
	 * in the same package.
	 * 
	 * @return the graph as the set of adjacency lists.
	 */
	static synchronized Map<Position, Set<Position>> getAdjacencySets() {
		return adjacencySets;
	}

	/**
	 * sets the graph of positions with the POIs. This is for emulation purposes.
	 * 
	 * @param adjSets the graph as the set of adjacency lists.
	 */
	static synchronized void setAdjacencySets(final Map<Position, Set<Position>> adjSets) {
		adjacencySets = adjSets;
	}

	/**
	 * sets the possible paths to the next positions, chose one of the path, and set
	 * the current position to the first position of the path. The argument are the
	 * user and the destination position (e.g. the next POI in the visit).
	 * 
	 * @param user        the identifier of the user.
	 * @param destination the destination position.
	 */
	static synchronized void setAPathTo(final String user, final Position destination) {
		if (destination == null) {
			throw new IllegalArgumentException("destination position cannot be null");
		}
		if (currentPositionOfUsers.get(user) == null) {
			throw new IllegalArgumentException("user " + user + " has no current position set");
		}
		List<List<Position>> possiblePaths = GraphOfPositionsForEmulation
				.computePathsFromDepartureToDestination(adjacencySets, currentPositionOfUsers.get(user), destination);
		if (LOG_ON && EMULATION.isDebugEnabled()) {
			EMULATION.debug(user + ": possible paths to the next POI = "
					+ possiblePaths.stream()
							.map(l -> l.stream().map(Position::getName).collect(Collectors.joining(",")))
							.collect(Collectors.joining("\n")));
		}
		// the source of indeterminism is in choice
		int choice = ((user.equals(ExampleOfAVisitWithTwoTourists.USER_ID_JOE)) ? 1 : 2) % possiblePaths.size();
		currentPaths.put(user, possiblePaths.get(choice));
		if (LOG_ON && EMULATION.isDebugEnabled()) {
			EMULATION.debug(user + ": path to next POI = "
					+ currentPaths.get(user).stream().map(Position::getName).collect(Collectors.joining(",")));
		}
	}

	/**
	 * sets the current position of the given user at before the beginning of the
	 * visit. The method must be called only once per user.
	 * 
	 * @param user     the identifier of the user.
	 * @param position the starting position.
	 */
	static synchronized void setStartingPosition(final String user, final Position position) {
		if (currentPositionOfUsers.get(user) != null) {
			throw new IllegalStateException("user " + user + " already has a starting position");
		}
		currentPositionOfUsers.put(user, position);
	}
}

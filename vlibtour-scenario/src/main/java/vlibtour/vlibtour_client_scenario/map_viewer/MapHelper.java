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
Contributor(s): Denis Conan
 */
package vlibtour.vlibtour_client_scenario.map_viewer;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.net.MalformedURLException;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import vlibtour.vlibtour_visit_emulation.Position;

/**
 * This class contains some utility methods for displaying POIs and tourists on
 * a Open Street Map.
 * 
 * @author Chantal Taconet
 * @author Denis Conan
 */
public final class MapHelper {

	/**
	 * private constructor to avoid instantiation.
	 */
	private MapHelper() {
	}

	/**
	 * initialises the map: map viewer, and display position with the centre and the
	 * zoom level.
	 * 
	 * @param latitude  the latitude of the centre of the map.
	 * @param longitude the longitude of the centre of the map.
	 * @param zoomLevel the zoom level.
	 * @return the new map.
	 * @throws MalformedURLException pb in the file URL
	 */
	public static BasicMap createMapWithCenterAndZoomLevel(final double latitude,
			final double longitude, final int zoomLevel) throws MalformedURLException {
		BasicMap basicMap = new BasicMap();
		basicMap.map().setDisplayPosition(new Coordinate(latitude, longitude), zoomLevel);
		/*
		 * NB for offline access, the files have been downloaded with
		 * openstreetmap-client in the directory ~/.cache/champlain/osm-mapnik/ and then
		 * copied in src/main/resources
		 */
		basicMap.map()
			.setTileSource(new OfflineOsmTileSource(
					Thread.currentThread().getContextClassLoader().getResource("osm-mapnik/").toString(), zoomLevel,
					zoomLevel));

		return basicMap;
	}

	/**
	 * adds a marker dot on the map.
	 * 
	 * @param map       the map.
	 * @param latitude  the latitude of the marker dot.
	 * @param longitude the longitude of the marker dot.
	 * @param color     the color of the text.
	 * @param font      the font of the text.
	 * @param name      the name of the marker dot that is also the text.
	 * @return the new marker dot.
	 */
	public static MapMarkerDot addMarkerDotOnMap(final BasicMap map, final double latitude, final double longitude,
			final Color color, final Font font, final String name) {
		MapMarkerDot dot = new MapMarkerDot(map.getMarkerLayer(), "centrage", latitude, longitude);
		map.map().addMapMarker(dot);
		dot.setFont(font);
		dot.setColor(color);
		dot.setBackColor(color);
		dot.setName(name);
		return dot;
	}

	/**
	 * adds a tourist on the map.
	 * 
	 * @param map      the map.
	 * @param color    the color of the name of the tourist on the map.
	 * @param font     the font of the map.
	 * @param name     the name of the tourist that is used to search for the
	 *                 position and is also used for the text of the marker dot on
	 *                 the map.
	 * @param position the initial position.
	 * @return the new marker dot.
	 */
	public static MapMarkerDot addTouristOnMap(final BasicMap map, final Color color, final Font font,
			final String name, final Position position) {
		return addMarkerDotOnMap(map, position.getGpsPosition().getLatitude(), position.getGpsPosition().getLongitude(),
				color, font, name);
	}

	/**
	 * moves the marker dot of the tourist on the map.
	 * <p>
	 * Let us protect against bad input values: be silent—i.e. don't paint.
	 * 
	 * @param dot         the marker dot of the tourist.
	 * @param newPosition the new position to move to.
	 */
	public static void moveTouristOnMap(final MapMarkerDot dot, final Position newPosition) {
		if (dot == null || newPosition == null || newPosition.getGpsPosition() == null) {
			return;
		}
		dot.setLat(newPosition.getGpsPosition().getLatitude());
		dot.setLon(newPosition.getGpsPosition().getLongitude());
	}
}

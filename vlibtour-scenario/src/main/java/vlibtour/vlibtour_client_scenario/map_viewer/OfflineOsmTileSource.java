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
package vlibtour.vlibtour_client_scenario.map_viewer;

import org.openstreetmap.gui.jmapviewer.tilesources.AbstractOsmTileSource;

/**
 * This class defines the cache used when using OSM offline.
 * 
 * @author Chantal Taconet
 */
public class OfflineOsmTileSource extends AbstractOsmTileSource {
	/**
	 * the minimum zoom level.
	 */
	private final int minZoom;
	/**
	 * the maximum zoom level.
	 */
	private final int maxZoom;

	/**
	 * constructs the cache of the OSM tiles.
	 * 
	 * @param path
	 *            the path to the tiles.
	 * @param minZoom
	 *            the minimum zoom level.
	 * @param maxZoom
	 *            the maximum zoom level.
	 */
	public OfflineOsmTileSource(final String path, final int minZoom, final int maxZoom) {
		super("Offline from " + path, path, "cache");
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
	}

	@Override
	public int getMaxZoom() {
		return maxZoom;
	}

	@Override
	public int getMinZoom() {
		return minZoom;
	}
}

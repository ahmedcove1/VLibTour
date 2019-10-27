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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.OsmTileLoader;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

/**
 * This class defines a basic map.
 * 
 * @author Chantal Taconet
 */
public class BasicMap extends JFrame implements JMapViewerEventListener {
	/**
	 * serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the map viewer.
	 */
	private final JMapViewerTree treeMap;
	/**
	 * the layer.
	 */
	private Layer markerLayer;

	/**
	 * constructs a basic map.
	 * 
	 * @throws MalformedURLException pb in URL.
	 */
	public BasicMap() throws MalformedURLException {
		treeMap = new JMapViewerTree("Zones");
		treeMap.addLayer("marqueurs");
		map().addJMVListener(this);
		final int defaultZoom = 15;
		map().setZoom(defaultZoom);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		JPanel panelTop = new JPanel();
		JPanel panelBottom = new JPanel();
		JComboBox<TileSource> tileSourceSelector = new JComboBox<TileSource>(new TileSource[] {
				new OsmTileSource.Mapnik(), new OsmTileSource.CycleMap(), new BingAerialTileSource(), });
		tileSourceSelector.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent e) {
				map().setTileSource((TileSource) e.getItem());
			}
		});
		JComboBox<TileLoader> tileLoaderSelector;
		tileLoaderSelector = new JComboBox<TileLoader>(new TileLoader[] {new OsmTileLoader(map())});
		tileLoaderSelector.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent e) {
				map().setTileLoader((TileLoader) e.getItem());
			}
		});
		map().setTileLoader((TileLoader) tileLoaderSelector.getSelectedItem());
		panelTop.add(tileSourceSelector);
		panelTop.add(tileLoaderSelector);
		final JCheckBox showMapMarker = new JCheckBox("Map markers visible");
		showMapMarker.setSelected(map().getMapMarkersVisible());
		showMapMarker.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				map().setMapMarkerVisible(showMapMarker.isSelected());
			}
		});
		panelBottom.add(showMapMarker);
		final JCheckBox showToolTip = new JCheckBox("ToolTip visible");
		showToolTip.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				map().setToolTipText(null);
			}
		});
		panelBottom.add(showToolTip);
		final JCheckBox showTileGrid = new JCheckBox("Tile grid visible");
		showTileGrid.setSelected(map().isTileGridVisible());
		showTileGrid.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				map().setTileGridVisible(showTileGrid.isSelected());
			}
		});
		panelBottom.add(showTileGrid);
		final JCheckBox showZoomControls = new JCheckBox("Show zoom controls");
		showZoomControls.setSelected(map().getZoomControlsVisible());
		showZoomControls.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				map().setZoomContolsVisible(showZoomControls.isSelected());
			}
		});
		panelBottom.add(showZoomControls);
		final JCheckBox scrollWrapEnabled = new JCheckBox("Scrollwrap enabled");
		scrollWrapEnabled.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				map().setScrollWrapEnabled(scrollWrapEnabled.isSelected());
			}
		});
		panelBottom.add(scrollWrapEnabled);
		add(treeMap, BorderLayout.CENTER);
		map().addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(final MouseEvent e) {
				Point p = e.getPoint();
				boolean cursorHand = map().getAttribution().handleAttributionCursor(p);
				if (cursorHand) {
					map().setCursor(new Cursor(Cursor.HAND_CURSOR));
				} else {
					map().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				if (showToolTip.isSelected()) {
					map().setToolTipText(map().getPosition(p).toString());
				}
			}
		});
		setVisible(true);
		map().setTileSource(new OsmTileSource.Mapnik());
		map().setTileLoader(new OsmTileLoader(map()));
		final int zoomLevel = 14;
		map().setTileSource(new OfflineOsmTileSource(
				Thread.currentThread().getContextClassLoader().getResource("osm-mapnik/").toString(), zoomLevel, zoomLevel));
//		map().setTileSource(new OfflineOsmTileSource(
//				(new File("src/main/resources/osm-mapnik/").toURI().toURL()).toString(), zoomLevel, zoomLevel));

		map().setMapMarkerVisible(true);
		map().setZoomContolsVisible(true);
		// map().setDisplayToFitMapMarkers();
		// map().zoomOut();
	}

	/**
	 * gets the map viewer.
	 * 
	 * @return the map viewer.
	 */
	public JMapViewer map() {
		return treeMap.getViewer();
	}

	/**
	 * removes an existing marker.
	 * 
	 * @param mapMarker
	 *            the marker to remove.
	 */
	public void removeMarker(final MapMarkerDot mapMarker) {
		map().removeMapMarker(mapMarker);
	}

	/**
	 * updates the value of an existing marker.
	 * 
	 * @param mapMarker
	 *            the marker to modify.
	 * @param type
	 *            its type.
	 * @param newValue
	 *            the new value.
	 */
	public void updateValue(final MapMarkerDot mapMarker, final String type, final String newValue) {
		mapMarker.setName(type + " :" + newValue);
		map().repaint();
	}

	/**
	 * updates the coordinate and the value of an existing marker.
	 * 
	 * @param mapMarker
	 *            the marker to modify.
	 * @param type
	 *            type of the marker.
	 * @param newValue
	 *            new value.
	 * @param newLat
	 *            new latitude.
	 * @param newLon
	 *            new longitude.
	 */
	public void updateCoordAndValue(final MapMarkerDot mapMarker, final String type, final String newValue,
			final double newLat, final double newLon) {
		mapMarker.setName(type + " :" + newValue);
		mapMarker.setLat(newLat);
		mapMarker.setLon(newLon);
		map().setDisplayToFitMapMarkers();
	}

	/**
	 * adds a marker to the marker layer.
	 * 
	 * @param nameOfMarker
	 *            marker to be added.
	 * @param markerLayer
	 *            the layer marker.
	 * @param markerName
	 *            name of the marker.
	 * @param markerValue
	 *            value to be written.
	 * @param lat
	 *            latitude position of the marker.
	 * @param lon
	 *            longitude position of the marker.
	 */
	public void addMarker(final MapMarkerDot nameOfMarker, final Layer markerLayer, final String markerName,
			final String markerValue, final double lat, final double lon) {
		MapMarkerDot newNameOfMarker = new MapMarkerDot(markerLayer, markerName + " :" + markerValue, lat, lon);
		map().addMapMarker(newNameOfMarker);
		map().setDisplayToFitMapMarkers();
	}

	/**
	 * adds circle around a given marker.
	 * 
	 * @param nameOfMarker
	 *            name of the marker.
	 * @param width
	 *            circle diameter.
	 */
	public void addCircleAroundDevice(final MapMarkerDot nameOfMarker, final double width) {
		Coordinate c = new Coordinate(nameOfMarker.getLon(), nameOfMarker.getLat());
		MapMarkerCircle circle = new MapMarkerCircle(c, width);
		final int red = 50;
		final int alpha = 100;
		Color color = new Color(red, 0, 0, alpha);
		circle.setBackColor(color);
		map().addMapMarker(circle);
	}

	/**
	 * updates the position of a marker on the map.
	 * 
	 * @param firstMarker
	 *            name of the marker.
	 * @param lat
	 *            new latitude.
	 * @param lon
	 *            new longitude.
	 */
	public void updateMarker(final MapMarkerDot firstMarker, final double lat, final double lon) {
		firstMarker.setLat(lat);
		firstMarker.setLon(lon);
	}

	/**
	 * gets the layer which contains the markers.
	 * 
	 * @return the marker layer.
	 */
	public Layer getMarkerLayer() {
		return markerLayer;
	}

	/**
	 * gets the tree map.
	 * 
	 * @return the tree map.
	 */
	public JMapViewerTree getTreeMap() {
		return treeMap;
	}

	@Override
	public void processCommand(final JMVCommandEvent command) {
	}
}

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
package vlibtour.vlibtour_client_scenario;

import static vlibtour.vlibtour_visit_emulation.Log.EMULATION;
import static vlibtour.vlibtour_visit_emulation.Log.LOG_ON;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.tools.jsonrpc.JsonRpcException;

import vlibtour.vlibtour_client_emulation_visit.VLibTourVisitEmulationClient;
import vlibtour.vlibtour_client_group_communication_system.VLibTourGroupCommunicationSystemClient;
import vlibtour.vlibtour_client_lobby_room.VLibTourLobbyRoomClient;
import vlibtour.vlibtour_client_scenario.map_viewer.BasicMap;
import vlibtour.vlibtour_client_scenario.map_viewer.MapHelper;
import vlibtour.vlibtour_lobby_room_api.InAMQPPartException;
import vlibtour.vlibtour_lobby_room_server.VLibTourLobbyServer;
import vlibtour.vlibtour_tour_management.api.VlibTourTourManagement;
import vlibtour.vlibtour_tour_management.entity.POI;
import vlibtour.vlibtour_tour_management.entity.Tour;
import vlibtour.vlibtour_tour_management.entity.VlibTourTourManagementException;
import vlibtour.vlibtour_visit_emulation.ExampleOfAVisitWithTwoTourists;
import vlibtour.vlibtour_visit_emulation.GPSPosition;
import vlibtour.vlibtour_visit_emulation.Position;

/**
 * This class is the client application of the tourists. The access to the lobby
 * room server, to the group communication system, and to the visit emulation
 * server visit should be implemented using the design pattern Delegation (see
 * https://en.wikipedia.org/wiki/Delegation_pattern).
 * 
 * A client creates two queues to receive messages from the broker; the binding
 * keys are of the form "{@code *.*.identity}" and "{@code *.*.location}" while
 * the routing keys are of the form "{@code sender.receiver.identity|location}".
 * 
 * This class uses the classes {@code MapHelper} and {@code BasicMap} for
 * displaying the tourists on the map of Paris. Use the attributes for the
 * color, the map, the map marker dot, etc.
 * 
 * @author Denis Conan
 */
public class VLibTourVisitTouristApplication {
	/**
	 * the colour onto the map of the user identifier of the first tourist.
	 */
	private static final Color colorJoe = Color.RED;
	/**
	 * the colour onto the map of the user identifier of the second tourist.
	 */
	private static final Color colorAvrel = Color.GREEN;
	/**
	 * the map to manipulate. Not all the clients need to have a map; therefore we
	 * use an optional.
	 */
	@SuppressWarnings("unused")
	private static Optional<BasicMap> map = Optional.empty();
	/**
	 * the dot on the map for the first tourist.
	 */
	@SuppressWarnings("unused")
	private static MapMarkerDot mapDotJoe;
	/**
	 * the dot on the map for the second tourist.
	 */
	@SuppressWarnings("unused")
	private static MapMarkerDot mapDotAvrel;
	/**
	 * delegation to the client of type
	 * {@link vlibtour.vlibtour_client_emulation_visit.VLibTourVisitEmulationClient}.
	 */
	@SuppressWarnings("unused")
	private VLibTourVisitEmulationClient emulationVisitClient;
	/**
	 * delegation to the client of type
	 * {@link vlibtour.vlibtour_client_group_communication_system.VLibTourGroupCommunicationSystemClient}.
	 */
	@SuppressWarnings("unused")
	private VLibTourLobbyRoomClient lobbyRoomClient;
	/**
	 * delegation to the client of type
	 * {@link vlibtour.vlibtour_client_group_communication_system.VLibTourGroupCommunicationSystemClient}.
	 * The identifier of the user is obtained from this reference.
	 */
	@SuppressWarnings("unused")
	private static VLibTourGroupCommunicationSystemClient groupCommClient;
	
	private static Map<String,Position> Others = new HashMap<String,Position>();
	private static VLibTourVisitTouristApplication client;
	private static Position positionOfOther;
	/**
	 * creates a client application, which will join a group that must already
	 * exist. The group identifier is optional whether this is the first user of the
	 * group ---i.e. the group identifier is built upon the user identifier.
	 * Concerning the tour identifier, it must be provided by the calling method.
	 * 
	 * @param tourId  the tour identifier of this application.
	 * @param groupId the group identifier of this client application.
	 * @param userId  the user identifier of this client application.
	 * @throws InAMQPPartException             the exception thrown in case of a
	 *                                         problem in the AMQP part.
	 * @throws VlibTourTourManagementException problem in the name or description of
	 *                                         POIs.
	 * @throws IOException                     problem when setting the
	 *                                         communication configuration with the
	 *                                         broker.
	 * @throws TimeoutException                problem in creation of connection,
	 *                                         channel, client before the RPC to the
	 *                                         lobby room.
	 * @throws JsonRpcException                problem in creation of connection,
	 *                                         channel, client before the RPC to the
	 *                                         lobby room.
	 * @throws InterruptedException            thread interrupted in call sleep.
	 * @throws NamingException                 the EJB server has not been found
	 *                                         when getting the tour identifier.
	 * @throws URISyntaxException 
	 */
	public VLibTourVisitTouristApplication(final String tourId, final String groupId, final String userId)
			throws InAMQPPartException, VlibTourTourManagementException, IOException, JsonRpcException,
			TimeoutException, InterruptedException, NamingException, URISyntaxException {
			emulationVisitClient  = new VLibTourVisitEmulationClient();
			lobbyRoomClient = new VLibTourLobbyRoomClient();
			String link = lobbyRoomClient.createAGroupAndJoin(groupId, userId);
			if(link.equals("NOT OK")) {
				link = lobbyRoomClient.JoinAGroup(groupId, userId);
			}
			groupCommClient = new VLibTourGroupCommunicationSystemClient(link);
			Consumer consumer1 = new DefaultConsumer(groupCommClient.getchannel()) {
				@Override
				public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, 
						final byte[] body) throws IOException {
					String GPSPosition = new String(body, "UTF-8");
					  
					System.out.println("User "+userId + " [x] Received ' "+ GPSPosition + " '");
					othersPosition(GPSPosition,userId);
				}
			};
			groupCommClient.addConsumer(consumer1, groupCommClient.getQueueName(), groupCommClient.getBindinkKey());
	}

	/**
	 * executes the tourist application. <br>
	 * We prefer insert comments in the method instead of detailing the method here.
	 * 
	 * @param args the command line arguments.
	 * @throws Exception all the potential problems (since this is a demonstrator,
	 *                   apply the strategy "fail fast").
	 */
	public static void main(final String[] args) throws Exception {
		@SuppressWarnings("unused")
		String usage = "USAGE: " + VLibTourVisitTouristApplication.class.getCanonicalName()
				+ " userId (either Joe or Avrel)";
		if (args.length != 1) {
			throw new IllegalArgumentException(usage);
		}
		String userId = args[0];
		String groupId = "1" ;
		client = new VLibTourVisitTouristApplication("The unusual Paris",groupId,userId);
		if (LOG_ON && EMULATION.isInfoEnabled()) {
			EMULATION.info(userId + "'s application is starting");
		}
		// the tour is empty, this client gets it from the data base (first found)
		// TODO
		// start the VLibTourVisitTouristApplication applications
		// TODO
		// set the map viewer of the scenario (if this is this client application that
		// has created the group [see #VLibTourVisitTouristApplication(...)])
		// the following code should be completed
		// FIXME
		String othername;
		if(userId.equals("Joe")) {
			othername="Avrel";
		}else {
			othername="Joe";
		}
		client.map = Optional.of(MapHelper.createMapWithCenterAndZoomLevel(48.851412, 2.343166, 14));
		Font font = new Font("name", Font.BOLD, 20);
		client.map.ifPresent(m -> {
			MapHelper.addMarkerDotOnMap(m, 48.871799, 2.342355, Color.BLACK, font, "Musée Grevin");
			MapHelper.addMarkerDotOnMap(m, 48.860959, 2.335757, Color.BLACK, font, "Pyramide du Louvres");
			MapHelper.addMarkerDotOnMap(m, 48.833566, 2.332416, Color.BLACK, font, "Les catacombes");
			// all the tourists start at the same position
			Position positionOfJoe = client.emulationVisitClient.getCurrentPosition(userId);
			mapDotJoe = MapHelper.addTouristOnMap(m, colorJoe, font, userId,
					positionOfJoe);
			positionOfOther = client.emulationVisitClient.getCurrentPosition(othername);
			mapDotAvrel = MapHelper.addTouristOnMap(m, colorAvrel, font, othername,
					positionOfOther);
			
			client.map.get().repaint();

			// wait for painting the map
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		groupCommClient.startConsumption(groupCommClient.getchannel());

		// start the consumption of messages (e.g. positions of group members) from the group communication system 
		// TODO
		// repainting # approximately 3s => delay only non-leader clients => not all
		// the clients at the same speed
		// TODO
		// loop until the end of the visit is detected
		// FIXME
		while (true) 
			{   
			Position next=client.emulationVisitClient.stepsInVisit(userId);
			Position positionOfJoe = client.emulationVisitClient.stepInCurrentPath(userId);	
			 String message = userId+"_"+positionOfJoe.getGpsPosition().getLongitude()+"_"+positionOfJoe.getGpsPosition().getLatitude();
			 byte[] encryptArray = Base64.getEncoder().encode(message.getBytes());        
		     String encstr = new String(encryptArray,"UTF-8");   
		
			 groupCommClient.Publish(encstr,"*.all");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			MapHelper.moveTouristOnMap(mapDotJoe,positionOfJoe);
			
			MapHelper.moveTouristOnMap(mapDotAvrel,positionOfOther);
			client.map.get().repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(positionOfJoe.getName().equals(next.getName())) {
			System.exit(0);
			}	
			}
		}
	
	public void othersPosition(String messageEncrypted, String userId) {
		byte[] dectryptArray = messageEncrypted.getBytes();
	     byte[] decarray = Base64.getDecoder().decode(dectryptArray);
	    String message="";
	     try {
			message = new String(decarray,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(!message.contains(userId)){
		String name = message.substring(0,message.indexOf("_"));
		double lon = Double.parseDouble(message.substring(message.indexOf("_")+1,message.lastIndexOf("_")));
		double lat = Double.parseDouble(message.substring(message.lastIndexOf("_")+1,message.length()));
		GPSPosition gps = new GPSPosition(lat,lon);
		positionOfOther = new Position(name,gps);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
	}
}

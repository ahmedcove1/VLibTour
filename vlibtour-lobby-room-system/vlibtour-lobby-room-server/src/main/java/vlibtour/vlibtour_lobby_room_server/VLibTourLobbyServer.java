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
package vlibtour.vlibtour_lobby_room_server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.http.client.Client;
import com.rabbitmq.tools.jsonrpc.JsonRpcClient;
import com.rabbitmq.tools.jsonrpc.JsonRpcException;
import com.rabbitmq.tools.jsonrpc.JsonRpcServer;

//import eu.telecomsudparis.rabbitmq.tutorial.FibonacciService;
//import eu.telecomsudparis.rabbitmq.tutorial.RPCServer3;
import vlibtour.vlibtour_lobby_room_api.InAMQPPartException;
import vlibtour.vlibtour_lobby_room_api.VLibTourLobbyService;

/**
 * This class implements the VLibTour lobby room service. This is the entry
 * point of the system for the clients when they want to start a tour.
 * <p>
 * When launched in its own process via a {@code java} command in shell script,
 * there is no call to {@link #close()}: the process is killed in the shell
 * script that starts this process. But, the class is a
 * {@link java.lang.Runnable} so that the lobby room server can be integrated in
 * another process.
 * 
 * @author Denis Conan
 */



public class VLibTourLobbyServer implements Runnable, VLibTourLobbyService {

	private ConnectionFactory factory ;
    private static VLibTourLobbyServer INSTANCE = null;
    
	/**
	 * the connection to the RabbitMQ broker.
	 */
	private Connection connection;
	/**
	 * the channel for consuming and producing.
	 */
	private Channel channel;
	/**
	 * the RabbitMQ JSON RPC server.
	 */
	private JsonRpcServer rpcServer;
	/**
	 * List of groups + their users 
	 */
	private Map<String,ArrayList<String>> groups;
		public static synchronized VLibTourLobbyServer getInstance() throws InAMQPPartException, IOException, TimeoutException, InterruptedException {
			if(INSTANCE == null) {
				INSTANCE = new VLibTourLobbyServer();
			}
			return INSTANCE;
	}
	
	/**
	 * creates the lobby room server and the corresponding JSON server object.
	 * 
	 * @throws InAMQPPartException the exception thrown in case of a problem in the
	 *                             AMQP part.
	 * @throws TimeoutException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	private VLibTourLobbyServer() throws InAMQPPartException, IOException, TimeoutException, InterruptedException {
	/*	try {
			new ProcessBuilder("rabbitmqctl", "stop").inheritIO().start().waitFor();
		} catch (IOException | InterruptedException e) {
		}
		Thread.sleep(1000);
		new ProcessBuilder("rabbitmq-server", "-detached").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "stop_app").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "reset").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "start_app").inheritIO().start().waitFor();
		*/factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, BINDING_KEY);
		rpcServer = new JsonRpcServer(channel, queueName, VLibTourLobbyService.class, this);
		this.groups = new HashMap<String,ArrayList<String>>();
	}
	

	@Override
	public String createGroupAndJoinIt(final String groupId, final String userId) {
		if(groups.containsKey(groupId) == false) {
			String mdp= groupId+"."+userId;
			ArrayList<String> onGroup = new ArrayList<String>();
			onGroup.add(userId);
			groups.put(groupId,onGroup);
			try {
				new ProcessBuilder("rabbitmqctl", "add_vhost", groupId ).inheritIO().start().waitFor();
				new ProcessBuilder("rabbitmqctl", "add_user", userId ,mdp).inheritIO().start().waitFor();
				new ProcessBuilder("rabbitmqctl", "set_permissions", "-p", groupId, userId, ".*", ".*", ".*").inheritIO().start().waitFor();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
			System.out.println("Group "+ userId + " Created ... " );
			return "amqp://"+userId+":"+mdp+"@"+factory.getHost()+":"+factory.getPort()+"/"+groupId;

		}else {
			System.out.println("this group is already exist try to join directly ;)" );
			return "NOT OK"	;
		}
		
	}

	@Override
	public String joinAGroup(final String groupId, final String userId) {
		if(groups.containsKey(groupId)==true) {
		String mdp =groupId+"."+userId;
		ArrayList<String> group = groups.get(groupId);
		group.add(userId);
		try {
			new ProcessBuilder("rabbitmqctl", "add_user", userId ,mdp).inheritIO().start().waitFor();
			new ProcessBuilder("rabbitmqctl", "set_permissions", "-p", groupId, userId, ".*", ".*", ".*").inheritIO().start().waitFor();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}	
		System.out.println("user "+ userId + "has joined the group "+ groupId);
		return "amqp://"+userId+":"+mdp+"@"+factory.getHost()+":"+factory.getPort()+"/"+groupId;
	}else {
		System.out.println("this group doesn't exist anymore");
		return "NOT OK";
	}
		}

	/**
	 * creates the JSON RPC server and enters into the main loop of the JSON RPC
	 * server. The exit to the main loop is done when calling
	 * {@code stopLobbyRoom()} on the administration server. At the end of this
	 * method, the connectivity is closed.
	 */
	@Override
	public void run() {
		try {
			rpcServer.mainloop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * calls for the termination of the main loop if not already done and then
	 * closes the connection and the channel of this server.
	 * 
	 * @throws InAMQPPartException the exception thrown in case of a problem in the
	 *                             AMQP part.
	 * @throws IOException 
	 * @throws TimeoutException 
	 */
	public void close() throws InAMQPPartException, IOException, TimeoutException {	
		if(rpcServer !=null) {
			rpcServer.close();
		}
		if (channel != null) {
			channel.close();
		}
		if (connection != null) {
			connection.close();
		}
		
	}

	/**
	 * starts the lobby server.
	 * <p>
	 * When launched in its own process via a {@code java} command in shell script,
	 * there is no call to {@link #close()}: the process is killed in the shell
	 * script that starts this process.
	 * 
	 * @param args command line arguments
	 * @throws Exception all the potential problems (since this is a demonstrator,
	 *                   apply the strategy "fail fast").
	 */
	public static void main(final String[] args) throws Exception {
		VLibTourLobbyServer rpcServer = new VLibTourLobbyServer();
		rpcServer.run();
	}


	@Override
	public String leaveAGroup(String groupId, String userId) {
		if(groups.containsKey(groupId) == false) {
			return "this groups doesn't exist";
		}else {
			groups.get(groupId).remove(userId);
			return "User "+userId+" is no longer in group " + groupId;
		}
	}
}
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
package vlibtour.vlibtour_client_group_communication_system;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;

/**
 * This class is the client application of the tourists.
 * 
 * @author Denis Conan
 */
public class VLibTourGroupCommunicationSystemClient {
	String gid;
	String tid;
	String userId;
	Connection connection;
	Channel channel;
	String name;
	Consumer consumer;
	String queueName;
	String bindingKey;
	String broadcast;
	VLibTourGroupCommunicationSystemClient(String gid,String tid,String userId,String mdp) throws IOException, TimeoutException{
		this.gid=gid;
		this.tid=tid;
		this.userId=userId;
		ConnectionFactory factory = new ConnectionFactory(); 
		//factory.setHost("localhost");
		String vhost=gid;
		URI link;
		try {
			link = new URI("amqp://" + userId + ":" + mdp + "@" + factory.getHost() + ":" + factory.getPort() + "/" + vhost);
			factory.setUri(link);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		connection = factory.newConnection();
		channel = connection.createChannel();
		this.name = tid;
		this.queueName=userId;
		this.bindingKey="*."+userId+".#";
		this.broadcast="*.all";
		channel.exchangeDeclare(name, BuiltinExchangeType.TOPIC);
		channel.queueDeclare(queueName, false, false, false, null);
		channel.queueBind(queueName, this.name, this.broadcast);	
	}

	public VLibTourGroupCommunicationSystemClient(String url) throws IOException, TimeoutException, URISyntaxException{
		URI link = new URI(url);
		ConnectionFactory factory = new ConnectionFactory();
		try {
			factory.setUri(link);
			this.userId = factory.getUsername();
			this.name = factory.getVirtualHost();
			this.queueName="user" + userId;
			this.bindingKey="*."+userId+".#";
			this.broadcast="*.all";
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.exchangeDeclare(name, BuiltinExchangeType.TOPIC);
			channel.queueDeclare(queueName, false, false, false, null);
			channel.queueBind(queueName, this.name, this.broadcast);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
			
	}
	public void Publish(String message,String routingKey) {
        try {
        	channel.basicPublish(name, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addConsumer(Consumer consumer,String queueName,String bindingKey) {
		try {
			this.channel.queueBind(queueName,this.name,bindingKey);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(final String consumerTag, final Envelope envelope,
					final AMQP.BasicProperties properties, final byte[] body) throws IOException {
				consumer.handleDelivery(consumerTag, envelope, properties, body);
			}
		};
	}
	public void startConsumption(Channel ch) throws IOException {
		ch.basicConsume(queueName, true, consumer );
	
	}
	
    
    public void close() throws IOException, TimeoutException {
    	if(this.channel != null ) {
    		this.channel.close();
    	}
    	if(this.connection != null) {
    		this.connection.close();
    	}
    	
    }
	public String  getExchangeName() {
		return this.name;
	}
	public String getQueueName() {
		return queueName;
	}

	public Channel getchannel() {
		return this.channel;
	}

	public String getBindinkKey() {
		return this.bindingKey;
	}
}

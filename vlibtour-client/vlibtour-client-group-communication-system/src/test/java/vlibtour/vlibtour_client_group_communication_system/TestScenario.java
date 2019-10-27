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
// CHECKSTYLE:OFF
package vlibtour.vlibtour_client_group_communication_system;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.http.client.Client;


public class TestScenario {

	private static Client c;

	@BeforeClass
	public static void setUp() throws IOException, InterruptedException, URISyntaxException {
		try {
			new ProcessBuilder("rabbitmqctl", "stop").inheritIO().start().waitFor();
		} catch (IOException | InterruptedException e) {
		}
		Thread.sleep(1000);
		new ProcessBuilder("rabbitmq-server", "-detached").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "stop_app").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "reset").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "start_app").inheritIO().start().waitFor();
		c = new Client("http://127.0.0.1:15672/api/", "guest", "guest");
	}

	@Test
	public void test() throws IOException, TimeoutException, InterruptedException, ExecutionException {
		new ProcessBuilder("rabbitmqctl", "add_vhost", "1" ).inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "add_vhost", "2" ).inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "add_vhost", "3" ).inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "list_vhosts").inheritIO().start().waitFor();

		
		new ProcessBuilder("rabbitmqctl", "add_user", "1" ,"password1").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "add_user", "2" ,"password2").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "add_user", "3" ,"password3").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "add_user", "4" ,"password4").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "add_user", "5" ,"password5").inheritIO().start().waitFor();
		
		new ProcessBuilder("rabbitmqctl", "list_users").inheritIO().start().waitFor();
		
		new ProcessBuilder("rabbitmqctl", "set_permissions", "-p", "1", "1", ".*", ".*", ".*").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "set_permissions", "-p", "1", "2", ".*", ".*", ".*").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "set_permissions", "-p", "2", "3", ".*", ".*", ".*").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "set_permissions", "-p", "2", "4", ".*", ".*", ".*").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "set_permissions", "-p", "3", "5", ".*", ".*", ".*").inheritIO().start().waitFor();
		
		new ProcessBuilder("rabbitmqctl", "list_permissions", "-p", "1").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "list_permissions", "-p", "2").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "list_permissions", "-p", "3").inheritIO().start().waitFor();
		
		VLibTourGroupCommunicationSystemClient VLibTourGroupCommunicationSystemClient1 = new VLibTourGroupCommunicationSystemClient("1","1","1","password1");
		VLibTourGroupCommunicationSystemClient VLibTourGroupCommunicationSystemClient2 = new VLibTourGroupCommunicationSystemClient("1","1","2","password2");
		VLibTourGroupCommunicationSystemClient VLibTourGroupCommunicationSystemClient3 = new VLibTourGroupCommunicationSystemClient("2","2","3","password3");
		VLibTourGroupCommunicationSystemClient VLibTourGroupCommunicationSystemClient4 = new VLibTourGroupCommunicationSystemClient("2","2","4","password4");
		VLibTourGroupCommunicationSystemClient VLibTourGroupCommunicationSystemClient5 = new VLibTourGroupCommunicationSystemClient("3","2","5","password5");
		
		
		Consumer consumer1 = new DefaultConsumer(VLibTourGroupCommunicationSystemClient1.getchannel()) {
			@Override
			public void handleDelivery(final String consumerTag, final Envelope envelope,
					final AMQP.BasicProperties properties, final byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("User 1 [x] Received ' "+ message + " '");
			}
			};

			Consumer consumer2 = new DefaultConsumer(VLibTourGroupCommunicationSystemClient2.getchannel()) {
				@Override
				public void handleDelivery(final String consumerTag, final Envelope envelope,
						final AMQP.BasicProperties properties, final byte[] body) throws IOException {
					String message = new String(body, "UTF-8");

					System.out.println("User 2 [x] Received ' "+ message + " '");
					}
				};

				Consumer consumer3 = new DefaultConsumer(VLibTourGroupCommunicationSystemClient3.getchannel()) {
					@Override
					public void handleDelivery(final String consumerTag, final Envelope envelope,
							final AMQP.BasicProperties properties, final byte[] body) throws IOException {
						String message = new String(body, "UTF-8");

						System.out.println("User 3 [x] Received ' "+ message + " '");
			
					}
					};

					Consumer consumer4 = new DefaultConsumer(VLibTourGroupCommunicationSystemClient4.getchannel()) {
						@Override
						public void handleDelivery(final String consumerTag, final Envelope envelope,
								final AMQP.BasicProperties properties, final byte[] body) throws IOException {
							String message = new String(body, "UTF-8");

							System.out.println("User 4 [x] Received ' "+ message + " '");
				}
						};

						Consumer consumer5 = new DefaultConsumer(VLibTourGroupCommunicationSystemClient5.getchannel()) {
							@Override
							public void handleDelivery(final String consumerTag, final Envelope envelope,
									final AMQP.BasicProperties properties, final byte[] body) throws IOException {
								String message = new String(body, "UTF-8");

								System.out.println("User 5 [x] Received ' "+ message + " '");
					}
							};
						
		VLibTourGroupCommunicationSystemClient1.addConsumer(consumer1,VLibTourGroupCommunicationSystemClient1.getQueueName() ,VLibTourGroupCommunicationSystemClient1.getBindinkKey() );
		VLibTourGroupCommunicationSystemClient2.addConsumer(consumer2,VLibTourGroupCommunicationSystemClient2.getQueueName() ,VLibTourGroupCommunicationSystemClient2.getBindinkKey() );
		VLibTourGroupCommunicationSystemClient3.addConsumer(consumer3,VLibTourGroupCommunicationSystemClient3.getQueueName() ,VLibTourGroupCommunicationSystemClient3.getBindinkKey());
		VLibTourGroupCommunicationSystemClient4.addConsumer(consumer4,VLibTourGroupCommunicationSystemClient4.getQueueName() ,VLibTourGroupCommunicationSystemClient4.getBindinkKey());
		VLibTourGroupCommunicationSystemClient5.addConsumer(consumer5,VLibTourGroupCommunicationSystemClient5.getQueueName() ,VLibTourGroupCommunicationSystemClient5.getBindinkKey());
		
		Thread.sleep(2000);
		

		VLibTourGroupCommunicationSystemClient1.Publish("messge from 1", "1.1.2");
		VLibTourGroupCommunicationSystemClient1.Publish("message from 1 to all", "*.all");
		VLibTourGroupCommunicationSystemClient3.Publish("message from 3 to all ", "*.all");
		VLibTourGroupCommunicationSystemClient2.Publish("message from 2 to 1", "1.1.#");
		VLibTourGroupCommunicationSystemClient4.Publish("message from 4 to 3", "1.3");
		VLibTourGroupCommunicationSystemClient5.Publish("message from 5", "1.5");

		VLibTourGroupCommunicationSystemClient1.startConsumption(VLibTourGroupCommunicationSystemClient1.getchannel());
		VLibTourGroupCommunicationSystemClient2.startConsumption(VLibTourGroupCommunicationSystemClient2.getchannel());
		VLibTourGroupCommunicationSystemClient3.startConsumption(VLibTourGroupCommunicationSystemClient3.getchannel());
		VLibTourGroupCommunicationSystemClient4.startConsumption(VLibTourGroupCommunicationSystemClient4.getchannel());
		VLibTourGroupCommunicationSystemClient5.startConsumption(VLibTourGroupCommunicationSystemClient5.getchannel());
		
		
		
		Thread.sleep(5000);
		VLibTourGroupCommunicationSystemClient1.close();
		VLibTourGroupCommunicationSystemClient2.close();
		VLibTourGroupCommunicationSystemClient3.close();
		VLibTourGroupCommunicationSystemClient4.close();
		VLibTourGroupCommunicationSystemClient5.close();
	}

	@AfterClass
	public static void tearDown() throws InterruptedException, IOException {
		new ProcessBuilder("rabbitmqctl", "stop_app").inheritIO().start().waitFor();
		new ProcessBuilder("rabbitmqctl", "stop").inheritIO().start().waitFor();
	}
}

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

Initial developer(s): Denis Conan and Chantal Taconet
Contributor(s):
 */
package vlibtour.vlibtour_emulation_visit;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import vlibtour.vlibtour_visit_emulation.ExampleOfAVisitWithTwoTourists;
import vlibtour.vlibtour_visit_emulation.Position;

/**
 * A REST client test example.
 * <p>
 * This class is not necessary; it is provided with the
 * "{@code mvn exec:java@client}" configuration in order to test the server in
 * command line.
 */
public final class VisitEmulationTestClient {
	/**
	 * utility class with no instance.
	 */
	private VisitEmulationTestClient() {
	}

	/**
	 * the main method.
	 * 
	 * @param args there is no command line arguments.
	 * @throws IOException communication problem.
	 */
	public static void main(final String[] args) throws IOException {
		Client client = ClientBuilder.newClient();
		URI uri = UriBuilder.fromUri(ExampleOfAVisitWithTwoTourists.BASE_URI_WEB_SERVER).build();
		WebTarget service = client.target(uri);
		try {
			WebTarget target = service
					.path("visitemulation/getNextPOIPosition/" + ExampleOfAVisitWithTwoTourists.USER_ID_JOE);
			System.out.println(target.toString());
			Response jsonResponse = service
					.path("visitemulation/getNextPOIPosition/" + ExampleOfAVisitWithTwoTourists.USER_ID_JOE).request()
					.accept(MediaType.APPLICATION_JSON).get();
			Position position = jsonResponse.readEntity(Position.class);
			System.out.println(position);
		} catch (NotFoundException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}

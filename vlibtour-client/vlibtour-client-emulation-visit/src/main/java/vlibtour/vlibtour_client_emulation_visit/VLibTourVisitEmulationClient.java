/**
This file is part of the course CSC5002.

Copyright (C) 2019 Télécom SudParis

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

Initial developer(s): Chantal Taconet and Denis Conan
Contributor(s):
 */
package vlibtour.vlibtour_client_emulation_visit;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import vlibtour.vlibtour_visit_emulation.ExampleOfAVisitWithTwoTourists;
import vlibtour.vlibtour_visit_emulation.Position;

/**
 * The REST client of the VLibTour Emulation Visit Server.
 */
public final class VLibTourVisitEmulationClient {
	private static WebTarget service;
		 
		public VLibTourVisitEmulationClient(){
			Client client = ClientBuilder.newClient();
			URI uri = UriBuilder.fromUri(ExampleOfAVisitWithTwoTourists.BASE_URI_WEB_SERVER).build();
			this.service = client.target(uri);
		}
		public Position getNextPOIPosition(String ExampleOfAVisit) {
			Position position=service
					.path("visitemulation/getNextPOIPosition/" + ExampleOfAVisit).request()
					.accept(MediaType.APPLICATION_JSON).get().readEntity(Position.class);
			return position;
		}
		public Position getCurrentPosition(String ExempleOfAvisit) {
			Position position=service.path("visitemulation/getCurrentPosition/"+ ExempleOfAvisit).request()
					.accept(MediaType.APPLICATION_JSON).get().readEntity(Position.class);
			return position;
		}
		public Position stepInCurrentPath(String ExempleOfAvisit) {
			Position position=service.path("visitemulation/stepInCurrentPath/"+ ExempleOfAvisit).request()
					.accept(MediaType.APPLICATION_JSON).get().readEntity(Position.class);
			return position;
		}
		public Position stepsInVisit(String E) {
			Position position=service.path("visitemulation/stepsInVisit/"+ E).request()
					.accept(MediaType.APPLICATION_JSON).get().readEntity(Position.class);
			return position;
		}
	
}

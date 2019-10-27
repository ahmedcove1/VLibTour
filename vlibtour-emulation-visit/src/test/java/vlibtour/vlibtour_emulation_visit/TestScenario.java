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
Contributor(s):
 */
package vlibtour.vlibtour_emulation_visit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import vlibtour.vlibtour_visit_emulation.ExampleOfAVisitWithTwoTourists;
import vlibtour.vlibtour_visit_emulation.Position;

public class TestScenario {
	private static Process process;
	private static WebTarget service;

	@BeforeClass
	public static void setUp() throws IOException, InterruptedException, URISyntaxException {
		Runtime r = Runtime.getRuntime();
		Process wc = r.exec("netstat -nlp");
		wc.waitFor();
		BufferedReader b = new BufferedReader(new InputStreamReader(wc.getInputStream()));
		String line = "";
		while ((line = b.readLine()) != null) {
			if (line.contains("127.0.0.1:8888")) {
				String sub = line.substring(StringUtils.lastIndexOfAny(line, "LISTEN") + "LISTEN".length()).trim();
				String p = sub.substring(0, sub.indexOf("/"));
				System.out.println("Warning: Already a REST server on address 127.0.0.1:8888 => kill -9 " + p);
				r.exec("kill -9 " + p);
			}
		}
		b.close();
		Thread.sleep(1000);
		process = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"),
				"vlibtour.vlibtour_emulation_visit.VisitEmulationServer").inheritIO().start();
		Thread.sleep(1000);
		Client client = ClientBuilder.newClient();
		URI uri = UriBuilder.fromUri(ExampleOfAVisitWithTwoTourists.BASE_URI_WEB_SERVER).build();
		service = client.target(uri);
	}

	@Test
	public void testGetNextPOIPosition() throws IOException {
		Response jsonResponse = service
				.path("visitemulation/getNextPOIPosition/" + ExampleOfAVisitWithTwoTourists.USER_ID_JOE).request()
				.accept(MediaType.APPLICATION_JSON).get();
		Assert.assertNotNull(jsonResponse);
		Assert.assertNotNull(jsonResponse.readEntity(Position.class));
	}

	@Test
	public void testGetCurrentPosition() throws IOException {
		Response jsonResponse = service
				.path("visitemulation/getCurrentPosition/" + ExampleOfAVisitWithTwoTourists.USER_ID_JOE).request()
				.accept(MediaType.APPLICATION_JSON).get();
		Assert.assertNotNull(jsonResponse);
		Assert.assertNotNull(jsonResponse.readEntity(Position.class));
	}

	@Test
	public void testStepInCurrentPath() throws IOException {
		Response jsonResponse = service
				.path("visitemulation/stepInCurrentPath/" + ExampleOfAVisitWithTwoTourists.USER_ID_JOE).request()
				.accept(MediaType.APPLICATION_JSON).get();
		Assert.assertNotNull(jsonResponse);
		Assert.assertNotNull(jsonResponse.readEntity(Position.class));
	}

	@Test
	public void testStepsInVisit() throws IOException {
		Response jsonResponse = service
				.path("visitemulation/stepInCurrentPath/" + ExampleOfAVisitWithTwoTourists.USER_ID_JOE).request()
				.accept(MediaType.APPLICATION_JSON).get();
		Assert.assertNotNull(jsonResponse);
		Assert.assertNotNull(jsonResponse.readEntity(Position.class));
	}

	@AfterClass
	public static void tearDown() throws InterruptedException, IOException {
		if (process != null) {
			process.destroyForcibly();
		}
	}
}

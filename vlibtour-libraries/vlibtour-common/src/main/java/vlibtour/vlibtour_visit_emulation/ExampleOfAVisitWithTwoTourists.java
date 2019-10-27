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
Contributor(s): Chantal Taconet
 */
package vlibtour.vlibtour_visit_emulation;

/**
 * The class defines an example of a visit with two tourists.
 * 
 * @author Denis Conan
 */
public final class ExampleOfAVisitWithTwoTourists {
	/**
	 * the number of users/tourists in the scenario.
	 */
	public static final int NB_TOURISTS = 2;

	/**
	 * the user identifier of the first tourist.
	 */
	public static final String USER_ID_JOE = "Joe";

	/**
	 * the user identifier of the second tourist.
	 */
	public static final String USER_ID_AVREL = "Avrel";

	/**
	 * the base URI of the Grizzly HTTP server will listen on.
	 */
	public static final String BASE_URI_WEB_SERVER = "http://localhost:8888/VisitEmulation/";
	
	/**
	 * private constructor of the utility class.
	 */
	private ExampleOfAVisitWithTwoTourists() {
	}
}

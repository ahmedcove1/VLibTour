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
package vlibtour.vlibtour_lobby_room_api;

/**
 * The exception thrown in case of problem in the use of AMQP entities.
 * 
 * @author Denis Conan
 */
public class InAMQPPartException extends Exception {

	/**
	 * the version number.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * creates an exception object with a message.
	 * 
	 * @param message
	 *            the message.
	 */
	public InAMQPPartException(final String message) {
		super(message);
	}
}

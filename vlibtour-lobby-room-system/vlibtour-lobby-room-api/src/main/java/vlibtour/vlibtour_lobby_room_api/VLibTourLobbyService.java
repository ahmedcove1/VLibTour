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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;

/**
 * This interface defines the service of the lobby room. A server implementing
 * this service is controlled by a lobby room administration server; this
 * explains why there is no method for stopping the lobby room.
 * 
 * @author Denis Conan
 */
public interface VLibTourLobbyService {
	/**
	 * the delimiter used to compute the group identifier by concatenating a tour
	 * identifier, this delimiter, and the user identifier.
	 */
	String GROUP_TOUR_USER_DELIMITER = "_";
	/**
	 * the name of the exchange for sending message to the lobby server. This
	 * information is shared with clients.
	 */
	String EXCHANGE_NAME = "vlib-tour-lobby";
	/**
	 * the binding key for sending message to the lobby server. This information is
	 * shared with clients (for their routing key).
	 */
	String BINDING_KEY = "lobby";

	/**
	 * creates a group for a tour instance and joins it. The method returns an URL,
	 * which is used to connect to the infrastructure for group communication. The
	 * URL contains the log-in and the password of the user and should not be used
	 * by the other members of the group.
	 * 
	 * Assumption/limitation: there is no access control and if the user already
	 * exists then this is not the first connection to the system.
	 * 
	 * Assumption/limitation: the computation of the group identifier assumes that
	 * the user creates a group for this tour once.
	 * 
	 * TODO : when the service is not provided, the error message is returned in the
	 * URL.
	 * 
	 * @param groupId
	 *            the group identifier.
	 * @param userId
	 *            the user identifier.
	 * @return the URL to the group communication system for that user to this
	 *         group.
	 */
	String createGroupAndJoinIt(String groupId, String userId);

	/**
	 * joins a group for a tour instance. The group identifier should be computed
	 * from the URL provided by the call to
	 * {@link #createGroupAndJoinIt(String, String)} using the method.
	 * 
	 * @param groupId
	 *            the group identifier.
	 * @param userId
	 *            the user identifier.
	 * @return the URL to the group communication system for that user to this
	 *         group.
	 */
	String joinAGroup(String groupId, String userId);
	String leaveAGroup(String groupId,String userId);
	/**
	 * computes the group identifier from the URL obtained by the call to
	 * {@link #createGroupAndJoinIt(String, String)}.
	 * 
	 * @param url
	 *            the URL to decode
	 * @return the group identifier.
	 * @throws UnsupportedEncodingException
	 *             the set of characters used for encoding the URL is not supported.
	 * @throws URISyntaxException
	 *             the URL is malformed.
	 */
	static String computeGroupId(String url) throws UnsupportedEncodingException, URISyntaxException {
		// URLDecoder decodes '+' to a space, as for form encoding. So protect plus
		// signs.
		// See {@link com.rabbitmq.client.ConnectionFactory#uriDecode(String)}
		return URLDecoder.decode(new URI(url).getPath().substring(1).replace("+", "%2B"), "US-ASCII");
	}
}

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
package vlibtour.vlibtour_visit_emulation;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * This class contains the configuration of some logging facilities.
 * 
 * To recapitulate, logging levels are: TRACE, DEBUG, INFO, WARN, ERROR, FATAL.
 * 
 * @author Denis Conan
 * 
 */
public final class Log {
	/**
	 * states whether logging is enabled or not.
	 */
	public static final boolean LOG_ON = true;
	/**
	 * name of logger for the emulation of a visit / the scenario.
	 */
	public static final String LOGGER_NAME_EMULATION = "emulation";
	/**
	 * logger object for the emulation part.
	 */
	public static final Logger EMULATION = Logger.getLogger(LOGGER_NAME_EMULATION);

	/**
	 * static configuration, which can be changed by command line options.
	 */
	static {
		BasicConfigurator.configure();
		EMULATION.setLevel(Level.INFO);
	}

	/**
	 * private constructor to avoid instantiation.
	 */
	private Log() {
	}

	/**
	 * configures a logger to a level.
	 * 
	 * @param loggerName
	 *            the name of the logger.
	 * @param level
	 *            the level.
	 */
	public static void configureALogger(final String loggerName,
			final Level level) {
		if (loggerName == null) {
			return;
		}
		if (loggerName.equalsIgnoreCase(LOGGER_NAME_EMULATION)) {
			EMULATION.setLevel(level);
		}
	}
}

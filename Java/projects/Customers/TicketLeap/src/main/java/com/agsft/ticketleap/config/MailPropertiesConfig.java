package com.agsft.ticketleap.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * @author shekhar
 *
 */
@Component
@PropertySource("classpath:mail.properties")
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "mail")
public class MailPropertiesConfig {

	/**
	 * The Class Smtp.
	 */
	public static class Smtp {

		/** The auth. */
		private boolean auth;

		/** The starttls enable. */
		private boolean starttlsEnable;

		/**
		 * Checks if is auth.
		 *
		 * @return true, if is auth
		 */
		public boolean isAuth() {
			return auth;
		}

		/**
		 * Sets the auth.
		 *
		 * @param auth
		 *            the new auth
		 */
		public void setAuth(boolean auth) {
			this.auth = auth;
		}

		/**
		 * Checks if is starttls enable.
		 *
		 * @return true, if is starttls enable
		 */
		public boolean isStarttlsEnable() {
			return starttlsEnable;
		}

		/**
		 * Sets the starttls enable.
		 *
		 * @param starttlsEnable
		 *            the new starttls enable
		 */
		public void setStarttlsEnable(boolean starttlsEnable) {
			this.starttlsEnable = starttlsEnable;
		}

	}

	/** The host. */
	private String host;

	/** The port. */
	private int port;

	/** The from. */
	private String from;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The smtp. */
	private Smtp smtp;

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the host.
	 *
	 * @param host
	 *            the new host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @param port
	 *            the new port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Gets the from.
	 *
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Sets the from.
	 *
	 * @param from
	 *            the new from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the smtp.
	 *
	 * @return the smtp
	 */
	public Smtp getSmtp() {
		return smtp;
	}

	/**
	 * Sets the smtp.
	 *
	 * @param smtp
	 *            the new smtp
	 */
	public void setSmtp(Smtp smtp) {
		this.smtp = smtp;
	}
}

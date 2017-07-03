package org.alan.ml.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.alan.ml.services.ConfigService;

@WebListener
public class SessionControlListener implements HttpSessionListener {

	private static final int sessionTimeoutInSecond = ConfigService.getConfig().getInt("web.sessions.timeoutInSecond");
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(sessionTimeoutInSecond); // in seconds
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

	}
}
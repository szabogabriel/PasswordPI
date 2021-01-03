package pi.password.service.webserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Optional;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import pi.password.service.dialog.DialogService;
import pi.password.service.util.SystemUtil;

public class WebserverServiceImpl implements WebserverService {
	
	private final DialogService DIALOG_SERVICE;
	private final SystemUtil SYSTEM_UTIL;
	
	private HttpServer server;
	private int port = 0;
	
	private boolean running = false;
	
	public WebserverServiceImpl(DialogService dialogService, SystemUtil sysUtil) {
		this.DIALOG_SERVICE = dialogService;
		this.SYSTEM_UTIL = sysUtil;
	}

	@Override
	public void startWebserver() {
		Optional<String> ipAddress = SYSTEM_UTIL.getIpAddress();
		if (!running && ipAddress.isPresent() && port > 0) {
			try {
				InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(ipAddress.get()), port);
				
				server = HttpServer.create(socketAddress, port);

				server.createContext("/index", this::handleIndex);
				server.createContext("/", this::unsupportedRequest);
				
				server.start();
				running = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stopWebserver() {
		if (running) {
			server.stop(1);
			running = false;
		}
	}

	@Override
	public void setPort(int port) {
		if (port > 0 && port < 65536) {
			this.port = port;
	
			if (running) {
				stopWebserver();
				startWebserver();
			}
		}
	}
	
	private void unsupportedRequest(HttpExchange exchange) throws IOException {
		String response = "noooo";
		exchange.sendResponseHeaders(404, response.length());
		exchange.getResponseBody().write(response.getBytes());
		exchange.getResponseBody().close();
	}
	
	private void handleIndex(HttpExchange exchange) throws IOException {
		if (DIALOG_SERVICE.showYesNoDialog("Accept incoming web request?")) {
			String response = "yesss";
			exchange.sendResponseHeaders(200, response.length());
			exchange.getResponseBody().write(response.getBytes());
			exchange.getResponseBody().close();
		} else {
			unsupportedRequest(exchange);
		}
	}

}

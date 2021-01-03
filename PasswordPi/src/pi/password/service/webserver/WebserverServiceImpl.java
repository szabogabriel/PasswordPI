package pi.password.service.webserver;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.jdi.ConfigService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import pi.password.config.RuntimeConfig;
import pi.password.enums.Templates;
import pi.password.service.dialog.DialogService;
import pi.password.service.template.TemplateService;
import pi.password.service.util.SystemUtil;

public class WebserverServiceImpl implements WebserverService {

	private final static String PATH_RESOURCE = "/res";
	private final static String PATH_INDEX = "/index";

	private final DialogService DIALOG_SERVICE;
	private final TemplateService TEMPLATE_SERVICE;
	private final SystemUtil SYSTEM_UTIL;
	private final ConfigService CONFIG;

	private final Map<String, byte[]> RESOURCE_CONTENT = new HashMap<>();
	private final Map<String, String> RESOURCE_MIME_TYPE = new HashMap<>();

	private HttpServer server;
	private int port = 0;
	private boolean strict = false;

	private boolean running = false;

	public WebserverServiceImpl(DialogService dialogService, TemplateService templateService, SystemUtil sysUtil, ConfigService config) {
		this.DIALOG_SERVICE = dialogService;
		this.TEMPLATE_SERVICE = templateService;
		this.SYSTEM_UTIL = sysUtil;
		this.CONFIG = config;
	}

	@Override
	public void startWebserver() {
		Optional<String> ipAddress = SYSTEM_UTIL.getIpAddress();
		if (!running && ipAddress.isPresent() && port > 0) {
			try {
				InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(ipAddress.get()), port);

				server = HttpServer.create(socketAddress, port);

				server.createContext(PATH_RESOURCE, this::resourceHandler);
				server.createContext(PATH_INDEX, this::handleIndex);
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
	
	@Override
	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	private void resourceHandler(HttpExchange exchange) throws IOException {
		String url = exchange.getRequestURI().toString();
		if (url.startsWith(PATH_RESOURCE) && !url.contains("..")) {
			exchange.getResponseHeaders().set("Content-Type", getResourceType(url));
			sendResponse(exchange, 200, getResource(url));
		} else {
			unsupportedRequest(exchange);
		}
	}
	
	private void unsupportedRequest(HttpExchange exchange) throws IOException {
		readInput(exchange);
		String response = render(Templates.ERROR, getBaseData());
		exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
		sendResponse(exchange, 404, response.getBytes());
	}

	private void handleIndex(HttpExchange exchange) throws IOException {
		if (strict) {
			if (!DIALOG_SERVICE.showYesNoDialog("Accept incoming web request?")) {
				unsupportedRequest(exchange);
				return;
			}
		}
		
		readInput(exchange);
		String response = render(Templates.INDEX, getBaseData());
		exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
		sendResponse(exchange, 200, response.getBytes());
	}

	private String render(Templates template, Map<String, Object> data) {
		return TEMPLATE_SERVICE.render(template, data);
	}

	private String readInput(HttpExchange exchange) throws IOException {
		StringBuilder ret = new StringBuilder();

		byte[] buffer = new byte[1024];
		int read;

		if (exchange.getRequestBody().available() > 0) {
			while ((read = exchange.getRequestBody().read(buffer)) > 0) {
				ret.append(new String(buffer, 0, read));
			}
		}

		return ret.toString();
	}

	private void sendResponse(HttpExchange exchange, int code, byte[] response) throws IOException {
		exchange.sendResponseHeaders(code, response.length);
		exchange.getResponseBody().write(response);
		exchange.getResponseBody().close();
	}

	private byte[] getResource(String resource) {
		if (!RESOURCE_CONTENT.containsKey(resource)) {
			try {
				RESOURCE_CONTENT.put(resource, Files.readAllBytes(resourceToFile(resource).toPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return RESOURCE_CONTENT.get(resource);
	}

	private String getResourceType(String resource) {
		if (!RESOURCE_MIME_TYPE.containsKey(resource)) {
			try {
				RESOURCE_MIME_TYPE.put(resource, Files.probeContentType(resourceToFile(resource).toPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return RESOURCE_MIME_TYPE.get(resource);
	}
	
	private File resourceToFile(String resource) {
		return new File(CONFIG.get(RuntimeConfig.WEB_DIRECTORY.getKey()).get() + resource);
	}
	
	private Map<String, Object> getBaseData() {
		Map<String, Object> ret = new HashMap<>();
		
		ret.put("string.menu.pageName", "Password PI");
		
		return ret;
	}
}

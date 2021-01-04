package pi.password.service.webserver;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jdi.ConfigService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import pi.password.config.RuntimeConfig;
import pi.password.entity.SettingsEntity;
import pi.password.entity.SettingsEntity.Keys;
import pi.password.enums.Templates;
import pi.password.service.dialog.DialogService;
import pi.password.service.password.PasswordVaultService;
import pi.password.service.settings.SettingsService;
import pi.password.service.template.TemplateService;
import pi.password.service.util.SystemUtil;

public class WebserverServiceImpl implements WebserverService {

	private final static String PATH_RESOURCE = "/res";
	private final static String PATH_INDEX = "/index";
	private final static String PATH_PASSWORDS = "/passwords";
	private final static String PATH_SETTINGS = "/settings";

	private final DialogService DIALOG_SERVICE;
	private final TemplateService TEMPLATE_SERVICE;
	private final SystemUtil SYSTEM_UTIL;
	private final ConfigService CONFIG;
	private final PasswordVaultService PASSWORD_SERVICE;
	private final SettingsService SETTINGS_SERVICE;

	private final Map<String, byte[]> RESOURCE_CONTENT = new HashMap<>();
	private final Map<String, String> RESOURCE_MIME_TYPE = new HashMap<>();

	private HttpServer server;
	private int port = 0;
	private boolean strict = false;

	private boolean running = false;

	public WebserverServiceImpl(DialogService dialogService, TemplateService templateService,
			PasswordVaultService passwordService, SystemUtil sysUtil, ConfigService config, SettingsService settingsService) {
		this.DIALOG_SERVICE = dialogService;
		this.TEMPLATE_SERVICE = templateService;
		this.PASSWORD_SERVICE = passwordService;
		this.SYSTEM_UTIL = sysUtil;
		this.CONFIG = config;
		this.SETTINGS_SERVICE = settingsService;
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
				server.createContext(PATH_PASSWORDS, this::handlePasswords);
				server.createContext(PATH_SETTINGS, this::handleSettings);
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
		if (progressWebRequest()) {
			Map<String, Object> data = getBaseData();
			String response = render(Templates.INDEX, data);
			exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
			sendResponse(exchange, 200, response.getBytes());
		} else {
			unsupportedRequest(exchange);
		}
	}

	private void handlePasswords(HttpExchange exchange) throws IOException {
		if (progressWebRequest()) {
			Map<String, Object> responseData = getBaseData();
			
			if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
				Map<String, String> data = readInput(exchange);
				
				String account = data.get("account");
				String p1, p2;
				
				switch (data.get("operation")) {
				case "update":
					p1 = data.get("psswd1");
					p2 = data.get("psswd2");
					if (p1 != null && p1.equals(p2)) {
						if (DIALOG_SERVICE.showYesNoDialog("Change password for '" + account + "'?")) {
							PASSWORD_SERVICE.update(account, p1);
							responseData.put("alert.message.present", Boolean.TRUE);
							responseData.put("alert.message.header", "Changed!");
							responseData.put("alert.message.content", "The password for the account " + account + " was changed successfully.");
						} else {
							responseData.put("error.message.present", Boolean.TRUE);
							responseData.put("error.message.header", "Declined!");
							responseData.put("error.message.content", "Change of the password for the account " + account + " was declined.");
						}
					}
					break;
				case "delete":
					if (DIALOG_SERVICE.showYesNoDialog("Remove account '" + account + "'?")) {
						PASSWORD_SERVICE.remove(account);
						responseData.put("alert.message.present", Boolean.TRUE);
						responseData.put("alert.message.header", "Deleted!");
						responseData.put("alert.message.content", "The account " + account + " was deleted successfully.");
					} else {
						responseData.put("error.message.present", Boolean.TRUE);
						responseData.put("error.message.header", "Declined!");
						responseData.put("error.message.content", "Removal of the account " + account + " was declined.");
					}
					break;
				case "add":
					p1 = data.get("psswd1");
					p2 = data.get("psswd2");
					if (p1 != null && p1.equals(p2)) {
						if (DIALOG_SERVICE.showYesNoDialog("Add new account'" + account + "'?")) {
							boolean success = PASSWORD_SERVICE.add(account, p1);
							if (success) {
								responseData.put("alert.message.present", Boolean.TRUE);
								responseData.put("alert.message.header", "Changed!");
								responseData.put("alert.message.content", "The account " + account + " was created successfully.");
							} else {
								responseData.put("error.message.present", Boolean.TRUE);
								responseData.put("error.message.header", "Error!");
								responseData.put("error.message.content", "Error when creating account " + account + ".");
							}
						} else {
							responseData.put("error.message.present", Boolean.TRUE);
							responseData.put("error.message.header", "Declined!");
							responseData.put("error.message.content", "Creation of the account " + account + " was declined.");
						}
					}
					break;
				}
			}
			
			responseData.put("activePasswords", Boolean.TRUE);
			responseData.put("passwords", PASSWORD_SERVICE.listPasswordEntities()
					.stream()
					.sorted((k1, k2) -> k1.getName().compareTo(k2.getName()))
					.map(e -> {
						Map<String, Object> tmpData = new HashMap<>();
						tmpData.put("password", e.getName());
						return tmpData;
					})
					.collect(Collectors.toList())
			);
	
			String response = render(Templates.PASSWORDS, responseData);
			exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
			sendResponse(exchange, 200, response.getBytes());
		} else {
			unsupportedRequest(exchange);
		}
	}

	private void handleSettings(HttpExchange exchange) throws IOException {
		if (progressWebRequest()) {
			Map<String, Object> responseData = getBaseData();
			
			if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
				Map<String, String> data = readInput(exchange);
				
				String setting = data.get("setting");
				String value = data.get("value");
				
				if (setting != null && value != null) {
					if (DIALOG_SERVICE.showYesNoDialog("Update config '" + setting + "' to '" + value + "'?")) {
						Keys.getKey(setting).ifPresent(k -> SETTINGS_SERVICE.setValue(new SettingsEntity(k, value)));
						responseData.put("alert.message.present", Boolean.TRUE);
						responseData.put("alert.message.header", "Changed!");
						responseData.put("alert.message.content", "The configuration value for " + setting + " was changed successfully.");
					} else {
						responseData.put("error.message.present", Boolean.TRUE);
						responseData.put("error.message.header", "Error!");
						responseData.put("error.message.content", "Update of the configuration value " + setting + " = " + value + " was cancelled.");
					}
				} else {
					responseData.put("error.message.present", Boolean.TRUE);
					responseData.put("error.message.header", "Error!");
					responseData.put("error.message.content", "Error when updating of the configuration value " + setting + " = " + value + ".");
				}
			}
			
			responseData.put("activeSettings", Boolean.TRUE);
			responseData.put("settings", SETTINGS_SERVICE.getSettings()
					.stream()
					.sorted((s1, s2) -> s1.getKey().ordinal() - s2.getKey().ordinal())
					.map(s -> {
						Map<String, Object> tmp = new HashMap<>();
						tmp.put("setting", s.getKey().name());
						tmp.put("value", s.getValue());
						return tmp;
					})
					.collect(Collectors.toList())
					);
			String response = render(Templates.SETTINGS, responseData);
			exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
			sendResponse(exchange, 200, response.getBytes());
		} else {
			unsupportedRequest(exchange);
		}
	}

	private boolean progressWebRequest() {
		if (strict) {
			if (!DIALOG_SERVICE.showYesNoDialog("Accept incoming web request?")) {
				return false;
			}
		}
		return true;
	}

	private String render(Templates template, Map<String, Object> data) {
		return TEMPLATE_SERVICE.render(template, data);
	}

	private Map<String, String> readInput(HttpExchange exchange) throws IOException {
		StringBuilder request = new StringBuilder();
		
		byte[] buffer = new byte[1024];
		int read;

		while ((read = exchange.getRequestBody().read(buffer)) > 0) {
			request.append(new String(buffer, 0, read));
		}
		
		Map<String, String> ret = new HashMap<>();
		Arrays.asList(request.toString().split("&"))
			.stream()
			.map(e -> e.split("="))
			.forEach(e -> ret.put(e[0], e[1]));
		
		return ret;
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

package pi.password.service.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import com.jdi.ConfigService;

import pi.password.config.RuntimeConfig;
import pi.password.entity.SettingsEntity;
import pi.password.service.webserver.WebserverService;

public class SettingsServiceFilesystem extends SettingsService {

	private File settings;
	
	private Properties props = new Properties();
	
	public SettingsServiceFilesystem(ConfigService configService, WebserverService webserverService) {
		super(webserverService);
		String workingDirectory = configService.get(RuntimeConfig.WORKING_DIRECTORY.getKey()).orElse(".");
		settings = new File(workingDirectory + "/settings.properties");
		
		loadSettings();
		
		init();
	}
	
	private void loadSettings() {
		try (InputStream in = new FileInputStream(settings)) {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveSettings() {
		try (OutputStream out = new FileOutputStream(settings)) {
			props.store(out, "Created by the " + this.getClass().getCanonicalName() + " class.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Set<SettingsEntity> getSettings() {
		return props.keySet()
				.stream()
				.map(k -> k.toString())
				.map(this::getSetting)
				.filter(e -> e.isPresent())
				.map(e -> e.get())
				.collect(Collectors.toSet());
	}

	@Override
	public Optional<SettingsEntity> getSetting(String key) {
		Optional<SettingsEntity> ret = Optional.empty();
		
		Optional<SettingsEntity.Keys> keyInstance = SettingsEntity.Keys.getKey(key);
		if (props.containsKey(key) && keyInstance.isPresent()) {
			String value = props.getProperty(key).toString();
			ret = Optional.of(new SettingsEntity(keyInstance.get(), value));
		}
		
		return ret;
	}

	@Override
	public void setValue(SettingsEntity setting) {
		if (setting.getKey() != null && setting.getValue() != null) {
			props.put(setting.getKey().name(), setting.getValue());
			saveSettings();
			handleValueChange(setting);
		}
	}

}

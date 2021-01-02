package pi.password.config;

import java.util.Optional;

import com.jdi.ConfigService;

public class JdiConfigService implements ConfigService {

	@Override
	public Optional<String> get(String key) {
		return RuntimeConfig.get(key);
	}

}

package pi.password.service.util;

import java.io.IOException;
import java.util.Optional;

import com.pi4j.system.NetworkInfo;

public class SystemUtil {
	
	public Optional<String> getIpAddress() {
		StringBuilder ret = new StringBuilder();
        try {
			for (String ipAddress : NetworkInfo.getIPAddresses()) {
			    if (!ipAddress.contains("127.0.0.1") && !ipAddress.contains(":")) {
			    	ret.append(ipAddress);
			    }
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
        String retString = ret.toString();
        if (retString.length() < 2) {
        	return Optional.empty();
        } else {
        	return Optional.of(retString);
        }
	}

}

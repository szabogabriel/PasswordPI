package pi.password.service.webserver;

public interface WebserverService {
	
	void startWebserver();
	
	void stopWebserver();
	
	void setPort(int port);
	
	void setStrict(boolean strict);

}

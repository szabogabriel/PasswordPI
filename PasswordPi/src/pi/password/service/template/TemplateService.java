package pi.password.service.template;

import java.util.Map;

import pi.password.enums.Templates;

public interface TemplateService {
	
	String render(Templates template, Map<String, Object> data);

}

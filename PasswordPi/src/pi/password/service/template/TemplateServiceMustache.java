package pi.password.service.template;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.jmtemplate.Template;

import pi.password.enums.Templates;

public class TemplateServiceMustache implements TemplateService {
	
	private final Map<Templates, Template> TEMPLATES = new HashMap<>();
	
	private File templateFolder = null;
	
	public TemplateServiceMustache() {
	}

	@Override
	public String render(Templates template, Map<String, Object> data) {
		Template templ = getTemplate(template);
		String ret = templ.render(data);
		return ret;
	}
	
	private File getTemplateFolder() {
		if (templateFolder == null) {
			templateFolder = new File("./web/templates");//TODO - change to access via configuration
		}
		return templateFolder;
	}

	private Template getTemplate(Templates template) {
		if (!TEMPLATES.containsKey(template)) {
			Template tmp = new Template(getTemplateFolder(), templateToFileName(template), false);
			TEMPLATES.put(template, tmp);
		}
		
		return TEMPLATES.get(template);
	}
	
	private String templateToFileName(Templates template) {
		String ret = "";
		switch (template) {
		case ERROR:
			ret = "error.template";
			break;
		case INDEX:
			ret = "page.template";
			break;
		case PASSWORDS:
			ret = "passwords.template";
			break;
		case SETTINGS:
			ret = "settings.template";
			break;
		default:
			break;
		}
		return ret;
	}
}

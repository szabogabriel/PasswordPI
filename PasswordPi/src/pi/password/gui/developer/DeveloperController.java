package pi.password.gui.developer;

import java.util.stream.Collectors;

import pi.password.Main;
import pi.password.enums.LocalizedTexts;
import pi.password.gui.AbstractController;
import pi.password.gui.components.list.ListModel;
import pi.password.gui.components.list.ListView;
import pi.password.gui.components.list.StringDisplayable;
import pi.password.gui.splash.SplashController;
import pi.password.service.gui.DialogService;
import pi.password.service.util.CgiService;
import pi.password.service.util.ImageUtilService;

public class DeveloperController extends AbstractController {
	
	private final CgiService CGI_SERVICE;
	private final DialogService DIALOG_SERVICE;
	
	private ListView<StringDisplayable> view;
	private ListModel<StringDisplayable> model;

	public DeveloperController(CgiService cgiService, DialogService dialogService) {
		CGI_SERVICE = cgiService;
		DIALOG_SERVICE = dialogService;
		model = new ListModel<>();
		view = new ListView<>(LocalizedTexts.VIEW_PASSWORDS_TITLE.toString(), Main.getInstance(ImageUtilService.class).getMainBackground(), model);
		model.setData(CGI_SERVICE.getCgiScripts().stream().map(d -> new StringDisplayable(d)).collect(Collectors.toList()));
	}
	
	@Override
	public void activateHandler() {
		view.paint();
	}

	@Override
	public void reactivateHandler() {
		view.paint();
	}
	
	
	
	
	@Override
	public void handleButtonAReleased() {
		Main.getInstance(SplashController.class).activate();			
	}
	
	@Override
	public void handleButtonBReleased() {
		//TODO: delete
	}

	@Override
	public void handleButtonCReleased() {
		Main.lock();
	}

	@Override
	public void handleJoystickUpReleased() {
		//TODO
	}

	@Override
	public void handleJoystickDownReleased() {
		//TODO
	}

	@Override
	public void handleJoystickLeftReleased() {
		//TODO
	}

	@Override
	public void handleJoystickRightReleased() {
		//TOOD		
	}

	@Override
	public void handleJoystickCenterReleased() {
		String script = model.getSelectedValue().getValue();
		
		String response = CGI_SERVICE.execute(script);
		
		DIALOG_SERVICE.showMessage(response);
	}
	
	
}

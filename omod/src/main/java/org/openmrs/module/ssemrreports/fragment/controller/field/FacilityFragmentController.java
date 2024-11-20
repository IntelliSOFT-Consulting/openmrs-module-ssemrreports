package org.openmrs.module.ssemrreports.fragment.controller.field;

import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.ArrayList;
import java.util.List;

public class FacilityFragmentController {
	
	public void controller(FragmentModel fragmentModel, UiSessionContext uiSessionContext) {
		List<Location> getLocations = new ArrayList<Location>();
		User currentUSer = uiSessionContext.getCurrentUser();
		List<Location> visitLocations = Context.getLocationService().getLocationsByTag(
		    Context.getLocationService().getLocationTagByName("Visit Location"));
		if (currentUSer != null) { //&& currentUSer.hasPrivilege(BotswanaPrivilegeConstants.VIEW_BOTS_REPORTS) && !visitLocations.isEmpty()) {
			//getLocations.addAll(visitLocations);
			getLocations.add(uiSessionContext.getSessionLocation());
		}
		/*else {
		    getLocations.add(uiSessionContext.getSessionLocation());
		}*/
		
		fragmentModel.addAttribute("loggedInLocation", getLocations);
	}
}

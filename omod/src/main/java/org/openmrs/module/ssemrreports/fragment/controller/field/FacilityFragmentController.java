package org.openmrs.module.ssemrreports.fragment.controller.field;

import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.ArrayList;
import java.util.List;

public class FacilityFragmentController {
	
	public void controller(FragmentModel fragmentModel,
	        @FragmentParam(value = "location", required = false) Location location, UiSessionContext uiSessionContext) {
		List<Location> getLocations = new ArrayList<Location>();
		User currentUSer = uiSessionContext.getCurrentUser();
		List<Location> visitLocations = Context.getLocationService().getLocationsByTag(
		    Context.getLocationService().getLocationTagByName("Visit Location"));
		
		List<Location> loginLocations = Context.getLocationService().getLocationsByTag(
		    Context.getLocationService().getLocationTagByName("Login Location"));
		if (currentUSer != null && loginLocations != null && loginLocations.size() > 0) {
			getLocations.addAll(loginLocations);
		} else {
			getLocations.addAll(visitLocations);
		}
		
		fragmentModel.addAttribute("loggedInLocation", getLocations);
	}
}

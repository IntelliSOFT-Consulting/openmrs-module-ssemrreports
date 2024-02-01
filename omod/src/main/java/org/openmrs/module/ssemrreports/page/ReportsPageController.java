package org.openmrs.module.ssemrreports.page;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.openmrs.ui.framework.page.PageModel;

public class ReportsPageController {
	
	public void controller(PageModel model){
		boolean moduleStatus = false;
		
		for (Module mod : ModuleFactory.getLoadedModules()) {
			if(mod.getModuleId().equals("ssemrreports") && mod.isStarted()) {
				moduleStatus = true;
			}
		}
		
		model.addAttribute("moduleStatus", moduleStatus);
		model.addAttribute("userRoles", new ArrayList<>(Context.getService(UserService.class).getAllRoles().stream().filter(role -> role.getName().startsWith("Org")).collect(
				Collectors.toList())));
	}
}

/**
 * 
 */
package com.leave.request;

import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.leave.request.model.RequestType;
import com.leave.request.model.Role;
import com.leave.request.repository.RequestTypeRepository;
import com.leave.request.repository.RoleRepository;

/**
 * @author Eraine
 *
 */
@Component
public class DefaultDataLoader implements ApplicationRunner {

	@Autowired
	private RequestTypeRepository requestTypeRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private IdentityService identityService;

	public void run(ApplicationArguments args) throws Exception {
		// For sample users, please use the register page. It will automatically save the user to its
		// corresponding group based on the user role.
		
		// Insert roles
		if (roleRepository.count() == 0) {
			roleRepository.save(new Role("ADMIN", "Admin"));
			roleRepository.save(new Role("HR", "HR"));
			roleRepository.save(new Role("EMPLOYEE", "Employee"));
			roleRepository.save(new Role("TEAM_LEAD", "Team lead"));
			roleRepository.save(new Role("MANAGER", "Manager"));
		}	
		
		// Insert request type
		if (requestTypeRepository.count() == 0) {
			requestTypeRepository.save(new RequestType("VL", "Vacation Leave"));
			requestTypeRepository.save(new RequestType("SL", "Sick Leave"));
			requestTypeRepository.save(new RequestType("EL", "Emergency Leave"));
		}
		
		// Insert groups
		if (identityService.createGroupQuery().count() == 0) {
			String[] groups = new String[] {"hr", "employee", "team_lead", "manager"};
			for (String group: groups) {
				Group newGroup = identityService.newGroup(group);
				newGroup.setName(group);
				newGroup.setType("assignment");
				identityService.saveGroup(newGroup);
			}
		}
	}

}

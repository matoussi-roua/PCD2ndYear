package PCD.BACKEND.RECRAFTMARKET;

import PCD.BACKEND.RECRAFTMARKET.model.role.Role;
import PCD.BACKEND.RECRAFTMARKET.service.role.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@RequiredArgsConstructor
@SpringBootApplication
public class RecraftmarketApplication {
	private final RoleService roleService;
	public static void main(String[] args) {
		SpringApplication.run(RecraftmarketApplication.class, args);
	}

	@PostConstruct
	private void initProject()
	{
		initRoles();
	}

	private void initRoles() {
		createRoleIfNotExists("ADMIN");
		createRoleIfNotExists("CLIENT");
	}

	private void createRoleIfNotExists(String roleName) {
		if (!roleService.roleExists(roleName)) {
			roleService.createRole(new Role(roleName));
		}
	}

}

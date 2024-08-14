package tech.suji.seven_prods.projects.empmgnt.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tech.suji.seven_prods.projects.empmgnt.domain.Department;
import tech.suji.seven_prods.projects.empmgnt.domain.Employee;
import tech.suji.seven_prods.projects.empmgnt.domain.Role;
import tech.suji.seven_prods.projects.empmgnt.model.EmployeeDTO;
import tech.suji.seven_prods.projects.empmgnt.repos.DepartmentRepository;
import tech.suji.seven_prods.projects.empmgnt.repos.EmployeeRepository;
import tech.suji.seven_prods.projects.empmgnt.repos.RoleRepository;
import tech.suji.seven_prods.util.NotFoundException;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final RoleRepository roleRepository;

	public EmployeeService(final EmployeeRepository employeeRepository, final DepartmentRepository departmentRepository,
			final RoleRepository roleRepository) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.roleRepository = roleRepository;
	}

	public List<EmployeeDTO> findAll() {
		final List<Employee> employees = employeeRepository.findAll(Sort.by("id"));
		return employees.stream().map(employee -> mapToDTO(employee, new EmployeeDTO())).toList();
	}

	public EmployeeDTO get(final Long id) {
		return employeeRepository.findById(id).map(employee -> mapToDTO(employee, new EmployeeDTO()))
				.orElseThrow(NotFoundException::new);
	}

	public Long create(final EmployeeDTO employeeDTO) {
		final Employee employee = new Employee();
		mapToEntity(employeeDTO, employee);
		return employeeRepository.save(employee).getId();
	}

	public void update(final Long id, final EmployeeDTO employeeDTO) {
		final Employee employee = employeeRepository.findById(id).orElseThrow(NotFoundException::new);
		mapToEntity(employeeDTO, employee);
		employeeRepository.save(employee);
	}

	public void delete(final Long id) {
		employeeRepository.deleteById(id);
	}

	private EmployeeDTO mapToDTO(final Employee employee, final EmployeeDTO employeeDTO) {
		// employeeDTO.setId(employee.getId());
		employeeDTO.setName(employee.getName());
		employeeDTO.setUsername(employee.getUsername());
		employeeDTO.setEmail(employee.getEmail());
		employeeDTO.setPassword(employee.getPassword());
		employeeDTO.setGender(employee.getGender());
		employeeDTO.setHiredate(employee.getHiredate());
		employeeDTO.setActive(employee.getActive());
		employeeDTO.setReportingTo(employee.getReportingTo());
		employeeDTO.setCountry(employee.getCountry());
		employeeDTO.setState(employee.getState());
		employeeDTO.setCity(employee.getCity());
		employeeDTO.setSalary(employee.getSalary());
		employeeDTO.setImage(employee.getImage());
		employeeDTO.setDepartmentId(employee.getDepartment() == null ? null : employee.getDepartment().getId());
		employeeDTO.setRoleId(employee.getRole() == null ? null : employee.getRole().getId());
		return employeeDTO;
	}

	private Employee mapToEntity(final EmployeeDTO employeeDTO, final Employee employee) {
		employee.setName(employeeDTO.getName());
		employee.setUsername(employeeDTO.getUsername());
		employee.setEmail(employeeDTO.getEmail());
		employee.setPassword(employeeDTO.getPassword());
		employee.setGender(employeeDTO.getGender());
		employee.setHiredate(employeeDTO.getHiredate());
		employee.setActive(employeeDTO.getActive());
		employee.setReportingTo(employeeDTO.getReportingTo());
		employee.setCountry(employeeDTO.getCountry());
		employee.setState(employeeDTO.getState());
		employee.setCity(employeeDTO.getCity());
		employee.setSalary(employeeDTO.getSalary());
		employee.setImage(employeeDTO.getImage());

//		final Department department = employeeDTO.getDepartmentId() == null ? null
//				: departmentRepository.findById(employeeDTO.getDepartmentId())
//						.orElseThrow(() -> new NotFoundException("department not found"));
//		employee.setDepartment(department);
//
//		final Role role = employeeDTO.getRoleId() == null ? null
//				: roleRepository.findById(employeeDTO.getRoleId())
//						.orElseThrow(() -> new NotFoundException("role not found"));
//		employee.setRole(role);

		return employee;
	}

	public boolean firstNameExists(final String firstName) {
		return employeeRepository.existsByNameIgnoreCase(firstName);
	}

	public boolean usernameExists(final String username) {
		return employeeRepository.existsByUsernameIgnoreCase(username);
	}

	public boolean emailExists(final String email) {
		return employeeRepository.existsByEmailIgnoreCase(email);
	}

}

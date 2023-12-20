package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.repositories.EmployeeRepository;
import augusto108.ces.appointmenttracker.security.entities.Employee;
import augusto108.ces.appointmenttracker.security.entities.EmployeeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    public Employee findEmployeeByUsername(String username) {
        return repository.findEmployeeByUsername(username);
    }

    @Override
    public void saveEmployee(Employee employee) {
        repository.saveEmployee(employee);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Employee employee = repository.findEmployeeByUsername(username);
        return new org.springframework.security.core.userdetails
                .User(employee.getUsername(), employee.getPassword(), mapRolesToPermissions(employee.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToPermissions(Collection<EmployeeRole> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
    }
}

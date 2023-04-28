package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.repositories.EmployeeRoleRepository;
import augusto108.ces.appointmenttracker.security.EmployeeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeRoleServiceImpl implements EmployeeRoleService {
    private final EmployeeRoleRepository repository;

    @Override
    public void saveEmployeeRole(EmployeeRole employeeRole) {
        repository.saveEmployeeRole(employeeRole);
    }
}

package application.data.service;

import application.data.model.Role;
import application.data.repository.RoleRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    public static final Logger logger = LogManager.getLogger(RoleService.class) ;

    @Autowired
    private RoleRepository roleRepository;

    public void addNewRole (Role role){
        roleRepository.save(role);
    }

    @Transactional
    public void addNewListRole (List<Role> roles){
        roleRepository.save(roles);
    }

    public Role findOne(int roleId) {
        return roleRepository.findOne(roleId);
    }

    public boolean updateRole (Role role) {
        try {
            roleRepository.save(role);
            return true;
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean deteleRole (int roleId) {
        try {
            roleRepository.delete(roleId);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<Role> getListAllRole() {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }
    public Role getRoleByUser(Integer userId){
        return roleRepository.getRoleByUser(userId);
    }
}

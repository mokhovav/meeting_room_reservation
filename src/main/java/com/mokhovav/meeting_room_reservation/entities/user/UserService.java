package com.mokhovav.meeting_room_reservation.entities.user;

import com.mokhovav.meeting_room_reservation.entities.authority.Authority;
import com.mokhovav.meeting_room_reservation.database.DAOService;
import com.mokhovav.meeting_room_reservation.entities.authority.AuthorityService;
import com.mokhovav.meeting_room_reservation.error.CustomException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService { //need for WebSecurityConfig configure

    private final UserValid userValid;
    private final DAOService daoService;
    private final AuthorityService authorityService;

    public UserService(UserValid userValid, DAOService daoService, AuthorityService authorityService) {
        this.userValid = userValid;
        this.daoService = daoService;
        this.authorityService = authorityService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return (UserDetails) getByUserName(userName);   //need add UserDetails to User
    }

    public User getById(Long id){
        return id > 0 ? (User)daoService.findObject("from User where id="+id) : null;
    }

    public User getByUserName(String name){
        return name.isEmpty()? null : (User)daoService.findObject("from User where user_name='"+name+"'");
    }

    public boolean isExist(String name) {
        return name!=null && (name.isEmpty() ? false : ((User)daoService.findObject("from User where user_name='"+name+"'")) != null);
    }

    public boolean isExist(User user) {
        return user!= null ? isExist(user.getUsername()) : false;
    }

    public boolean isExist(Long id) {
        return getById(id) != null;
    }

    public boolean isHaveAuthority(User user, Authority authority) {
        if (user !=null && authority != null)
            for (Authority r : user.getAuthorities())
                if (r.getAuthority().equals(authority.getAuthority()))
                    return true;
        return false;
    }

    public void save(User user) throws CustomException {
        if(isExist(user)) throw new CustomException("This user already exists");
        daoService.save(user);
    }

    public boolean update(User user) {
        if( user!=null && getById(user.getId()) != null && !user.getUsername().isEmpty() && !user.getPassword().isEmpty()) {
            daoService.update(user);
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        User user = getById(id);
        if(user == null ) return false;
        daoService.delete(user);
        return true;
    }

    public List<UserData> getAll(){
        return (List<UserData>)daoService.findAll(User.class);
    }

    public void userUpdate(UserData userData, Map<String, String> list) throws CustomException {
        User user = getById(userData.getId());
        if(user == null || userData == null) throw new CustomException("User is not found");
        if(!userValid.notNullAndEmpty(userData.getUsername())) throw new CustomException("User name incorrect");
        user.setUsername(userData.getUsername());
        user.getAuthorities().clear();
        for (String key : list.keySet())
            if(authorityService.isExist(key))
                user.getAuthorities().add(authorityService.getByAuthority(key));
        update(user);
    }

    public void userDataCheck(User userData) throws CustomException {
        if(!userValid.userName(userData)) throw new CustomException("User name incorrect");
        passwordsCheck(userData.getPassword(), userData.getConfirm());
    }

    public void passwordsCheck(String password, String confirm) throws CustomException {
        if(!userValid.notNullAndEmpty(password)) throw new CustomException("Password incorrect");
        if(!password.equals(confirm)) throw new CustomException("Passwords do not match");
    }
}

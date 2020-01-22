package com.mokhovav.meeting_room_reservation.entities.authority;

import com.mokhovav.meeting_room_reservation.database.DAOService;
import com.mokhovav.meeting_room_reservation.error.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {
    private final DAOService daoService;
    private final AuthorityValid authorityValid;

    public AuthorityService(DAOService daoService, AuthorityValid authorityValid) {
        this.daoService = daoService;
        this.authorityValid = authorityValid;
    }

    public boolean isExist(String name) {
        return name.isEmpty() ? false : (Authority)daoService.findObject("from Authority where authority='"+name+"'") != null;
    }

    public boolean isExist(Authority authority) {
        return authority!=null ? isExist(authority.getAuthority()) : false;
    }

    public boolean isExist(Long id) {
        return getById(id) != null;
    }

    public void save(Authority authority) throws CustomException {
        if(!authorityValid.notNullAndEmpty(authority)) throw new CustomException("Authority should not be empty");
        if(isExist(authority)) throw new CustomException("Authority is already exist");
        daoService.save(authority);
    }

    public void update(Authority authority) throws CustomException {
        if (!authorityValid.notNullAndEmpty(authority)) throw new CustomException("Authority should not be empty");
        Authority authorityObj = getById(authority.getId());
        if (authorityObj == null) throw new CustomException("Authority does not exist");
        daoService.update(authority);
    }

    public boolean delete(Long id) throws CustomException  {
        Authority authority = getById(id);
        if(authority == null ) throw new CustomException("Authority does not exist");
        return daoService.delete(authority);
    }

    public Authority getByAuthority(String name){
        return name.isEmpty()? null : (Authority)daoService.findObject("from Authority where authority='"+name+"'");
    }

    public Authority getById(Long id){
        return id > 0 ? (Authority)daoService.findObject("from Authority where id="+id) : null;
    }

    public List<Authority> getAll(){
        return (List<Authority>)daoService.findAll(Authority.class);
    }
}

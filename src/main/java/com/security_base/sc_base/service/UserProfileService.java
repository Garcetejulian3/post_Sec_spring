package com.security_base.sc_base.service;

import com.security_base.sc_base.dto.DTORegisterUser;
import com.security_base.sc_base.dto.DTOUserProfileResponse;
import com.security_base.sc_base.models.UserSec;
import com.security_base.sc_base.repository.UserProfileRepository;
import com.security_base.sc_base.repository.UserSecRepository;
import com.security_base.sc_base.service.impl.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileService implements IUserProfileService {
    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private UserSecRepository userSecRepository;

    @Override
    public List<DTOUserProfileResponse> userList() {
        List<DTOUserProfileResponse> listUser = new ArrayList<>();
        List<UserSec> listDB = userSecRepository.findAll();

        for(UserSec userDB:listDB){
            DTOUserProfileResponse dto = new DTOUserProfileResponse();
            dto.setId(userDB.getId());
            dto.setUsername(userDB.getUsername());
        }
        return null;
    }

    @Override
    public DTOUserProfileResponse findUser(Long id) {
        return null;
    }

    @Override
    public DTOUserProfileResponse createUser(DTORegisterUser user) {
        return null;
    }

    @Override
    public DTOUserProfileResponse editUser(DTORegisterUser user) {
        return null;
    }
}

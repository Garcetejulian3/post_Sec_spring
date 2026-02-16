package com.security_base.sc_base.service.impl;

import com.security_base.sc_base.dto.DTORegisterUser;
import com.security_base.sc_base.dto.DTOUserProfileResponse;

import java.util.List;

public interface IUserProfileService {
    public List<DTOUserProfileResponse> userList();
    public DTOUserProfileResponse findUser(Long id);
    public DTOUserProfileResponse createUser(DTORegisterUser user);
    public DTOUserProfileResponse editUser(DTORegisterUser user,Long id);
    public String encriptPassword(String password);

}

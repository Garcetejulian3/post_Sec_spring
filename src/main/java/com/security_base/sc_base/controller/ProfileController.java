package com.security_base.sc_base.controller;

import com.security_base.sc_base.dto.DTORegisterUser;
import com.security_base.sc_base.dto.DTOUserProfileResponse;
import com.security_base.sc_base.service.impl.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@PreAuthorize("permitAll()")
public class ProfileController {
    @Autowired
    private IUserProfileService profileService;

    @GetMapping
    public List<DTOUserProfileResponse> allProfile(){
        return profileService.userList();
    }


    @GetMapping("/{id}")
    public DTOUserProfileResponse getProfile(@PathVariable Long id){
        return profileService.findUser(id);
    }

    @PostMapping("/register")
    public DTOUserProfileResponse saveProfile(@RequestBody DTORegisterUser user){
        return profileService.createUser(user);
    }
}

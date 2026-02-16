package com.security_base.sc_base.service;

import com.security_base.sc_base.dto.DTORegisterUser;
import com.security_base.sc_base.dto.DTOUserProfileResponse;
import com.security_base.sc_base.models.Role;
import com.security_base.sc_base.models.UserProfile;
import com.security_base.sc_base.models.UserSec;
import com.security_base.sc_base.repository.RoleRepository;
import com.security_base.sc_base.repository.UserProfileRepository;
import com.security_base.sc_base.repository.UserSecRepository;
import com.security_base.sc_base.service.impl.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileService implements IUserProfileService {
    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private UserSecRepository userSecRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<DTOUserProfileResponse> userList() {
        // Lista de DTO para la muestra
        List<DTOUserProfileResponse> listUser = new ArrayList<>();

        // Lista de UserProfile traida de la base de datos
        List<UserProfile> listProfiles = profileRepository.findAll();

            for (UserProfile profile:listProfiles){
                DTOUserProfileResponse dto = new DTOUserProfileResponse();
                dto.setId(profile.getIdProfile());
                dto.setUsername(profile.getUsername());
                dto.setEmail(profile.getEmail());
                listUser.add(dto);
            }

        return listUser;
    }

    @Override
    public DTOUserProfileResponse findUser(Long id) {
        UserProfile profile = profileRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Error al buscar el perfil de usuario"));
        DTOUserProfileResponse dto = new DTOUserProfileResponse(
                profile.getIdProfile(),
                profile.getUsername(),
                profile.getEmail()
        );
        return dto;
    }

    @Override
    public DTOUserProfileResponse createUser(DTORegisterUser user) {
        // Traigo el rol de AUTHOR de la base de datos para guardarlo en la entidad UserSec
        Role authorRole = roleRepository.findById(2L)
                .orElseThrow(()-> new RuntimeException("Role no encontrado"));

        // Empiezo a crear la entidad UserSec para el spring security
        UserSec userSec = new UserSec();
        userSec.setUsername(user.getUsername());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            String newPassword = encriptPassword(user.getPassword());
            userSec.setPassword(newPassword);
        }
        userSec.setAccountNotExpired(true);
        userSec.setAccountNotLocked(true);
        userSec.setCredentialNotExpired(true);
        userSec.setEnabled(true);
        // Asigno el role
        userSec.getRolesList().add(authorRole);
        // Ahora guardo el UserSec creado
        userSecRepository.save(userSec);
        // Creo el profile del usuario
        UserProfile profile = new UserProfile();
        profile.setUsername(user.getUsername());
        profile.setEmail(user.getEmail());
        profile.setUserSec(userSec);
        // Guardo el profile en la base de datos
        profileRepository.save(profile);
        return new DTOUserProfileResponse(
                profile.getIdProfile(),
                userSec.getUsername(),
                profile.getEmail()
        );
    }

    @Override
    public DTOUserProfileResponse editUser(DTORegisterUser user,Long id) {
        // Busco el usuario de la base de datos
        UserSec userSec = userSecRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        // Busco el profile de la base de datos
        UserProfile profile = profileRepository.findByUserSec(userSec)
                .orElseThrow(()-> new RuntimeException("Profile no encontrado"));
        // Seteo los nuevos datos del usuario
        userSec.setUsername(user.getUsername());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            String newPassword = encriptPassword(user.getPassword());
            userSec.setPassword(newPassword);
        }

        profile.setEmail(user.getEmail());
        // Guardo el profile en la base de datos
        profileRepository.save(profile);
        // Ahora guardo el UserSec creado
        userSecRepository.save(userSec);
        return new DTOUserProfileResponse(
                profile.getIdProfile(),
                userSec.getUsername(),
                profile.getEmail()
        );
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}

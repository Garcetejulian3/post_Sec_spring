package com.security_base.sc_base.service;

import com.security_base.sc_base.dto.DTOCreatePost;
import com.security_base.sc_base.dto.DTOResponsePost;
import com.security_base.sc_base.models.Posteos;
import com.security_base.sc_base.models.UserProfile;
import com.security_base.sc_base.models.UserSec;
import com.security_base.sc_base.repository.PosteosRepository;
import com.security_base.sc_base.repository.UserProfileRepository;
import com.security_base.sc_base.repository.UserSecRepository;
import com.security_base.sc_base.service.impl.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService implements IPostService {
    @Autowired
    private PosteosRepository postRepository;

    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private UserSecRepository userSecRepository;
    @Override
    public List<DTOResponsePost> postList() {
        // Creo la dista dto
        List<DTOResponsePost> listDto = new ArrayList<>();
        // Traigo la lista de post de la base de datos
        List<Posteos> listPost = postRepository.findAll();
        // for para rellenar la lista dto
        for (Posteos post:listPost){
            DTOResponsePost dto = new DTOResponsePost();
            dto.setTema(post.getTema());
            dto.setAuthorName(post.getAuthor().getUsername());
            dto.setContenido(post.getContenido());
            dto.setTitulo(post.getTitulo());
            listDto.add(dto);
        }
        return listDto;
    }

    @Override
    public DTOResponsePost findPost(Long id) {
        Posteos post = postRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Posteo no encontrado"));
        DTOResponsePost dto = new DTOResponsePost(
                post.getTitulo(),
                post.getTema(),
                post.getAuthor().getUsername(),
                post.getContenido()
        );
        return dto;
    }

    @Override
    public DTOResponsePost createPost(DTOCreatePost dto) {
        UserProfile profile = authorAutenticado();

        // Creacion del post
        Posteos post = new Posteos(
                null,
                dto.getTitulo(),
                dto.getContenido(),
                dto.getTema(),
                profile
        );
        postRepository.save(post);
        // Return del Response
        DTOResponsePost dtoResponse = new DTOResponsePost(
                post.getTitulo(),
                post.getTema(),
                post.getContenido(),
                post.getAuthor().getUsername()
        );
        return dtoResponse;
    }

    @Override
    public DTOResponsePost editPost(DTOCreatePost dto,Long id) {
        // Traigo el post de la bd
        Posteos post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Posteo no encontrado"));
        // obtengo el usuario autenticado
        UserProfile profile = authorAutenticado();


        // Validación de seguridad / valido el id del author del post por el id del usuario autenticado
        if (!post.getAuthor().getIdProfile().equals(profile.getIdProfile())) {
            throw new RuntimeException("No puedes editar posts ajenos");
        }


        // Seteo de muevos datos
        post.setTitulo(dto.getTitulo());
        post.setTema(dto.getTema());
        post.setContenido(dto.getContenido());

        postRepository.save(post);
        // Return del Response
        DTOResponsePost dtoResponse = new DTOResponsePost(
                post.getTitulo(),
                post.getTema(),
                post.getAuthor().getUsername(),
                post.getContenido()
        );
        return dtoResponse;
    }

    public UserProfile authorAutenticado(){
            // Usuario autenticado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            // Busco al usuario autenticado de la bd
            UserSec user = userSecRepository.findUserEntityByUsername(username)
                    .orElseThrow(()-> new RuntimeException("Usuario no encontrado/no auntenticado"));
            // Traigo al author de la bd
            UserProfile profile = profileRepository.findByUserSec(user)
                    .orElseThrow(()-> new RuntimeException("Author no encontrado"));
            return profile;
        }
}

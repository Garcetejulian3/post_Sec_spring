package com.security_base.sc_base.controller;

import com.security_base.sc_base.dto.DTOCreatePost;
import com.security_base.sc_base.dto.DTOResponsePost;
import com.security_base.sc_base.service.impl.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class PostController {
    @Autowired
    private IPostService postService;

    @GetMapping
    public List<DTOResponsePost> allPost(){
        return postService.postList();
    }

    @GetMapping("/{id}")
    public DTOResponsePost getPost(@PathVariable Long id){
        return postService.findPost(id);
    }

    @PostMapping
    public DTOResponsePost savePost(@RequestBody DTOCreatePost dto){
        return postService.createPost(dto);
    }
}

package com.security_base.sc_base.service.impl;

import com.security_base.sc_base.dto.DTOCreatePost;
import com.security_base.sc_base.dto.DTOResponsePost;

import java.util.List;

public interface IPostService {
    public List<DTOResponsePost> postList();
    public DTOResponsePost findPost(Long id);
    public DTOResponsePost createPost(DTOCreatePost dto);
    public DTOResponsePost editPost(DTOCreatePost dto,Long id);
}

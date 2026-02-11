package com.security_base.sc_base.repository;

import com.security_base.sc_base.models.Posteos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosteosRepository extends JpaRepository<Posteos,Long> {
}

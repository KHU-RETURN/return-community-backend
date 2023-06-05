package com.khureturn.community.repository;

import com.khureturn.community.domain.notice.NoticeLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeLikeRepository extends JpaRepository<NoticeLike, Long> {
}

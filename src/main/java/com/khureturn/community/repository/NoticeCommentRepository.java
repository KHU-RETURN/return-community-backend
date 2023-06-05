package com.khureturn.community.repository;

import com.khureturn.community.domain.notice.NoticeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCommentRepository extends JpaRepository<NoticeComment, Long> {
}

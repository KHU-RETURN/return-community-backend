package com.khureturn.community.repository;

import com.khureturn.community.domain.notice.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeFileRepository extends JpaRepository<NoticeFile, Long> {
}

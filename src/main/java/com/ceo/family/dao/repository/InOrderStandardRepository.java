package com.ceo.family.dao.repository;

import com.ceo.family.dao.BaseRepository;
import com.ceo.family.dao.dos.InOrderStandardDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InOrderStandardRepository extends BaseRepository<InOrderStandardDO, Long> {
    List<InOrderStandardDO> findByInOrderIdAndStatusOrderByUpdatedAtAsc(long inOrderId, int status);
    @Modifying
    @Query("update InOrderStandardDO set  status = ?2 where id in ?1")
    int updateStatus(List<Long> ids, int status);
}

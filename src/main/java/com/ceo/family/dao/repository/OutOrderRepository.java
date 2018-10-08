package com.ceo.family.dao.repository;

import com.ceo.family.dao.BaseRepository;
import com.ceo.family.dao.dos.OutOrderDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutOrderRepository extends BaseRepository<OutOrderDO, Long> {
    @Modifying
    @Query("update OutOrderDO set  status = ?2 where id in ?1")
    int updateStatus(List<Long> ids, int status);
}

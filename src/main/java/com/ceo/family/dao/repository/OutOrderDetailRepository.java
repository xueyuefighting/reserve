package com.ceo.family.dao.repository;

import com.ceo.family.dao.BaseRepository;
import com.ceo.family.dao.dos.OutOrderDetailDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutOrderDetailRepository extends BaseRepository<OutOrderDetailDO, Long> {
    @Modifying
    @Query("update OutOrderDetailDO set  status = ?2 where id in ?1")
    int updateStatus(List<Long> ids, int status);

    List<OutOrderDetailDO> findByOutOrderIdAndStatusOrderByUpdatedAtDesc(long outOrderId, int status);

}

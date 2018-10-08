package com.ceo.family.dao.repository;

import com.ceo.family.dao.BaseRepository;
import com.ceo.family.dao.dos.InOrderDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InOrderRepository extends BaseRepository<InOrderDO, Long> {
    List<InOrderDO> findByModelLikeAndStatusOrderByUpdatedAtDesc(String model, int status);

    @Modifying
    @Query("update InOrderDO set  status = ?2 where id in ?1")
    int updateStatus(List<Long> ids, int status);

    @Modifying
    @Query("update InOrderDO set  currentCount = ?2,sellCount= ?3 where id = ?1")
    int updateCount(long id, long currentCount, long sellCount);
}

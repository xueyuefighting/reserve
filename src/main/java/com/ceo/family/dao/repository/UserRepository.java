package com.ceo.family.dao.repository;

import com.ceo.family.dao.BaseRepository;
import com.ceo.family.dao.dos.UserDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * @author zhangjinliang
 * @Date 2018/10/4 下午5:09
*/
public interface UserRepository extends BaseRepository<UserDO, Long> {
    List<UserDO> findByNameLikeAndAndStatusOrderByUpdatedAtDesc(String name, int status);
    List<UserDO> findByTelAndStatusOrderByUpdatedAtDesc(long tel, int status);
    List<UserDO> findByIdInAndStatusOrderByUpdatedAtDesc(List<Long> ids, int status);
    List<UserDO> findByStatusOrderByCreatedAtDesc(int status);
}

package com.ceo.family.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author zhangjinliang
 * @Date 2018/7/31 下午3:48
*/
@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable>
        extends JpaRepository<T,ID>,JpaSpecificationExecutor<T> {

}

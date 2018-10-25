package com.ceo.family.service.interf;

import com.ceo.family.dao.dos.UserDO;
import com.ceo.family.dao.dtos.UserDTO;
import com.ceo.family.dao.dtos.UserParam;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjinliang
 * @Date 2018/10/4 下午9:58
*/
public interface IUserService {
    UserDTO saveOrUpdate(UserDTO userDTO);

    UserDTO findById(long id);

    List<UserParam> findByNameLike(String name);

    List<UserDTO> findByTel(long tel);

    Page<UserDO> findByParam(UserParam userParam);

    List<UserDTO> findAll();
}

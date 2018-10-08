package com.ceo.family.service;

import com.ceo.family.constant.ContentConstant;
import com.ceo.family.constant.Status;
import com.ceo.family.dao.dos.UserDO;
import com.ceo.family.dao.dtos.UserDTO;
import com.ceo.family.dao.dtos.UserParam;
import com.ceo.family.dao.repository.UserRepository;
import com.ceo.family.service.interf.IUserService;
import com.ceo.family.utils.BeanCopy;
import com.ceo.family.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhangjinliang
 * @Date 2018/10/4 下午5:22
*/
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    private static final int size = 20;

    @Override
    @Transactional
    public UserDTO saveOrUpdate(UserDTO userDTO) {
        if(Objects.isNull(userDTO)){throw new IllegalArgumentException(ContentConstant.PARAM_IS_NULL);}
        UserDO userDO = new UserDO();
        if(userDTO.getId()<=0){
            userDTO.setCreatedAt(DateUtils.getCurrentSeconds());
            userDTO.setUpdatedAt(DateUtils.getCurrentSeconds());
        }else{
            userDTO.setUpdatedAt(DateUtils.getCurrentSeconds());
        }
        BeanCopy.copyProperties(userDTO, userDO);
        UserDO save = userRepository.save(userDO);

        BeanCopy.copyProperties(save, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO findById(long id) {
        if(id<=0){throw new IllegalArgumentException(ContentConstant.PARAM_IS_NULL);}
        UserDO userDO = userRepository.findById(id).orElse(new UserDO());

        UserDTO userDTO = new UserDTO();

        if(Objects.isNull(userDO) || userDO.getId()<=0){return userDTO;}
        BeanCopy.copyProperties(userDO, userDTO);
        return userDTO;
    }

    @Override
    public List<UserParam> findByNameLike(String name) {
        if(StringUtils.isEmpty(name)){throw new IllegalArgumentException(ContentConstant.PARAM_IS_NULL);}
        List<UserDO> userDOS = userRepository.findByNameLikeAndAndStatusOrderByUpdatedAtDesc("%" + name + "%", Status.NORMAL.getValue());
        Map<String, Long> collect = userDOS.stream().collect(Collectors.toMap(UserDO::getName, UserDO::getTel));
        List<UserParam> userParams = userDOS.stream().map(x -> new UserParam(x.getName(), x.getTel())).collect(Collectors.toList());
        return userParams;
    }

    @Override
    public List<UserDTO> findByTel(long tel) {
        return null;
    }

    @Override
    public Page<UserDO> findByParam(UserParam userParam) {
        Pageable page =
                PageRequest.of(userParam.getPage(), size, new Sort(Sort.Direction.DESC, "id"));
        Page<UserDO> content = userRepository.findAll(listWhere(userParam), page);
//        List<UserDO> all =content.getContent();
//        List<UserDTO> collect = all.stream()
//                .filter(x -> Objects.nonNull(x))
//                .map(x -> transferToDTO(x))
//                .collect(Collectors.toList());
        return content;
    }

    private static UserDO transferToDO(UserDTO dto, UserDO dO){
        if(dto==null || dO==null) return new UserDO();
        BeanCopy.copyProperties(dto, dO);
        dO.setStatus(dto.getStatus().getValue());
        return dO;
    }
    private static UserDTO transferToDTO(UserDO ds){
        if(ds==null) return new UserDTO();
        UserDTO dto = new UserDTO();
        BeanCopy.copyProperties(ds, dto);
        dto.setStatus(Status.findByValue(ds.getStatus()));
        return dto;
    }

    private Specification<UserDO> listWhere(final UserParam userParam){
        return new Specification<UserDO>() {
            @Override
            public Predicate toPredicate(Root<UserDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if(!StringUtils.isEmpty(userParam.getName()))
                    list.add(cb.like(root.get("name").as(String.class), "%"+userParam.getName()+"%"));
                if(userParam.getTel()>0)
                    list.add(cb.equal(root.get("tel").as(Long.class), userParam.getTel()));

                return query.where(list.toArray(new Predicate[list.size()])).getRestriction();
            }
        };

    }
}

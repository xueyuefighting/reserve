package com.ceo.family.service;

import com.ceo.family.constant.ContentConstant;
import com.ceo.family.constant.Status;
import com.ceo.family.constant.TradeConstant;
import com.ceo.family.dao.dos.InOrderDO;
import com.ceo.family.dao.dos.InOrderStandardDO;
import com.ceo.family.dao.dtos.InOrderDTO;
import com.ceo.family.dao.dtos.InOrderParam;
import com.ceo.family.dao.dtos.InOrderStandardDTO;
import com.ceo.family.dao.repository.InOrderRepository;
import com.ceo.family.dao.repository.InOrderStandardRepository;
import com.ceo.family.service.interf.IInOrderService;
import com.ceo.family.utils.BeanCopy;
import com.ceo.family.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
/**
 * @author zhangjinliang
 * @Date 2018/10/4 下午10:11
*/
public class InOrderService implements IInOrderService{

    @Autowired
    private InOrderStandardRepository inOrderStandardRepository;
    @Autowired
    private InOrderRepository inOrderRepository;

    private static final int size = 20;
    @Override
    @Transactional
    public InOrderDTO saveOrUpdate(InOrderDTO inOrderDTO) {
        if(Objects.isNull(inOrderDTO)){throw new IllegalArgumentException(ContentConstant.PARAM_IS_NULL);}
        InOrderDO inOrderDO = new InOrderDO();
        if(inOrderDTO.getId()<=0){
            inOrderDTO.setCreatedAt(DateUtils.getCurrentSeconds());
            inOrderDTO.setUpdatedAt(DateUtils.getCurrentSeconds());
        }else{
            inOrderDTO.setUpdatedAt(DateUtils.getCurrentSeconds());
        }

        transferToDO(inOrderDTO, inOrderDO);
        long tc = inOrderDO.getTotalCount()*10;
        inOrderDO.setTotalCount(tc);
        inOrderDO.setCurrentCount(tc);
        inOrderDO.setSellCount(0);
        InOrderDO inOrderDO1 = inOrderRepository.save(inOrderDO);

        List<InOrderStandardDTO> ios = inOrderDTO.getIos();
        List<InOrderStandardDO> isa = new ArrayList<>();
        InOrderStandardDO i = null;
        for(InOrderStandardDTO is : ios){
            i = new InOrderStandardDO();
            if(is.getId()<=0){
                is.setCreatedAt(DateUtils.getCurrentSeconds());
                is.setUpdatedAt(DateUtils.getCurrentSeconds());
            }else{
                is.setUpdatedAt(DateUtils.getCurrentSeconds());
            }
            transferToDO(is, i);
            i.setInOrderId(inOrderDO1.getId());
            isa.add(i);
        }

        List<InOrderStandardDO> dos = inOrderStandardRepository.saveAll(isa);
        InOrderDTO dto = getInOrderDTO(inOrderDO1, dos);
        return dto;
    }

    @Override
    public InOrderDTO findById(long id) {
        if(id<=0){throw new IllegalArgumentException(ContentConstant.PARAM_IS_NULL);}
        InOrderDO inOrderDO = inOrderRepository.findById(id).orElse(new InOrderDO());
        List<InOrderStandardDO> ats =
                inOrderStandardRepository.findByInOrderIdInAndStatusOrderByUpdatedAtAsc(Arrays.asList(id), Status.NORMAL.getValue());
        InOrderDTO inOrderDTO = getInOrderDTO(inOrderDO, ats);
        return inOrderDTO;
    }

    private InOrderDTO getInOrderDTO(InOrderDO inOrderDO, List<InOrderStandardDO> ats) {
        InOrderDTO inOrderDTO = transferToDTO(inOrderDO);
        inOrderDTO.setTotalCount(inOrderDTO.getTotalCount()/10);
        inOrderDTO.setCurrentCount(inOrderDTO.getCurrentCount()/10);
        inOrderDTO.setSellCount(inOrderDTO.getSellCount()/10);
        List<InOrderStandardDTO> ios = CollectionUtils.isEmpty(inOrderDTO.getIos()) ? new ArrayList<>() : inOrderDTO.getIos();
        for(InOrderStandardDO sdo : ats){
            ios.add(transferToDTO(sdo));
        }
        return inOrderDTO;
    }

    @Override
    public Page<InOrderDTO> findByParam(InOrderParam param) {
        Pageable pageable = PageRequest.of(param.getPage(), size, Sort.Direction.DESC, "updatedAt");
        Page<InOrderDO> all = inOrderRepository.findAll(listWhere(param), pageable);
        List<InOrderDO> content = all.getContent();
        List<Long> ids = content.stream().map(InOrderDO::getId).collect(Collectors.toList());

        List<InOrderStandardDO> ss =
                inOrderStandardRepository.findByInOrderIdInAndStatusOrderByUpdatedAtAsc(ids, Status.NORMAL.getValue());

        final Map<Long, List<InOrderStandardDO>> collect =
                ss.stream().collect(Collectors.groupingBy(InOrderStandardDO::getInOrderId));
        //转交换类
        List<InOrderDTO> orderDTOS = content.stream().map(x -> transferToDTO(x)).collect(Collectors.toList());
        //填充
        orderDTOS.forEach(x->x.setIos(transferToDTOs(collect.get(x.getId()))));

        return new PageImpl<>(orderDTOS, pageable, all.getTotalElements());
    }

    @Override
    @Transactional
    /**
     * @author zhangjinliang
     * @date 2018/10/6 下午4:39
     * @param [id, tradeNum]  tradeNum=交易量
     * @return long
     */
    public long updateModel(long id, List<TradeConstant> tradeNum) {
        if(id<=0){throw new IllegalArgumentException(ContentConstant.PARAM_IS_NULL);}
        InOrderDO inOrderDO = inOrderRepository.findById(id).orElse(new InOrderDO());
        Integer buy = tradeNum.stream().map(x -> x.getCount() * x.getStand()).reduce((x, y) -> x + y).orElse(0);
        Long longBuy = Long.valueOf(buy);
        inOrderDO.setCurrentCount(inOrderDO.getCurrentCount()-longBuy);
        inOrderDO.setSellCount(inOrderDO.getSellCount()+longBuy);
        InOrderDO save = inOrderRepository.save(inOrderDO);
        return 1;
    }


    private Specification<InOrderDO> listWhere(final InOrderParam inOrderParam){
        return new Specification<InOrderDO>() {
            @Override
            public Predicate toPredicate(Root<InOrderDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(inOrderParam.getCreatedTimeFrom()>0)
                    list.add(cb.greaterThanOrEqualTo(root.get("createdAt").as(Timestamp.class), new Timestamp(inOrderParam.getCreatedTimeFrom()*1000)));
                if(inOrderParam.getCreatedTimeTo()>0)
                    list.add(cb.lessThanOrEqualTo(root.get("createdAt").as(Timestamp.class), new Timestamp(inOrderParam.getCreatedTimeTo()*1000)));
                if(!StringUtils.isEmpty(inOrderParam.getModelLike()))
                    list.add(cb.like(root.get("model").as(String.class), "%"+inOrderParam.getModelLike()+"%"));
                if(inOrderParam.getStoneDateFrom()>0)
                    list.add(cb.greaterThanOrEqualTo(root.get("stoneDate").as(Timestamp.class), new Timestamp(inOrderParam.getStoneDateFrom()*1000)));
                if(inOrderParam.getStoneDateTo()>0)
                    list.add(cb.lessThanOrEqualTo(root.get("stoneDate").as(Timestamp.class), new Timestamp(inOrderParam.getStoneDateTo()*1000)));
                if(inOrderParam.getTotalCountMin()>0)
                    list.add(cb.lessThanOrEqualTo(root.get("totalCount").as(Long.class), inOrderParam.getTotalCountMin()));
                list.add(cb.greaterThanOrEqualTo(root.get("totalCount").as(Long.class), inOrderParam.getTotalCountMax()));
                if(inOrderParam.getCurrentCountMin()>0)
                    list.add(cb.lessThanOrEqualTo(root.get("currentCount").as(Long.class), inOrderParam.getCurrentCountMin()));
                list.add(cb.greaterThanOrEqualTo(root.get("currentCount").as(Long.class), inOrderParam.getCurrentCountMax()));
                if(inOrderParam.getSellCountMin()>0)
                    list.add(cb.lessThanOrEqualTo(root.get("sellCount").as(Long.class), inOrderParam.getSellCountMin()));
                list.add(cb.greaterThanOrEqualTo(root.get("sellCount").as(Long.class), inOrderParam.getSellCountMax()));
                return query.where(list.toArray(new Predicate[list.size()])).getRestriction();
            }
        };

    }
    private static InOrderDO transferToDO(InOrderDTO dto, InOrderDO dO){
        if(dto==null || dO==null) return new InOrderDO();
        BeanCopy.copyProperties(dto, dO);
        dO.setStatus(Objects.isNull(dto.getStatus())? Status.NORMAL.getValue() : dto.getStatus().getValue());
        return dO;
    }
    private static InOrderDTO transferToDTO(InOrderDO ds){
        if(ds==null) return new InOrderDTO();
        InOrderDTO dto = new InOrderDTO();
        BeanCopy.copyProperties(ds, dto);
        dto.setStatus(Status.findByValue(ds.getStatus()));
        return dto;
    }

    private static InOrderStandardDO transferToDO(InOrderStandardDTO dto, InOrderStandardDO dO){
        if(dto==null || dO==null) return new InOrderStandardDO();
        BeanCopy.copyProperties(dto, dO);
        dO.setStatus(Objects.isNull(dto.getStatus())? Status.NORMAL.getValue() : dto.getStatus().getValue());
        return dO;
    }

    private static List<InOrderStandardDTO> transferToDTOs(List<InOrderStandardDO> dss){
        if(CollectionUtils.isEmpty(dss)) return new ArrayList<>();
        return dss.stream().map(x->transferToDTO(x)).collect(Collectors.toList());
    }
    private static InOrderStandardDTO transferToDTO(InOrderStandardDO ds){
        if(ds==null) return new InOrderStandardDTO();
        InOrderStandardDTO dto = new InOrderStandardDTO();
        BeanCopy.copyProperties(ds, dto);
        dto.setStatus(Status.findByValue(ds.getStatus()));
        return dto;
    }

}

package com.ceo.family.service;

import com.ceo.family.constant.Status;
import com.ceo.family.dao.dos.OutOrderDO;
import com.ceo.family.dao.dtos.OutOrderDTO;
import com.ceo.family.dao.dtos.OutOrderParam;
import com.ceo.family.service.interf.IOutOrderService;
import com.ceo.family.utils.BeanCopy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangjinliang
 * @Date 2018/10/4 下午9:57
*/
@Service
public class OutOrderService implements IOutOrderService{
    @Override
    public OutOrderDTO saveOrUpdate(OutOrderDTO outOrderDTO) {
        return null;
    }

    @Override
    public OutOrderDTO findById(long id) {
        return null;
    }

    @Override
    public Page<OutOrderDO> findByParam(OutOrderParam param) {
        return null;
    }

    private static OutOrderDO transferToDO(OutOrderDTO dto, OutOrderDO dO){
        if(dto==null || dO==null) return new OutOrderDO();
        BeanCopy.copyProperties(dto, dO);
        dO.setStatus(dto.getStatus().getValue());
        return dO;
    }
    private static OutOrderDTO transferToDTO(OutOrderDO ds){
        if(ds==null) return new OutOrderDTO();
        OutOrderDTO dto = new OutOrderDTO();
        BeanCopy.copyProperties(ds, dto);
        dto.setStatus(Status.findByValue(ds.getStatus()));
        return dto;
    }
}

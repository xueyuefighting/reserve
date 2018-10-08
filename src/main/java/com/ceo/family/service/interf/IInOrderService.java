package com.ceo.family.service.interf;

import com.ceo.family.constant.TradeConstant;
import com.ceo.family.dao.dos.InOrderDO;
import com.ceo.family.dao.dtos.InOrderDTO;
import com.ceo.family.dao.dtos.InOrderParam;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IInOrderService {
    InOrderDTO saveOrUpdate(InOrderDTO inOrderDTO);

    InOrderDTO findById(long id);

    Page<InOrderDO> findByParam(InOrderParam param);

    long updateModel(long id, List<TradeConstant> currentCount);
}

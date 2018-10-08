package com.ceo.family.service.interf;


import com.ceo.family.dao.dos.OutOrderDO;
import com.ceo.family.dao.dtos.OutOrderDTO;
import com.ceo.family.dao.dtos.OutOrderParam;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOutOrderService {
    OutOrderDTO saveOrUpdate(OutOrderDTO outOrderDTO);

    OutOrderDTO findById(long id);

    Page<OutOrderDO> findByParam(OutOrderParam param);
}

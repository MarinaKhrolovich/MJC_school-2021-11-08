package com.epam.esm.mapper;

import com.epam.esm.bean.Order;
import com.epam.esm.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO convertToDTO(Order order);

    Order convertToEntity(OrderDTO orderDTO);

}

package com.github.sebastianp265.ordermanager.dtos;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Value
@Builder
public class CreateOrderDto {

    Long clientId;

    Map<Long, Long> productToQuantityMap;
}

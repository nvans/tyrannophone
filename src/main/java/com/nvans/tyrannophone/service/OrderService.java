package com.nvans.tyrannophone.service;

import com.nvans.tyrannophone.model.Order;
import com.nvans.tyrannophone.model.dto.ContractDto;
import com.nvans.tyrannophone.model.dto.CustomerDto;

public interface OrderService {

    void processOrder(Order order);

    void processContractOrder(Order order);

    void processPlanOrder(Order order);

    void processOptionsOrder(Order order);

    void createContractOrder(CustomerDto customer, ContractDto contract);

    void createPlanOrder(CustomerDto customer, ContractDto contract);

    void createOptionsOrder(CustomerDto customer, ContractDto contract);
}

package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.Order;
import com.nvans.tyrannophone.model.OrderStatus;
import com.nvans.tyrannophone.model.OrderType;
import com.nvans.tyrannophone.model.cart.OrderCart;
import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.dto.ContractDto;
import com.nvans.tyrannophone.model.dto.CustomerDto;
import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.service.*;
import com.nvans.tyrannophone.service.helper.OptionsGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Secured({"ROLE_CUSTOMER", "ROLE_EMPLOYEE"})
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private OptionsGraph optionsGraph;

    @Autowired
    private OrderCart orderCart;

    public void createContractOrder(CustomerDto customer, ContractDto contract) {

        Order order = new Order(customer, contract);
        order.setOrderType(OrderType.CONTRACT);

        orderCart.addOrder(order);
    }

    public void createPlanOrder(CustomerDto customer, ContractDto contract) {
        Order order = new Order(customer, contract);
        order.setOrderType(OrderType.PLAN);

        orderCart.addOrder(order);
    }

    public void createOptionsOrder(CustomerDto customer, ContractDto contract) {

        ContractDto existedContractDto = contractService.getContractDtoByNumber(contract.getContractNumber());

        Set<Option> optionsToConnect = new HashSet<>();

        contract.getOptions().forEach(option -> {
            Option optionPO = optionDao.findById(option.getId());
            optionsToConnect.add(optionPO);
            optionsToConnect.addAll(optionsGraph.getParents(optionPO));
        });

        contract.setOptions(optionsToConnect.stream().map(PlanOptionDto::new).collect(Collectors.toList()));

        Order order = new Order(customer, contract, existedContractDto.getOptions());
        order.setOrderType(OrderType.OPTIONS);

        orderCart.addOrder(order);
    }


    @Override
    public void processOrder(Order order) {

        switch (order.getOrderType()) {
            case CONTRACT:
                orderService.processContractOrder(order); break;
            case PLAN:
                orderService.processPlanOrder(order); break;
            case OPTIONS:
                orderService.processOptionsOrder(order); break;
        }

    }

    @Override
    public void processContractOrder(Order order) {

        order.setOrderStatus(OrderStatus.PROCESSING);

        contractService.addContract(order.getContract(), order.getCustomer().getCustomerId());

        order.setOrderStatus(OrderStatus.COMPLETED);
    }

    @Override
    public void processPlanOrder(Order order) {

        order.setOrderStatus(OrderStatus.PROCESSING);

        contractService.updateContractFull(order.getContract());

        order.setOrderStatus(OrderStatus.COMPLETED);
    }

    @Override
    public void processOptionsOrder(Order order) {

        order.setOrderStatus(OrderStatus.PROCESSING);

        contractService.updateContractOptions(order.getContract());

        order.setOrderStatus(OrderStatus.COMPLETED);
    }


}

package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.exception.TyrannophoneException;
import com.nvans.tyrannophone.model.Order;
import com.nvans.tyrannophone.model.cart.Cart;
import com.nvans.tyrannophone.model.dao.ContractDao;
import com.nvans.tyrannophone.model.dao.CustomerDao;
import com.nvans.tyrannophone.model.dao.OptionDao;
import com.nvans.tyrannophone.model.dao.PlanDao;
import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Customer;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.*;
import com.nvans.tyrannophone.utils.security.ApplicationAuthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PlanDao planDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private OptionValidationService optionValidationService;

    @Autowired
    private Cart cart;

    @Override
    public void processOrder(Order order) {

        if (order == null) return;

        CustomUserPrinciple userPrinciple =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Customer customer = customerDao.getByContractNumber(order.getContract());

        boolean isEmployee = userPrinciple.getAuthorities().contains(ApplicationAuthorities.EMPLOYEE_AUTHORITY);
        boolean isValidCustomer = customer.getId().equals(userPrinciple.getUserId());

        if(!(isEmployee || isValidCustomer)) {
            throw new TyrannophoneException("Can't process order");
        }

        order.setOrderDate(LocalDateTime.now());
        order.setPrice(order.getPlan().getConnectionPrice()
                + order.getOptions().stream().mapToInt(PlanOptionDto::getPrice).sum());



        if (customer.getBalance() - order.getPrice() < 0) {
            throw new TyrannophoneException("Your balance is not enough.");
        }

        Set<Option> contractOptions = order.getOptions()
                .stream().map(o -> optionDao.findById(o.getId())).collect(Collectors.toSet());

        if (!(optionValidationService.isOptionsCompatible(contractOptions))) {
            throw new TyrannophoneException("These options aren't compatible");
        }

        Contract contract = null;

        if (!isEmployee) {
            contract = contractDao.getContractByNumberAndCustomerId(order.getContract(), userPrinciple.getUserId());
        }
        else {
            contract = contractDao.getContractByNumber(order.getContract());
        }

        if(!(contract.isActive())) {
            throw new TyrannophoneException("Contract is suspended");
        }

        contract.setPlan(planDao.findById(order.getPlan().getId()));
        contract.setOptions(contractOptions);

        customer.setBalance(customer.getBalance() - order.getPrice());

        contractDao.save(contract);

        cart.clearCart();
    }
}

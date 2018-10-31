package com.nvans.tyrannophone.web;

import com.nvans.tyrannophone.model.dto.ContractView;
import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import com.nvans.tyrannophone.model.dto.PlanView;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.service.ContractService;
import com.nvans.tyrannophone.service.OptionService;
import com.nvans.tyrannophone.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private PlanService planService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private OptionService optionService;

    @GetMapping("/api/plans")
    public List<PlanView> plans() {

        return planService.getAllPlansViews();
    }

    @GetMapping("/api/plans/{planId}")
    public PlanView plan(@PathVariable Long planId) {

        return planService.getPlanView(planId);
    }

    @GetMapping("/api/contracts/{contractNumber}")
    public ContractView contract(@PathVariable Long contractNumber) {

        return contractService.getContractView(contractNumber);
    }

    @GetMapping("/api/options")
    public List<PlanOptionDto> options() {

        return optionService.getAllOptionsDtoList();
    }

}

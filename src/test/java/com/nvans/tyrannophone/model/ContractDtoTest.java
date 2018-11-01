package com.nvans.tyrannophone.model;

import com.nvans.tyrannophone.model.dto.ContractDto;
import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.Plan;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContractDtoTest {

    @Test
    public void ContractDtoCreationTest() {

        Option optionA = new Option() {{
            setId(1L);
            setName("A");
            setPrice(0);
            setConnectionAvailable(true);
        }};

        Option optionB = new Option() {{
            setId(2L);
            setName("B");
            setPrice(0);
            setConnectionAvailable(true);
        }};


        Map<Option, Boolean> options = new HashMap<>();
        options.put(optionA, true);
        options.put(optionB, false);


        Plan plan = new Plan() {{
            setId(1L);
            setPlanName("Test");
            setAvailableOptions(options);
            setConnectionPrice(0);
            setConnectionAvailable(true);
            setMonthlyPrice(0);
        }};

        Contract contract = new Contract() {{
            setContractNumber(70001111111L);
            setActive(true);
            setPlan(plan);
            setOptions(new HashSet<>(Collections.singletonList(optionB)));
        }};

        ContractDto testDto = new ContractDto(contract);

        assertEquals(contract.getContractNumber(), testDto.getContractNumber());
        assertTrue(testDto.getOptions().containsAll(options.keySet().stream().map(PlanOptionDto::new).collect(Collectors.toList())));

        testDto.getOptions().forEach(o -> assertTrue(o.isConnected()));

    }

}

package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.dao.ContractDao;
import com.nvans.tyrannophone.model.dao.GenericDao;
import com.nvans.tyrannophone.model.entity.BlockDetails;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.User;
import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlockServiceImpl implements BlockService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private GenericDao<User> userDao;

    @Override
    public void blockContract(Long contractNumber, String reason) {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Contract contract = contractDao.getContractByNumber(contractNumber);

        boolean isEmployee = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));

        boolean isSelfBlock =
                currentUser.getUserId().equals(contract.getCustomer().getId());

        if (isEmployee || isSelfBlock) {
            contract.setActive(false);

            BlockDetails blockDetails = new BlockDetails();
            blockDetails.setBlockedByUser(userDao.findById(currentUser.getUserId()));
            blockDetails.setReason(reason);

            contract.setBlockDetails(blockDetails);
        }

    }

    @Override
    public void unblockContract(Long contractNumber) {

        CustomUserPrinciple currentUser =
                (CustomUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Contract contract = contractDao.getContractByNumber(contractNumber);

        boolean isEmployee = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));

        boolean isSelfUnblock =
                currentUser.getUserId().equals(contract.getBlockDetails().getBlockedByUser().getId());

        if (isEmployee || isSelfUnblock) {
            contract.setActive(true);
            contract.setBlockDetails(null);
        }
    }
}

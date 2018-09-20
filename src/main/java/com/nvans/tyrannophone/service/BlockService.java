package com.nvans.tyrannophone.service;

public interface BlockService {

    void blockContract(Long contractNumber, String reason);

    void unblockContract(Long contractNumber);
}

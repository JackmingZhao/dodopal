package com.dodopal.transfer.business.dao;

import java.util.List;

import com.dodopal.transfer.business.model.LogTransfer;

public interface LogTransferMapper {
public int insartLogTransfer(LogTransfer logTransfer);
public List<LogTransfer> findAllLogTransfer(LogTransfer logTransfer);
}

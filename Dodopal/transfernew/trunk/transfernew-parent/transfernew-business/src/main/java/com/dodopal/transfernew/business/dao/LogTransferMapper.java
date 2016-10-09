package com.dodopal.transfernew.business.dao;

import java.util.List;

import com.dodopal.transfernew.business.model.LogTransfer;

public interface LogTransferMapper {
public int insartLogTransfer(LogTransfer logTransfer);
public List<LogTransfer> findAllLogTransfer(LogTransfer logTransfer);
}

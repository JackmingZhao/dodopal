package com.dodopal.transfernew.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfernew.business.model.LogTransfer;

public interface LogTransferService {
public DodopalResponse<List<LogTransfer>> findLogTransfer(LogTransfer logTransfer);
}

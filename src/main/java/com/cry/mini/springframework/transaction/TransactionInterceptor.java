package com.cry.mini.springframework.transaction;

import com.cry.mini.springframework.aop.MethodInterceptor;
import com.cry.mini.springframework.aop.MethodInvocation;

/**
 * <p>
 * 功能描述:
 * </p>
 *
 * @author ryChen
 * @version 1.0
 * @since 2023/05/29 15:35
 */

public class TransactionInterceptor implements MethodInterceptor {
    TransactionManager transactionManager;
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        transactionManager.doBegin();
        Object ret=invocation.proceed();
        transactionManager.doCommit();
        return ret;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}

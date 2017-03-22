package com.ibaixiong.erp.util;

/**
 * 赵磊
 *
 * 采购订单状态的枚举
 *
 */
public enum PurchaseOrderStatusEnum {

    /* 初始待入库 */
    INIT(10),

    /* 入库中 */
    STORAGSTART(20),
    
    /*入库完成*/
    STORAGEEND(30);


    public byte getCode() {
        return this.code;
    }

    private byte code;


    private PurchaseOrderStatusEnum(int code) {
        this.code = (byte)code;
    }
}

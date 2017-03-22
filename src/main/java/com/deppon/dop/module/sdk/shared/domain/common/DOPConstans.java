package com.deppon.dop.module.sdk.shared.domain.common;


/***
 * @Description 常量定义
 */
public class DOPConstans {
	//===============================支付方式 开始=======================================//
	//发货人支付包括：现金   银行卡   欠款   月结    返单付款
	/** 现付 */
	public static final String PAYTYPE_CASH="0";       // 现付
	/** 到付 */
	public static final String PAYTYPE_ARRIVE="1";     // 到付
	//===============================支付方式 结束=======================================//
	
	//===============================运输方式 开始=======================================//
	/** 精准汽运(长) */
	public static final String TRANSPORT_TYPE_QC_JZQYC = "QC_JZQYC"; // 精准汽运(长)
	/** 精准汽运(短) */
	public static final String TRANSPORT_TYPE_QC_JZQYD = "QC_JZQYD"; // 精准汽运(短)
	/** 精准空运 */
	public static final String TRANSPORT_TYPE_HK_JZKY = "HK_JZKY";   // 精准空运
	/** 精准卡航 */
	public static final String TRANSPORT_TYPE_QC_JZKH = "QC_JZKH";   // 精准卡航
	/** 精准卡航 */
	public static final String TRANSPORT_TYPE_QC_JZCY = "QC_JZCY";   // 精准城运
	/** 汽运偏线 */
	public static final String TRANSPORT_TYPE_QC_QYPX = "QC_QYPX";   // 汽运偏线
	/** 整车 */
	public static final String TRANSPORT_TYPE_ZC = "AL_QC_ZC";       // 整车
	/** 德邦快递 */
	public static final String TRANSPORT_TYPE_PACKAGE = "PACKAGE";	// 德邦快递
	//===============================运输方式 结束=======================================//
	
	//===============================代收货款方式 开始=======================================//
	/** 三日退 */
	public static final String CODTYPE_NORMAL = "3";       // 三日退
	/** 即日退 */
	public static final String CODTYPE_INTRADAY = "1";     // 即日退
	//===============================代收货款方式 结束=======================================//
	
	//===============================提货方式 开始=======================================//
	/** 自提*/
	public static final String PICKUP_TYPE_PICKSELF = "0";          // 自提
	/** 送货（不含上楼） */
	public static final String PICKUP_TYPE_PICKNOTUPSTAIRS = "1";   // 送货（不含上楼）
	/** 机场自提 */
	public static final String PICKUP_TYPE_PICKONAIRPORT = "2";     // 机场自提
	/** 送货上楼 */
	public static final String PICKUP_TYPE_UPSTAIRS = "3";          // 送货上楼
	//===============================提货方式 结束=======================================//
	
	//===============================签单回收方式 开始=======================================//
	/** 无需返单 */
	public static final String BACK_SIGN_BILL_NONE = "0";       // 无需返单
	/** 客户签收单传真返回 */
	public static final String BACK_SIGN_BILL_FAX = "2";        // 客户签收单传真返回
	//===============================签单回收方式结束=======================================//
	
	//===============================订单状态 开始=======================================//
	/** 待受理 */
	public static final String ORDER_STATUS_WAITACCEPT = "WAITACCEPT";   // 待受理
	/** 已受理 */
	public static final String ORDER_STATUS_ACCEPT = "ACCEPT";           // 已受理
	/** 已开单 */
	public static final String ORDER_STATUS_GOT = "GOT";                 // 已开单
	/** 已撤销 */
	public static final String ORDER_STATUS_CANCELLED = "CANCELLED";     // 已撤销
	/** 已拒绝 */
	public static final String ORDER_STATUS_UNACCEPT = "UNACCEPT";       // 已拒绝
	/** 揽货失败 */
	public static final String ORDER_STATUS_NOGET = "NOGET";             // 揽货失败
	/** 签收成功 */
	public static final String ORDER_STATUS_SIGNSUCCESS = "SIGNSUCCESS"; // 签收成功
	/** 异常签收 */
	public static final String ORDER_STATUS_SIGNFAILED = "SIGNFAILED";   // 异常签收
	//===============================订单状态结束=======================================//
	
	//===============================物流公司编码 开始=======================================//
	/** 德邦物流 */
	public static final String 	LOGISTIC_PROVIDERID_DEFAULT = "DEPPON";
	//===============================物流公司编码 结束=======================================//
	
	
	
}

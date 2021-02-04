package com.mq.service;

import com.mq.model.InvitationRecord;

public interface InvitationRecordService {

	void initRecord(Long shareCardId, String skey);

	void registered(Long inviteeId, String skey);

	void purchased(Long inviteeId, Long goodsId, String goodsType);

	void usePoints(Long inviterId);

	void withdrawApplyAll(Long inviterId);

	void withdrawApply(Long id);

	void approvalAgree(Long id);

	void approvalReject(Long id);

	InvitationRecord getBySkey(String skey, String status);

	InvitationRecord getByInviteeIdAndGoodsId(Long inviteeId, Long goodsId, String goodsType);
}

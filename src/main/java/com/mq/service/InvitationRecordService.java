package com.mq.service;

import com.mq.model.InvitationRecord;
import com.mq.vo.InvitationRecordVo;

import java.util.Date;
import java.util.List;

public interface InvitationRecordService {

	void initRecord(Long shareCardId, String skey);

	void registered(Long inviteeId, String skey);

	void purchased(Long invitationId);

	void usePoints(Long inviterId, Date unifiedOrderTime);

	void withdrawApplyAll(Long inviterId);

	void withdrawApply(Long id);

	void approvalAgree(Long id);

	void approvalReject(Long id);

	InvitationRecord getBySkey(String skey, String status);

	InvitationRecord getByInviteeIdAndGoodsId(Long inviteeId, Long goodsId, String goodsType);

	List<InvitationRecord> findProfitSharableList(Date begin, Date end);

	void withdrawSuccess(Long id);

	List<InvitationRecordVo> findByInviterSkey(String skey);
}

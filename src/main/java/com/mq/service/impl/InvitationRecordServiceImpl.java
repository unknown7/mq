package com.mq.service.impl;

import com.mq.base.Enums;
import com.mq.mapper.InvitationRecordMapper;
import com.mq.mapper.ShareCardMapper;
import com.mq.model.InvitationRecord;
import com.mq.model.ShareCard;
import com.mq.service.InvitationRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class InvitationRecordServiceImpl implements InvitationRecordService {

	@Resource
	private InvitationRecordMapper invitationRecordMapper;

	@Resource
	private ShareCardMapper shareCardMapper;

	@Override
	@Transactional
	public void initRecord(Long shareCardId, String skey) {

		InvitationRecord query = new InvitationRecord();
		query.setInviteeSkey(skey);
		query.setShareCardId(shareCardId);
		InvitationRecord history = invitationRecordMapper.selectRecentlyByQuery(query);
		if (history == null) {
			ShareCard shareCard = shareCardMapper.selectByPrimaryKey(shareCardId);

			Date now = new Date();
			InvitationRecord invitationRecord = new InvitationRecord();
			invitationRecord.setShareCardId(shareCardId);
			invitationRecord.setInviteeSkey(skey);
			invitationRecord.setStatus(Enums.InvitationStatus.SCANNED.getKey());
			invitationRecord.setDelFlag(Boolean.FALSE);
			invitationRecord.setCreatedTime(now);
			invitationRecord.setModifiedTime(now);
			invitationRecord.setInviterId(shareCard.getUserId());

			invitationRecordMapper.insertSelective(invitationRecord);
		}
	}

	@Override
	@Transactional
	public void registered(Long inviteeId, String skey) {
		InvitationRecord query = new InvitationRecord();
		query.setInviteeSkey(skey);
		query.setStatus(Enums.InvitationStatus.SCANNED.getKey());
		InvitationRecord invitationRecord = invitationRecordMapper.selectRecentlyByQuery(query);
		if (invitationRecord != null) {
			invitationRecord.setInviteeId(inviteeId);
			invitationRecord.setStatus(Enums.InvitationStatus.REGISTERED.getKey());
			invitationRecord.setModifiedTime(new Date());
			invitationRecordMapper.updateByPrimaryKeySelective(invitationRecord);
		}
	}

	@Override
	@Transactional
	public void purchased(Long inviteeId, Long goodsId, String goodsType) {
		InvitationRecord query = new InvitationRecord();
		query.setGoodsId(goodsId);
		query.setGoodsType(goodsType);
		query.setInviteeId(inviteeId);
		query.setStatus(Enums.InvitationStatus.REGISTERED.getKey());
		InvitationRecord invitationRecord = invitationRecordMapper.selectRecentlyByQuery(query);
		if (invitationRecord != null) {
			invitationRecord.setStatus(Enums.InvitationStatus.PURCHASED.getKey());
			invitationRecord.setModifiedTime(new Date());
			invitationRecordMapper.updateByPrimaryKeySelective(invitationRecord);
		}
	}

	@Override
	@Transactional
	public void usePoints(Long inviterId) {
		invitationRecordMapper.updateByInviterIdBeforeDate(
			inviterId,
			Enums.InvitationStatus.USED.getKey(),
			Enums.InvitationStatus.PURCHASED.getKey(),
			new Date()
		);
	}

	@Override
	@Transactional
	public void withdrawApplyAll(Long inviterId) {
		invitationRecordMapper.updateByInviterIdBeforeDate(
			inviterId,
			Enums.InvitationStatus.APPLIED.getKey(),
			Enums.InvitationStatus.PURCHASED.getKey(),
			new Date()
		);
	}

	@Override
	@Transactional
	public void withdrawApply(Long id) {
		InvitationRecord invitationRecord = invitationRecordMapper.selectByPrimaryKey(id);
		if (invitationRecord != null && Enums.InvitationStatus.PURCHASED.getKey().equals(invitationRecord.getStatus())) {
			invitationRecord.setStatus(Enums.InvitationStatus.APPLIED.getKey());
			invitationRecordMapper.updateByPrimaryKeySelective(invitationRecord);
		}
	}

	@Override
	@Transactional
	public void approvalAgree(Long id) {
		InvitationRecord invitationRecord = invitationRecordMapper.selectByPrimaryKey(id);
		if (invitationRecord != null && Enums.InvitationStatus.APPLIED.getKey().equals(invitationRecord.getStatus())) {
			invitationRecord.setStatus(Enums.InvitationStatus.AGREED.getKey());
			invitationRecordMapper.updateByPrimaryKeySelective(invitationRecord);
		}
	}

	@Override
	@Transactional
	public void approvalReject(Long id) {
		InvitationRecord invitationRecord = invitationRecordMapper.selectByPrimaryKey(id);
		if (invitationRecord != null && Enums.InvitationStatus.APPLIED.getKey().equals(invitationRecord.getStatus())) {
			invitationRecord.setStatus(Enums.InvitationStatus.REJECTED.getKey());
			invitationRecordMapper.updateByPrimaryKeySelective(invitationRecord);
		}
	}

	@Override
	public InvitationRecord getBySkey(String skey, String status) {
		InvitationRecord condition = new InvitationRecord();
		condition.setInviteeSkey(skey);
		condition.setStatus(status);
		InvitationRecord invitationRecord = invitationRecordMapper.selectRecentlyByQuery(condition);
		return invitationRecord;
	}
}

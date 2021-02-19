package com.mq.mapper;

import com.mq.model.InvitationRecord;
import com.mq.vo.InvitationRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface InvitationRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InvitationRecord record);

    int insertSelective(InvitationRecord record);

    InvitationRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InvitationRecord record);

    int updateByPrimaryKey(InvitationRecord record);

	InvitationRecord selectRecentlyByQuery(InvitationRecord record);

	int updateByInviterIdBeforeDate(
		@Param("inviterId") Long inviterId,
		@Param("newStatus") String newStatus,
		@Param("originalStatus") String originalStatus,
		@Param("now") Date now
	);

	List<InvitationRecord> selectProfitSharableByRange(
		@Param("modifiedTimeBegin") Date modifiedTimeBegin,
		@Param("modifiedTimeEnd") Date modifiedTimeEnd
	);

	int updateStatusByPrimaryKey(@Param("id") Long id, @Param("status") String status);

	List<InvitationRecordVo> selectByInviterId(@Param("inviterId") Long inviterId);
}
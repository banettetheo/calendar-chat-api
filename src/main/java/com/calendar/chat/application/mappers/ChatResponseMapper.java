package com.calendar.chat.application.mappers;

import com.calendar.chat.application.dtos.ConversationDetailResponse;
import com.calendar.chat.application.dtos.ConversationSummaryResponse;
import com.calendar.chat.application.dtos.MessageBucketResponse;
import com.calendar.chat.application.dtos.MessageResponse;
import com.calendar.chat.domain.models.ConversationDetail;
import com.calendar.chat.domain.models.ConversationSummary;
import com.calendar.chat.domain.models.Message;
import com.calendar.chat.domain.models.MessageBucket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatResponseMapper {

    MessageResponse toMessageResponse(Message message);

    ConversationDetailResponse toDetailResponse(ConversationDetail detail);

    ConversationSummaryResponse toSummaryResponse(ConversationSummary summary);

    MessageBucketResponse toBucketResponse(MessageBucket bucket);
}

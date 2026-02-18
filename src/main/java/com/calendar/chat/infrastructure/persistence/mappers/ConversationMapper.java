package com.calendar.chat.infrastructure.persistence.mappers;

import com.calendar.chat.domain.models.ConversationDetail;
import com.calendar.chat.domain.models.ConversationSummary;
import com.calendar.chat.domain.models.Message;
import com.calendar.chat.domain.models.MessageBucket;
import com.calendar.chat.infrastructure.persistence.models.dtos.MessageEntity;
import com.calendar.chat.infrastructure.persistence.models.entities.ConversationEntity;
import com.calendar.chat.infrastructure.persistence.models.entities.MessageBucketEntity;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    ConversationEntity toConversationEntity(ConversationSummary conversationSummary);

    ConversationSummary toConversationSummary(ConversationEntity conversationEntity);

    ConversationDetail toConversationDetail(ConversationEntity conversationEntity);

    Message toMessage(MessageEntity messageEntity);

    MessageEntity toMessageEntity(Message message);

    MessageBucket toMessageBucket(MessageBucketEntity messageBucketEntity);

    default String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }

    default ObjectId map(String value) {
        return value != null ? new ObjectId(value) : null;
    }
}

package com.tinqin.library.book.api.operations.get.getsubscription;

import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.models.BookModel;
import com.tinqin.library.book.api.models.SubscriptionModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class GetSubscriptionOutput implements ProcessorResult {

    private SubscriptionModel subscription;
}

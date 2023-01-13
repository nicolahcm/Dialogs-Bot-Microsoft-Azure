// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.sample.echo;

import com.codepoetics.protonpack.collectors.CompletableFutures;
import com.microsoft.bot.builder.*;
import com.microsoft.bot.dialogs.ComponentDialog;
import com.microsoft.bot.dialogs.Dialog;
import com.microsoft.bot.schema.ChannelAccount;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class implements the functionality of the Bot.
 *
 * <p>
 * This is where application specific logic for interacting with the users would be added. For this
 * sample, the {@link #onMessageActivity(TurnContext)} echos the text back to the user. The {@link
 * #onMembersAdded(List, TurnContext)} will send a greeting to new conversation participants.
 * </p>
 */
public class EchoBot<T extends Dialog> extends ActivityHandler {

    protected final Dialog dialog;
    protected final BotState conversationState;

    public EchoBot(
            ConversationState withConversationState,
            T withDialog
    ) {
        dialog = withDialog;
        conversationState = withConversationState;
    }


    @Override
    public CompletableFuture<Void> onTurn(
            TurnContext turnContext
    ) {
        return super.onTurn(turnContext)
                .thenCompose(result -> conversationState.saveChanges(turnContext));
                // Save any state changes that might have occurred during the turn.
    }

    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {

        return Dialog.run(dialog, turnContext, conversationState.createProperty("DialogState"));

    }

    @Override
    protected CompletableFuture<Void> onMembersAdded(
        List<ChannelAccount> membersAdded,
        TurnContext turnContext
    ) {
        String welcomeText = "Hello and welcome! Type to start....";
        return membersAdded.stream()
            .filter(
                member -> !StringUtils
                    .equals(member.getId(), turnContext.getActivity().getRecipient().getId())
            ).map(channel -> turnContext.sendActivity(MessageFactory.text(welcomeText, welcomeText, null)))
            .collect(CompletableFutures.toFutureList()).thenApply(resourceResponses -> null);
    }
}

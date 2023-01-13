package com.microsoft.bot.sample.echo;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.UserState;
import com.microsoft.bot.dialogs.*;
import com.microsoft.bot.dialogs.prompts.PromptOptions;
import com.microsoft.bot.dialogs.prompts.TextPrompt;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class D3 extends ComponentDialog {

    public D3() {
        super("D3");

        addDialog(new TextPrompt("TextPrompt"));

        WaterfallStep[] waterfallSteps = {
                this::sss1,
                this::sss2
        };
        addDialog(new WaterfallDialog("WaterfallDialog", Arrays.asList(waterfallSteps)));

        setInitialDialogId("WaterfallDialog");
    }


    public CompletableFuture<DialogTurnResult> sss1(WaterfallStepContext stepContext) {
        PromptOptions promptOptions = new PromptOptions();
        promptOptions.setPrompt(MessageFactory.text("This is step 1 of D3 (sss1 from D3). Type anything"));
        return stepContext.prompt("TextPrompt", promptOptions);
    }

    public CompletableFuture<DialogTurnResult> sss2(WaterfallStepContext stepContext) {
        return stepContext.getContext().sendActivity(
                MessageFactory.text("This is step 2 of D3 (sss2 from D3). Ending Dialog 3, returning to dialog 2...")
        ).thenCompose(resourceResponse -> stepContext.endDialog());
    }

}
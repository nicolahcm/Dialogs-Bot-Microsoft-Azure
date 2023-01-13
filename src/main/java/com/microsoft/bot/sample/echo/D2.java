package com.microsoft.bot.sample.echo;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.UserState;
import com.microsoft.bot.dialogs.*;
import com.microsoft.bot.dialogs.prompts.PromptOptions;
import com.microsoft.bot.dialogs.prompts.TextPrompt;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class D2 extends ComponentDialog {

    public D2() {
        super("D2");

        addDialog(new TextPrompt("TextPrompt")); // adding a dialog TextPrompt in the set dialog of D2
        addDialog(new D3());  // adding dialog D3 in the set dialog of D2

        WaterfallStep[] waterfallSteps = {
                this::ss1,
                this::ss2,
                this::ss3
        };
        addDialog(new WaterfallDialog("WaterfallDialog", Arrays.asList(waterfallSteps)));

        setInitialDialogId("WaterfallDialog");
    }


    public CompletableFuture<DialogTurnResult> ss1(WaterfallStepContext stepContext) {
        return stepContext.getContext().sendActivity(
                MessageFactory.text("This is step 1 of D2 (ss1 from D2). Now beginning Dialog 3.")
        ).thenCompose(resourceResponse -> stepContext.beginDialog("D3"));
    }

    public CompletableFuture<DialogTurnResult> ss2(WaterfallStepContext stepContext) {
        PromptOptions promptOptions = new PromptOptions();
        promptOptions.setPrompt(MessageFactory.text("This is step 2 of D2 (ss2 from D2). Type anything"));
        return stepContext.prompt("TextPrompt", promptOptions);
    }

    public CompletableFuture<DialogTurnResult> ss3(WaterfallStepContext stepContext) {
        return stepContext.getContext().sendActivity(
                MessageFactory.text("This is step 3 of D2 (ss3 from D2). Dialog 2 ending here. " +
                        "Returning to dialog 1...")
        ).thenCompose(resourceResponse -> stepContext.endDialog());
    }

}

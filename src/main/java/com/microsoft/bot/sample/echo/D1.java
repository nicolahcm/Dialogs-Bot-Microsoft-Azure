package com.microsoft.bot.sample.echo;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.UserState;
import com.microsoft.bot.dialogs.*;
import com.microsoft.bot.dialogs.prompts.PromptOptions;
import com.microsoft.bot.dialogs.prompts.TextPrompt;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
public class D1 extends ComponentDialog {

    private final UserState userState;

    public D1(UserState withUserState) {
        super("D1");

        userState = withUserState;

        addDialog(new TextPrompt("TextPrompt"));  // adding a dialog of type TextPrompt in the set Dialog of D1.
        // its dialogid is TextPrompt

        addDialog(new D2());       // adding the custom dialog D2 in the set Dialog of D1

        WaterfallStep[] waterfallSteps = {
                this::s1,
                this::s2,
                this::s3,
                this::s4
        };
        addDialog(new WaterfallDialog("WaterfallDialog", Arrays.asList(waterfallSteps)));

        setInitialDialogId("WaterfallDialog");
    }


    public CompletableFuture<DialogTurnResult> s1(WaterfallStepContext stepContext) {
        PromptOptions promptOptions = new PromptOptions();
        promptOptions.setPrompt(MessageFactory.text("This is step 1 of D1 (s1 from D1). Type anything"));
        return stepContext.prompt("TextPrompt", promptOptions);
    }

    public CompletableFuture<DialogTurnResult> s2(WaterfallStepContext stepContext) {
        return stepContext.getContext().sendActivity(
                MessageFactory.text("This is step 2 of D1 (s2 from D1). Now we call dialog 2, D2")
        ).thenCompose(resourceResponse -> stepContext.beginDialog("D2"));
    }

    public CompletableFuture<DialogTurnResult> s3(WaterfallStepContext stepContext) {
        PromptOptions promptOptions = new PromptOptions();
        promptOptions.setPrompt(MessageFactory.text("This is step 3 of D1 (s3 from D1). Type anything"));
        return stepContext.prompt("TextPrompt", promptOptions);
    }

    public CompletableFuture<DialogTurnResult> s4(WaterfallStepContext stepContext) {
        return stepContext.getContext().sendActivity(
                        MessageFactory.text("This is step 4 of D1 (s4 from D1). Ending our whole conversation.")
                ).thenCompose(resourceResponse -> stepContext.endDialog());
    }
}

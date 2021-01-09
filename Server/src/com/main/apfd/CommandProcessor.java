package com.main.apfd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.main.apfd.Action.actionFrom;
import static org.apache.commons.lang3.StringUtils.split;

public class CommandProcessor implements CommandProcessable {

    @Override
    public Command process(String message) {
        List<String> allArguments = reformat(message);
        String action = allArguments.remove(0);

        return new Command()
                .action(actionFrom(action))
                .arguments(allArguments);
    }

    private List<String> reformat(String message){
        List<String> arguments = Arrays.asList(split(message));
        if(!hasMessageWithMoreSpaces(arguments)) return new ArrayList<>(arguments);
        return new ArrayList<>(Arrays.asList(split(message, null, 3)));
    }

    private boolean hasMessageWithMoreSpaces(List<String> arguments){
        return arguments.size() > 3;
    }
}

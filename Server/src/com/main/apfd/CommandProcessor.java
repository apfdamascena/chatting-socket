package com.main.apfd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.main.apfd.Action.actionFrom;
import static org.apache.commons.lang3.StringUtils.split;

public class CommandProcessor implements CommandProcessable {

    @Override
    public Command process(String message) {
        List<String> allArguments = new ArrayList<>(Arrays.asList(split(message)));
        String action = allArguments.remove(0);

        return new Command()
                .action(actionFrom(action))
                .arguments(allArguments);
    }

}

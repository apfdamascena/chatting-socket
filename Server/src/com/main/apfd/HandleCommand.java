package com.main.apfd;

import java.util.List;

interface HandleCommand {

    void handle(Action action, List<String> arguments);
}

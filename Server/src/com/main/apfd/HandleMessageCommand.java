package com.main.apfd;

import java.util.List;

public class HandleMessageCommand implements HandleCommand {

    @Override
    public void handle(Action action, List<String> arguments) {
        if(!isMessage(action)) return;
        String receiver = arguments.get(0);
        String message = arguments.get(1);

        boolean isChatRoom = receiver.charAt(0) == '#';

        List<ServerWorker> workers = Store.shared.getWorkers();
        workers.forEach((worker -> {
            Chatting chat = new ChattingBuilder()
                                        .from(worker)
                                        .with(message)
                                        .forThis(receiver)
                                        .inRoom(isChatRoom)
                                        .build();

            worker.tryToSend(chat.doConversation());
        }));
    }
    private boolean isMessage(Action action){
        return action.equals(Action.message);
    }
}

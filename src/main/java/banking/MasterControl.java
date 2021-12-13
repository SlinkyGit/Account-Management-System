package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MasterControl {
    Bank bank;
    ValidatorRedirect validatorRedirect;
    HandlerRedirect handlerRedirect;
    StoreCommand storeCommand;

    public MasterControl(Bank bank, ValidatorRedirect validatorRedirect,
                         HandlerRedirect handlerRedirect, StoreCommand storeCommand) {
        this.bank = bank;
        this.validatorRedirect = validatorRedirect;
        this.handlerRedirect = handlerRedirect;
        this.storeCommand = storeCommand;

    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (validatorRedirect.validate(command)) {
                handlerRedirect.handle(command);
            } else {
                storeCommand.addCommand(command);
            }
        }
        List<String> listOfOutput = new ArrayList<>();
        Set<String> accounts = bank.getAccounts().keySet();
        for (String a : accounts) {
            listOfOutput.add(bank.getAccounts().get(a).getStateofAcc());
            listOfOutput.addAll(bank.getAccounts().get(a).getTransactionHistory());
        }
        listOfOutput.addAll(storeCommand.getCommand());
        return listOfOutput;
    }


}

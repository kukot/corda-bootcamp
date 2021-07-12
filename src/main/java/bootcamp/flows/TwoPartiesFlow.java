package bootcamp.flows;

import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.FlowSession;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.flows.StartableByRPC;
import net.corda.core.identity.Party;

@InitiatingFlow
@StartableByRPC
public class TwoPartiesFlow extends FlowLogic<Integer> {

    private final Party counterParty;
    private int initNumber;

    public TwoPartiesFlow(Party counterParty, int initNumber) {
        this.counterParty = counterParty;
        this.initNumber = initNumber;
    }

    @Override
    public Integer call() throws FlowException {
        FlowSession flowSession = initiateFlow(counterParty);
        flowSession.send(initNumber);
        int receivedIncrementedInteger = flowSession.receive(Integer.class).unwrap(it -> it);
        return receivedIncrementedInteger;
    }
}

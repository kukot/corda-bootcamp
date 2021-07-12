package bootcamp.flows;

import bootcamp.HouseState;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.FlowSession;
import net.corda.core.flows.InitiatedBy;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.node.NodeInfo;
import net.corda.core.node.ServiceHub;

import java.util.List;

@InitiatedBy(TwoPartiesFlow.class)
public class TwoPartiesFlowResponder extends FlowLogic<Void> {

    FlowSession counterPartyFlowSession;

    public TwoPartiesFlowResponder(FlowSession counterPartyFlowSession) {
        this.counterPartyFlowSession = counterPartyFlowSession;
    }

    @Override
    public Void call() throws FlowException {
        ServiceHub serviceHub = getServiceHub();
        List<StateAndRef<HouseState>> availableState = serviceHub.getVaultService().queryBy(HouseState.class).getStates();

        CordaX500Name kevin = new CordaX500Name("Kevin Nguyen", "CMC Global", "Vietnam");
        NodeInfo nodeInfo = serviceHub.getNetworkMapCache().getNodeByLegalName(kevin);
        int platformVersion = serviceHub.getMyInfo().getPlatformVersion();
        int received = this.counterPartyFlowSession.receive(Integer.class).unwrap(it -> {
            if (it > 100) {
                throw new IllegalArgumentException("Exceed maximum value");
            }
            return it;
        });
        int incremented = received + 1;
        counterPartyFlowSession.send(incremented);
        return null;
    }
}

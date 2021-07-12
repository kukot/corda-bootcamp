package bootcamp.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.flows.StartableByRPC;
import net.corda.core.flows.StartableByService;

//@StartableByRPC
//@StartableByService
@InitiatingFlow
public class SimpleFlowLogic extends FlowLogic<Void> {

    @Override
    @Suspendable//this is mandatory for all Suspendable
    public Void call() throws FlowException {
        return null;
    }
}

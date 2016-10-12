package za.co.discoverygrouprisk;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;

public class SendMessageTaskDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            System.out.println("SendMessageTaskDelegate#execute");
            delegateExecution
                    .getProcessEngineServices()
                    .getRuntimeService()
                    .correlateMessage("DOTASK", delegateExecution.getProcessBusinessKey());
            delegateExecution.setVariable("send message", "done!!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

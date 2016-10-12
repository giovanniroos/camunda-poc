package za.co.discoverygrouprisk;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class MyTestDelegate implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("MyTestDelegate#execute");
        delegateExecution.setVariable("long-running-task", "done!!");

    }
}

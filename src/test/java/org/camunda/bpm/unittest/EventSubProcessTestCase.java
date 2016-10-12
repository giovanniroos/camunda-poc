package org.camunda.bpm.unittest;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.LogUtil;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;

public class EventSubProcessTestCase {

    // enable more detailed logging
    static {
        LogUtil.readJavaUtilLoggingConfigFromClasspath();
    }

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    @Test
    @Deployment(resources = {"event-sub-process.bpmn"})
    public void testDeployment() {

    }

    @Test
    @Deployment(resources = {"event-sub-process.bpmn"})
    public void testEventSubProcess() {

        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("event-sub-process", "123");
        assertThat(processInstance).isActive();
        Assertions.assertThat(processInstanceQuery().count()).isEqualTo(1);
        assertThat(processInstance).isWaitingAt("send-message");
        execute(job());
        assertThat(processInstance).isWaitingAt("UserTask_1");
        complete(task());
        assertThat(processInstance).isEnded();

    }
}

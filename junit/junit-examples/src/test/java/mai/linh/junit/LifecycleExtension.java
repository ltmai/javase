package mai.linh.junit;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public class LifecycleExtension 
    implements  TestInstancePostProcessor, 
                BeforeAllCallback, 
                BeforeEachCallback, 
                AfterAllCallback, 
                AfterEachCallback, 
                AfterTestExecutionCallback  
{

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        System.out.println("#### postProcessTestInstance ###");
    }

	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
        System.out.println("### afterTestExecution ###");
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
        System.out.println("### afterEach ###");
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("### afterAll ###");
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
        System.out.println("### beforeEach ###");
	}

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
        System.out.println("### beforeAll ###");
	}
    
}
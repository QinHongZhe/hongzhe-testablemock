package com.alibaba.testable.demo;

import android.content.Intent;
import android.util.Log;

import com.alibaba.testable.core.annotation.MockInvoke;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.alibaba.testable.core.matcher.InvocationVerifier.verifyInvoked;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30)
public class DemoServiceTest {

    private DemoService demoService;

    public static class Mock {
        @MockInvoke(targetClass = Log.class, targetMethod = "d")
        public static int log(String tag, String msg) {
            return 0;
        }
    }

    @Before
    public void setup() throws Exception {
        demoService = Robolectric.setupService(DemoService.class);
    }

    @Test
    public void testOnStartCommand() {
        Intent intent = new Intent();

        intent.setAction("start_foreground");
        demoService.onStartCommand(intent, 0, 1);
        verifyInvoked("log").with("DemoService", "start service.");

        intent.setAction("stop_foreground");
        demoService.onStartCommand(intent, 0, 1);
        verifyInvoked("log").with("DemoService", "stop service.");
    }
}
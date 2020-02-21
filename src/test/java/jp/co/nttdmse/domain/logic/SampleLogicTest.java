package jp.co.nttdmse.domain.logic;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SampleLogicTest {

	@Autowired
	SampleLogic sampleLogic;

	@Test
	void testDoSampleLogic() {
		final String TEST_PREFIX = "sampleLogicTest";
		final String ASSERT_STR = "sampleLogicTest do Logic★";
		String result = sampleLogic.doSampleLogic(TEST_PREFIX);
		assertThat(result, is(ASSERT_STR));
	}
}

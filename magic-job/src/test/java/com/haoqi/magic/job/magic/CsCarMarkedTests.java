package com.haoqi.magic.job.magic;

import com.haoqi.magic.job.MagicJobApplication;
import com.haoqi.magic.job.service.ICsMarkedCarTagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MagicJobApplication.class)
public class CsCarMarkedTests {

	@Autowired
	private ICsMarkedCarTagService csMarkedCarTagService;




	@Test
	public void testMarkCarTag() {
		csMarkedCarTagService.markCarTag(3,0);
		csMarkedCarTagService.markCarTag(3,1);
		csMarkedCarTagService.markCarTag(3,2);
	}





}

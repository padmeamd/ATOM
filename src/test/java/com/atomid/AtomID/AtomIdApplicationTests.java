package com.atomid.AtomID;

import com.atomid.AtomID.entity.Topic;
import com.atomid.AtomID.repository.TopicRepository;
import com.atomid.AtomID.service.TopicService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class AtomIdApplicationTests {

	@MockBean
	private TopicRepository topicRepository;

	@Autowired
	private TopicService topicService;

	@Test
	void contextLoads() {
		final long id = 10;
		Mockito.when(topicRepository.findById(id))
				.thenReturn(Optional.of(getTopicFromRepoExample()));


		assertThat(topicService.getTopicById(id))
				.isNotNull()
				.isPresent();
		assertThat(topicService.getTopicById(id).get())
				.isNotNull()
				.hasNoNullFieldsOrProperties();

	}


	private Topic getTopicFromRepoExample(){
		return new Topic(10L, "title", List.of());
	}

}

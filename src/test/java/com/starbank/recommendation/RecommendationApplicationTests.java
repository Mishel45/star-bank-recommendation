package com.starbank.recommendation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecommendationApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testInvest500Recommendation() throws Exception {
		mockMvc.perform(get("/recommendation/cd515076-5d8a-44be-930e-8d4fcb79f42d"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.user_id").value("cd515076-5d8a-44be-930e-8d4fcb79f42d"))
				.andExpect(jsonPath("$.recommendations[0].name").value("Invest 500"));
	}

	@Test
	void testTopSavingRecommendation() throws Exception {
		mockMvc.perform(get("/recommendation/d4a4d619-9a0c-4fc5-b0cb-76c49409546b"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.recommendations[0].name").value("Top Saving"));
	}

	@Test
	void testSimpleCreditRecommendation() throws Exception {
		mockMvc.perform(get("/recommendation/1f9b149c-6577-448a-bc94-16bea229b71a"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.recommendations[0].name").value("Простой кредит"));
	}

	@Test
	void testUnknownUserReturnsEmptyList() throws Exception {
		mockMvc.perform(get("/recommendation/00000000-0000-0000-0000-000000000000"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.recommendations").isEmpty());
	}
}
package com.deepansha.cowinSlots.repo;

import com.deepansha.cowinSlots.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<Feedback,Integer> {

}
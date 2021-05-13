package com.deepansha.cowinSlots.repo;

import com.deepansha.cowinSlots.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Date;

public interface NotificationsRepo extends JpaRepository<Notifications,Integer> {
    public List<Notifications> findByAlertId(int id);

    public List<Notifications> findByAlertIdAndCreatedAtAfter(int id, Date date);
}

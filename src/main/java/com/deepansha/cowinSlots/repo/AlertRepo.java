package com.deepansha.cowinSlots.repo;
import java.util.List;

import com.deepansha.cowinSlots.entity.Alerts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepo extends JpaRepository<Alerts,Integer> {

    public List<Alerts> findByName(String name);

    public List<Alerts> findByPhoneNumber(String phoneNumber);

    public List<Alerts> findByPhoneNumberAndActiveTrue(String phoneNumber);

    public List<Alerts> findByActiveTrue();
}

package module5.pro.repository;

import jakarta.transaction.Transactional;
import module5.pro.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ManufacturerRepo  extends JpaRepository<Manufacturer, Long> {
}

package it.cnr.rethinkwaste.repository.assessment;

import it.cnr.rethinkwaste.model.assessment.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>  {

}

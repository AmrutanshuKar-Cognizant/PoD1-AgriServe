package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.FarmerDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FarmerDocumentRepository  extends JpaRepository<FarmerDocument, Long> {

}

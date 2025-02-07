package br.com.susunity.repository;

import br.com.susunity.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, UUID> {

    AddressModel findByStreetAndNumberAndCity(String street, int number, String city);

}

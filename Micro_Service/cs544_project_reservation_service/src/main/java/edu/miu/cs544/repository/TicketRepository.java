package edu.miu.cs544.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.cs544.domain.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	Optional<Ticket> findByNumber(String ticketNumber);
}

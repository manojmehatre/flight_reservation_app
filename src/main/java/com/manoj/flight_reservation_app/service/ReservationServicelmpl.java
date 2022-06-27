package com.manoj.flight_reservation_app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.flight_reservation_app.dto.ReservationRequest;
import com.manoj.flight_reservation_app.entity.Flight;
import com.manoj.flight_reservation_app.entity.Passenger;
import com.manoj.flight_reservation_app.entity.Reservation;
import com.manoj.flight_reservation_app.repository.FlightRepository;
import com.manoj.flight_reservation_app.repository.PassengerRepository;
import com.manoj.flight_reservation_app.repository.ReservationRepository;
import com.manoj.flight_reservation_app.utilities.EmailSender;
import com.manoj.flight_reservation_app.utilities.PdfGenerator;

@Service
public class ReservationServicelmpl implements ReservationService {

	
	@Autowired
	private PassengerRepository passengerRepo;
	
	@Autowired
	private FlightRepository flightRepo;
	
	@Autowired
	private ReservationRepository  reservationRepo;
	
	@Autowired
	private EmailSender emailSender;
	
	@Override
	public Reservation bookFlight(ReservationRequest request) {
		
		String filePath = "E:\\PSA\\tickets\\booking";
		 
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getFirstName());
		passenger.setLastName(request.getLastName());
		passenger.setMiddleName(request.getMiddleName());
		passenger.setEmail(request.getEmail());
		passenger.setPhone(request.getPhone());
		passengerRepo.save(passenger);
		
		long flightId = request.getFlightId();
		Optional<Flight> findById = flightRepo.findById(flightId);
		Flight flight = findById.get();
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(passenger);
		reservation.setCheckedIn(false);
		reservation.setNumberOfBags(0);
		reservationRepo.save(reservation);
		
		PdfGenerator pdf = new PdfGenerator();
		pdf.generatePDF(filePath+reservation.getId()+".pdf", reservation.getPassenger().getFirstName(), reservation.getPassenger().getEmail(), reservation.getPassenger().getPhone(), reservation.getFlight().getOperatingAirlines(), reservation.getFlight().getDateOfDeparture(), reservation.getFlight().getDepartureCity(), reservation.getFlight().getArrivalCity());
		
		
		emailSender.sendEmail(passenger.getEmail(), filePath);
		
		return reservation;
	}

}

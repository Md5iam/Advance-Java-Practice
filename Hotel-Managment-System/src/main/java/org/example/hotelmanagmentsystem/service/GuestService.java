package org.example.hotelmanagmentsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagmentsystem.model.Guest;
import org.example.hotelmanagmentsystem.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Optional<Guest> getGuestById(Long id) {
        return guestRepository.findById(id);
    }

    public Guest saveGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }

    public long countTotalGuests() {
        return guestRepository.count();
    }
}

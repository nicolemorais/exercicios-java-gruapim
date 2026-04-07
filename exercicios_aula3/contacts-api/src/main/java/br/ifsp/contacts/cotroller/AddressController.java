package br.ifsp.contacts.controller;

import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.AddressRepository;
import br.ifsp.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/contacts/{contactId}/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    // ---- Rota para CRIAR o endereço
    @PostMapping
    public Address addAddress(@PathVariable Long contactId, @RequestBody Address address) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));

        address.setContact(contact);
        return addressRepository.save(address);
    }

    //---- Rota para LISTAR os endereços
    @GetMapping
    public List<Address> getAddressesByContact(@PathVariable Long contactId) {
        if (!contactRepository.existsById(contactId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado");
        }
        return addressRepository.findByContactId(contactId);
    }
}
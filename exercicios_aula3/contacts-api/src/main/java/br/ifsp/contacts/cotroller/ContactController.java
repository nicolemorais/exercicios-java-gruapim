package br.ifsp.contacts.controller;

import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {


    @Autowired
    private ContactRepository contactRepository;

    // ---- Método para CRIAR (POST) ---
    @PostMapping
    public Contact createContact(@Valid @RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    // ---- EXERCÍCIO 1: Busca por Nome (GET) ---
    @GetMapping("/search")
    public List<Contact> searchContactsByName(@RequestParam String name) {
        return contactRepository.findByNomeContainingIgnoreCase(name);
    }

    // ---- EXERCÍCIO 2: Atualização Parcial (PATCH) ---
    @PatchMapping("/{id}")
    public Contact updateContactParcial(@PathVariable Long id, @RequestBody Contact atualizacao) {

         Contact contatoExistente = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));

         Optional.ofNullable(atualizacao.getNome())
                .ifPresent(novoNome -> contatoExistente.setNome(novoNome));

        Optional.ofNullable(atualizacao.getTelefone())
                .ifPresent(novoTelefone -> contatoExistente.setTelefone(novoTelefone));

        Optional.ofNullable(atualizacao.getEmail())
                .ifPresent(novoEmail -> contatoExistente.setEmail(novoEmail));

         return contactRepository.save(contatoExistente);
    }
}
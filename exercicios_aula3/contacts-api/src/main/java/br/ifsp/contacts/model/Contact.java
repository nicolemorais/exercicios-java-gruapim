package br.ifsp.contacts.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;


@Getter
@Entity
public class Contact {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;

    @Size(min = 8, max = 15, message = "O telefone deve ter entre 8 e 15 caracteres")
    private String telefone;

    @Email(message = "Formato de e-mail inválido")
    private String email;

     @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    private List<Address> addresses;

     public Contact() {}

     public Contact(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
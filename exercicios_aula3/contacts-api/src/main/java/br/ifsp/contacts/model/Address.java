package br.ifsp.contacts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;
    private String cidade;
    private String estado;
    private String cep;

    @ManyToOne
    @JoinColumn(name = "contact_id") // Cria a chave estrangeira no banco
    @JsonIgnore // Evita o loop infinito ao transformar em JSON!
    private Contact contact;

     public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public Contact getContact() { return contact; }
    public void setContact(Contact contact) { this.contact = contact; }
}
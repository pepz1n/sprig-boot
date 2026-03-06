package med.voll.api.medico;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    private String telefone;
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public Medico(DadosCadastroMedico request) {
        this.nome = request.nome();
        this.email = request.email();
        this.crm = request.crm();
        this.especialidade = request.especialidade();
        this.telefone = request.telefone();
        this.endereco = new Endereco(request.endereco());
    }

    public void atualizarInformacoes(DadosAtualizarMedico request) {
        if (request.nome() != null) {
            this.nome = request.nome();
        }
        if (request.telefone() != null) {
            this.telefone = request.telefone();
        }
        if (request.endereco() != null) {
            this.endereco.atualizarInformacoes(request.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
